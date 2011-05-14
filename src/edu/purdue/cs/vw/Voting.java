package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.purdue.cs.vw.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private LayoutInflater mInflater;
    private Vector<RowData> data;
    CustomAdapter adapter;
    RowData rd;
    ArrayList<String> voteList;
    ArrayList<String> votes;
    Animation anim = null;
    ProgressDialog myProgressDialog = null;
    String editTextPreference;
    int portNumber;
    String serverName;
    boolean voted = false;
    int lastPosition = -1;
    Server server;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	Log.d("Voting", "onCreate");

	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	setContentView(R.layout.vote);
	anim = AnimationUtils.loadAnimation(this, R.anim.shake); // Sets the animation to shake
	mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	
	data = new Vector<RowData>();
	adapter = new CustomAdapter(this, R.layout.list, R.id.title, data);
	setListAdapter(adapter);
	getListView().setTextFilterEnabled(true);

	fetchPreferenceData();
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
	TextView title = (TextView) v.findViewById(R.id.title);
	final String vi = (String) ((TextView) title).getText();
	try {
	    server.vote(vi);
	    voted = true;
	    votes = server.getCount();
	    lastPosition = position;
	    // TODO: if server is down, voteList might be null, but call to getCount above generates exception, skipping
	    // .size()
	    toast("Voted for " + vi);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    toast("onListItemClick Exception: " + e.toString());
	    e.printStackTrace();
	}

	RowData r = null;

	// TODO: code assumes the voteList and data list are the same size (in sync)
	try {
	    for (int i = 0; i < voteList.size(); i++) {
		r = (RowData) data.elementAt(i);
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
		    rd = new RowData(i, voteList.get(i), "Number of votes: " + votes.get(i));
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		data.add(rd);
	    }
	}
	// TODO: Has adapter already been set? Was setting every time. Needed?
	if (adapter == null) {
	    Log.d("Voting", "RESETTING ADAPTER in updateData");
	    adapter = new CustomAdapter(this, R.layout.list, R.id.title, data);
	    setListAdapter(adapter);
	    getListView().setTextFilterEnabled(true);
	}
    }

    private class RowData {
	protected int mId;
	protected String mTitle;
	protected String mDetail;

	RowData(int id, String title, String detail) {
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

    private class CustomAdapter extends ArrayAdapter<RowData> {
	public CustomAdapter(Context context, int resource, int textViewResourceId, List<RowData> objects) {
	    super(context, resource, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder = null;
	    TextView title = null;
	    TextView detail = null;
	    ImageView i11 = null;
	    RowData rowData = getItem(position);
	    if (null == convertView) {
		convertView = mInflater.inflate(R.layout.list, null);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
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