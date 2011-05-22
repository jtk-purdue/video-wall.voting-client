package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and sends across what was voted for.
 */

public class Voting extends ListActivity {
    private Vector<VoteData> data;
    private VoteDataAdapter adapter;
    private VoteData rd;
    private ArrayList<String> voteList;
    private ArrayList<String> votes;
    private String editTextPreference;
    private int portNumber;
    private String serverName;
    private Server server;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	Log.d("Voting", "onCreate");

	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
	data = new Vector<VoteData>();
	adapter = new VoteDataAdapter(this, R.layout.list_item, data);
	setListAdapter(adapter);
	getListView().setTextFilterEnabled(true);

	fetchPreferenceData();
	
	// TODO: Rewrite to put in background task...
	updateVoteData();
    }

    void fetchPreferenceData() {
	SharedPreferences serverPref = PreferenceManager.getDefaultSharedPreferences(this);
	SharedPreferences portPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

	String serverNamePref = serverPref.getString("serverPref", "pc.cs.purdue.edu");
	String portNumberPref = portPref.getString("editTextPref", "4242");

	if (!(serverNamePref.equals(serverName)) || !(portNumberPref).equals(editTextPreference)) {
	    serverName = serverNamePref;
	    editTextPreference = portNumberPref;
	    portNumber = Integer.parseInt(editTextPreference);

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    // TODO: Need to auto-choose between which server to use...
	    server = new ServerReal(serverName, portNumber, cm);
//	    server = new ServerTest();
	}
    }

    void updateVoteData() {
	try {
	    voteList = server.getList();
	    votes = server.getCount();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    voteList = null; // set votes to null also? bit of a kludge to indicate
	    toast("onCreate Exception: " + e.toString());
	    e.printStackTrace();
	}
	updateData();
    }

    void toast(String message) {
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
	super.onPause();
	Log.d("Voting", "onPause");
    }

    @Override
    public void onResume() {
	super.onResume();
	Log.d("Voting", "onResume");
	fetchPreferenceData();
	updateVoteData();
    }

    @Override
    public void onStop() {
	super.onStop();
	Log.d("Voting", "onStop");
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	Log.d("Voting", "onDestroy");
    }

    public void onListItemClick(ListView parent, View v, final int position, long id) {
	final String vi = data.get(position).mTitle;
	
	try {
	    server.vote(vi);
	    votes = server.getCount();
	    // TODO: if server is down, voteList might be null, but call to getCount above generates exception, skipping
	    // .size()
	    toast("Voted for " + vi);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    toast("onListItemClick Exception: " + e.toString());
	    e.printStackTrace();
	}

	VoteData r = null;

	// TODO: code assumes the voteList and data list are the same size (in sync)
	try {
	    for (int i = 0; i < voteList.size(); i++) {
		r = (VoteData) data.elementAt(i);
		String temp = "Number of votes: " + votes.get(i);
		r.setDetail(temp);
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	adapter.notifyDataSetChanged();
    }

    public void updateData() {
	data.clear();
	if (voteList == null)
	    Log.d("Voting", "voteList is null in updateData");
	else {
	    Log.d("Voting", "updateData with " + voteList.size() + " votable items");
	    for (int i = 0; i < voteList.size(); i++) {
		try {
		    rd = new VoteData(i, voteList.get(i), "Number of votes: " + votes.get(i));
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		data.add(rd);
	    }
	}
    }

    private class VoteData {
	protected int mId;
	protected String mTitle;
	protected String mDetail;

	VoteData(int id, String title, String detail) {
	    mId = id;
	    mTitle = title;
	    mDetail = detail;
	}

	@Override
	public String toString() {
	    return mId + " " + mTitle + " " + mDetail;
	}

	public void setDetail(String item) {
	    mDetail = item;
	}

    }

    private class VoteDataAdapter extends ArrayAdapter<VoteData> {
	LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

	
	public VoteDataAdapter(Context context, int layoutId, List<VoteData> voteData) {
	    super(context, layoutId, voteData);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder = null;
	    TextView title = null;
	    TextView detail = null;
	    ImageView i11 = null;
	    VoteData rowData = getItem(position);
	    if (null == convertView) {
		convertView = inflater.inflate(R.layout.list_item, null);
		holder = new ViewHolder(convertView);
		convertView.setTag(holder);
	    }
	    holder = (ViewHolder) convertView.getTag();
	    title = holder.gettitle();
	    title.setText(rowData.mTitle);
	    detail = holder.getdetail();
	    detail.setText(rowData.mDetail);
	    i11 = holder.getImage();
	    i11.setImageResource(R.drawable.voteicon);
	    return convertView;
	}

	public class ViewHolder {
	    private View mRow;
	    private TextView title = null;
	    private TextView detail = null;
	    private ImageView i11 = null;

	    public ViewHolder(View row) {
		mRow = row;
	    }

	    public TextView gettitle() {
		if (null == title) {
		    title = (TextView) mRow.findViewById(R.id.title);
		}
		return title;
	    }

	    public TextView getdetail() {
		if (null == detail) {
		    detail = (TextView) mRow.findViewById(R.id.detail);
		}
		return detail;
	    }

	    public ImageView getImage() {
		if (null == i11) {
		    i11 = (ImageView) mRow.findViewById(R.id.img);
		}
		return i11;
	    }

	}
    }

    // @Override
    public boolean onPrepareOptionsMenu(Tabs menu) {
	return super.onPrepareOptionsMenu((android.view.Menu) menu);
    }

    // @Override
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.voteoptionsmenu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.help:
	    Intent i = new Intent();
	    i.setClass(Voting.this, Help.class);
	    startActivity(i);
	    break;

	case R.id.stats:
	    Intent i2 = new Intent();
	    i2.putExtra("votes", voteList);
	    i2.putExtra("votesint", votes);
	    i2.setClass(Voting.this, Stats.class);
	    startActivity(i2);
	    break;

	case R.id.settings:
	    Intent i3 = new Intent();
	    i3.setClass(Voting.this, Settings.class);
	    startActivity(i3);
	    break;

	case R.id.quit:
	    finish();
	    break;
	}
	return true;
    }
}