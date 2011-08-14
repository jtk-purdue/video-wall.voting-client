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
import edu.purdue.cs.vw.adapter.VoteAdapter;
import edu.purdue.cs.vw.server.Server;
import edu.purdue.cs.vw.server.ServerReal;

/*
 * When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and sends across what was voted for.
 */

public class Voting extends ListActivity {
    public static Server server = null;
    private ArrayList<ChannelItem> data;
    private VoteAdapter adapter;
    private String serverPort;
    private int portNumber;
    private String serverName;
    private ReadThread bth;
    private Handler h;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.list);
	
	Log.d("Voting", "onCreate");

	data = new ArrayList<ChannelItem>();
	adapter = new VoteAdapter(data,this);
	setListAdapter(adapter);
	getListView().setTextFilterEnabled(true);
	
	if (server == null) { 
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    server = new ServerReal(serverName, portNumber, cm);
	    Log.d("Server", "Intializing Server");
	}
	
	h=new Handler();
	Runnable r = new Runnable(){
	    @Override
	    public void run() {
		//This may make the screen flash when updating, use notifyDataSetChanged instead
		if(bth!=null){
		data=(ArrayList<ChannelItem>) bth.list.clone();
		adapter=new VoteAdapter(data, Voting.this);
		Voting.this.setListAdapter(adapter);
		//adapter.notifyDataSetChanged();
		bth.list.clear();
		try {
		    server.sendMessage("GETLIST");
		} catch (IOException e) {
		    e.printStackTrace();
		}
		}
		h.postDelayed(this, 5000);
	    }
	}; 
	h.postDelayed(r, 5000);
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
    public void onResume() {
	super.onResume();
	Log.d("Voting", "onResume");
	fetchPreferenceData();
	startReadThread();
    }

    private void startReadThread(){
	Tabs.setStatus("Starting Read Thread...");
	if(bth==null){
	try {
	    server.sendMessage("GETLIST");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	bth = new ReadThread(server,Voting.this,h);
	bth.start();
	}
	Tabs.setStatus("");
    }

    public void onListItemClick(ListView parent, View v, final int position, long id){
	String vote = data.get(position).getId();
	registerServerVote(vote,1);
    }
    
    private void registerServerVote(final String vote,int rank) {
	try {
	    server.vote(vote, rank);
	} catch (IOException e) {e.printStackTrace();}
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