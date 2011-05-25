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
import android.view.Menu;
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
    private ServerReal server = null;
    private Vector<VoteData> data;
    private VoteDataAdapter adapter;
    private String serverPort;
    private int portNumber;
    private String serverName;
    
    public ServerReal getServer() {
	return server;
    }
    
    public void setServer(ServerReal server) {
	this.server = server;
	fetchServerData();
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

	if (server == null) { 
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    server = new ServerReal(serverName, portNumber, cm);
	}
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
	    server.resetSocket(serverName, portNumber);
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
	    public ArrayList<String> voteList;
	    public ArrayList<String> votes;

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
		    notifyDataAvailable();
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
	    public ArrayList<String> votes;

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
		    // TODO: code assumes the votes and data list are the same size (in sync)
		    for (int i = 0; i < votes.size(); i++) {
			VoteData voteData = (VoteData) data.elementAt(i);
			voteData.setDetail(votes.get(i));
		    }
		    adapter.notifyDataSetChanged();
		    notifyDataAvailable();
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
    
    /*
     * Allow external threads (e.g., test threads) to wait for data to have arrived from the server
     * and transferred into the interface.
     */
    
    boolean haveData = false;
    
    synchronized public void notifyDataAvailable() {
	haveData = true;
	notifyAll();
    }

    synchronized public void waitForData() {
	while (!haveData)
	    try {
		wait();
	    } catch (InterruptedException e) {
		Log.e("ServerReal", "exception while waiting for data");
		e.printStackTrace();
	    }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
	return Tabs.doCreateOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	return Tabs.doOptionsItemSelected(this, item);
    }
}