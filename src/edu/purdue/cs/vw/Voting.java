package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
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
    public static ArrayList<String> voteList;
    public static ArrayList<String> votes;

    private Server server = null;
    private Vector<VoteData> data;
    private VoteDataAdapter adapter;
    private String serverPort;
    private int portNumber;
    private String serverName;
    
    public Server getServer() {
	return server;
    }
    
    public void setServer(Server server) {
	this.server = server;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Log.d("Voting", "onCreate");

	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	setContentView(R.layout.list);

	data = new Vector<VoteData>();
	adapter = new VoteDataAdapter(this, R.layout.list_item, data);
	setListAdapter(adapter);
	getListView().setTextFilterEnabled(true);
    }

    void fetchPreferenceData() {
	Tabs.setStatus("Fetching preference data...");
	
	SharedPreferences serverPref = PreferenceManager.getDefaultSharedPreferences(this);
	SharedPreferences portPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

	String serverNamePref = serverPref.getString("serverPref", "pc.cs.purdue.edu");
	String portNumberPref = portPref.getString("editTextPref", "4242");

	if (!(serverNamePref.equals(serverName)) || !(portNumberPref).equals(serverPort)) {
	    serverName = serverNamePref;
	    serverPort = portNumberPref;
	    portNumber = Integer.parseInt(serverPort);
	    server = null;  // force server reset below
	}

	if (server == null) { 
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    server = new Server(serverName, portNumber, cm);
	}
    }

    void doToast(String message) {
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
	fetchServerData();
    }

    private void fetchServerData() {
	AsyncTask<Void, String, Boolean> handleServer = new AsyncTask<Void, String, Boolean>() {
	    @Override
	    protected Boolean doInBackground(Void... params) {
		try {
		    publishProgress("Contacting server...");
		    voteList = server.getList();
		    publishProgress("Fetched vote list...");
		    votes = server.getCount();
		    publishProgress("Fetched vote count...");
		} catch (IOException e) {
		    voteList = null; // TODO: Set votes to null also? bit of a kludge to indicate
		    publishProgress("Failed to connect to server...");
		    return false;
		}
		return true;
	    }

	    @Override
	    protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	    }

	    @Override
	    protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);
		if (success) {
		    data.clear();
		    if (voteList == null)
			Log.e("Voting", "voteList is null in updateData");
		    else {
			Log.d("Voting", "updateData with " + voteList.size() + " votable items");
			for (int i = 0; i < voteList.size(); i++)
			    data.add(new VoteData(voteList.get(i), votes.get(i)));
		    }
		    adapter.notifyDataSetChanged();
		} else {
		    Log.d("Voting", "failure in onPostExecute");
		}
		publishProgress("");
	    }

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	    }

	    @Override
	    protected void onProgressUpdate(String... messages) {
		Tabs.setStatus(messages[0]);
		super.onProgressUpdate(messages);
	    }
	};

	handleServer.execute();
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
	String vote = data.get(position).title;
	registerServerVote(vote);
    }

    private void registerServerVote(final String vote) {
	AsyncTask<Void, String, Boolean> handleServer = new AsyncTask<Void, String, Boolean>() {
	    @Override
	    protected Boolean doInBackground(Void... params) {
		try {
		    publishProgress("Registering vote...");
		    server.vote(vote);
		    publishProgress("Getting counts...");
		    votes = server.getCount();
		    // TODO: if server is down, voteList might be null, but call to getCount above generates exception,
		    // skipping
		    // .size()
		    publishProgress("Voted for " + vote + ".");
		} catch (IOException e) {
		    publishProgress("Voting failed...");
		    return false;
		}
		return true;
	    }

	    @Override
	    protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	    }

	    @Override
	    protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);
		if (success) {
		    // TODO: code assumes the voteList and data list are the same size (in sync)
		    for (int i = 0; i < voteList.size(); i++) {
			VoteData voteData = (VoteData) data.elementAt(i);
			voteData.setDetail(votes.get(i));
		    }
		    adapter.notifyDataSetChanged();
		} else {
		    Log.d("Voting", "voting failure in onPostExecute");
		}
		publishProgress("");
	    }

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	    }

	    @Override
	    protected void onProgressUpdate(String... messages) {
//		emptyMessage.setText(messages[0]);
		Tabs.setStatus(messages[0]);
		super.onProgressUpdate(messages);
	    }
	};

	handleServer.execute();
    }

    private class VoteData {
	protected String title;
	protected String detail;

	VoteData(String title, String detail) {
	    this.title = title;
	    this.detail = detail;
	}

	@Override
	public String toString() {
	    return title + " " + detail;
	}

	public void setDetail(String item) {
	    detail = item;
	}
    }

    private class VoteDataAdapter extends ArrayAdapter<VoteData> {
	LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	int layoutId;

	public VoteDataAdapter(Context context, int layoutId, List<VoteData> voteData) {
	    super(context, layoutId, voteData);
	    this.layoutId = layoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    class ViewData {
		TextView title;
		TextView detail;
		ImageView icon;

		ViewData(View row) {
		    title = (TextView) row.findViewById(R.id.title);
		    detail = (TextView) row.findViewById(R.id.detail);
		    icon = (ImageView) row.findViewById(R.id.img);
		}
	    }
	    ViewData viewData;

	    if (convertView != null)
		viewData = (ViewData) convertView.getTag();
	    else {
		convertView = inflater.inflate(layoutId, null);
		viewData = new ViewData(convertView);
		convertView.setTag(viewData);
	    }

	    VoteData voteData = getItem(position);

	    viewData.title.setText(voteData.title);
	    viewData.detail.setText("Number of votes: " + voteData.detail);
	    viewData.icon.setImageResource(R.drawable.voteicon);

	    return convertView;
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
	inflater.inflate(R.menu.menu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	return Tabs.doOptionsItemSelected(this, item);
	
//	Intent i = new Intent();
//	switch (item.getItemId()) {
//	case R.id.help:
//	    i.setClass(Voting.this, Help.class);
//	    startActivity(i);
//	    break;
//
//	case R.id.info:
//	    i.setClass(Voting.this, Information.class);
//	    startActivity(i);
//	    break;
//
//	case R.id.stats:
//	    Intent i2 = new Intent();
//	    i2.putExtra("votes", voteList);
//	    i2.putExtra("votesint", votes);
//	    i2.setClass(Voting.this, Stats.class);
//	    startActivity(i2);
//	    break;
//
//	case R.id.settings:
//	    Intent i3 = new Intent();
//	    i3.setClass(Voting.this, Settings.class);
//	    startActivity(i3);
//	    break;
//
//	case R.id.quit:
//	    finish();
//	    break;
//	}
//	return true;
    }
}