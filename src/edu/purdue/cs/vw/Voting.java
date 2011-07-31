package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/*
 * When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and sends across what was voted for.
 */

public class Voting extends ListActivity {
    private ServerReal server = null;
    private ArrayList<ChannelItem> data;
    public VoteAdapter adapter;
    private String serverPort;
    private int portNumber;
    private String serverName;
    private ChannelItemThread bth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.list);

	Log.d("Voting", "onCreate");

	data = new ArrayList<ChannelItem>();
	adapter = new VoteAdapter(data,this);
	setListAdapter(adapter);
	getListView().setTextFilterEnabled(true);
	fetchPreferenceData();
	
	if (server == null) { 
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    server = new ServerReal(serverName, portNumber, cm);
	}
	
	final Handler h=new Handler();

	
	Runnable r = new Runnable(){

	    @Override
	    public void run() {
		//This may make the screen flash when updating, use notifyDataSetChanged instead
		if(bth!=null){
		data=(ArrayList<ChannelItem>) bth.list.clone();
		adapter=new VoteAdapter(data, Voting.this);
		Voting.this.setListAdapter(adapter);
		//adapter.notifyDataSetChanged();
		}
		h.postDelayed(this, 2000);
	    }
	    
	};
	   
	h.postDelayed(r, 2000);

	
    }

    void fetchPreferenceData() {
	Tabs.setStatus("Fetching preference data...");
	
	SharedPreferences serverPref = PreferenceManager.getDefaultSharedPreferences(this);
	SharedPreferences portPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

	String serverNamePref = serverPref.getString("serverPref", "pc.cs.purdue.edu");
	String portNumberPref = portPref.getString("editTextPref", "4242");

	if(server!=null)
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
	//stopGatherThread();
    }

    @Override
    public void onResume() {
	super.onResume();
	Log.d("Voting", "onResume");
	fetchPreferenceData();
	startChannelThread();
    }

    private void startChannelThread(){
	Tabs.setStatus("");
	if(bth==null){
	bth = new ChannelItemThread(server);
	bth.start();
	}
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

    public void onListItemClick(ListView parent, View v, final int position, long id){
	String vote = data.get(position).id;
	registerServerVote(vote,1);
    }

    private void registerServerVote(final String vote,int rank) {
	try {
	    server.vote(vote, rank);
	} catch (IOException e) {e.printStackTrace();}
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
    public boolean onPrepareOptionsMenu(Menu menu){
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