package edu.purdue.cs.vw;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import edu.purdue.cs.vw.server.Server;
import edu.purdue.cs.vw.server.ServerReal;

public class BaseActivity extends Activity {

    protected static Server server = null;
    protected static ReadThread readThread = null;
    protected static int portNumber=4242;
    protected static String serverName = "pc.cs.purdue.edu";
    protected static ArrayList<ChannelItem> channels;
    protected Handler h=null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Log.d(this.getClass().getSimpleName(), "onCreate");
	if(h==null)
	    h=new Handler();
	if(channels==null)
	    channels = new ArrayList<ChannelItem>();
    }

    @Override
    protected void onResume() {
	super.onResume();
	Log.d(this.getClass().getSimpleName(), "onResume");
	fetchPreferenceData();
	if (server == null || !server.isConnected())
	    initServer();
	if(server==null){
	    Tabs.setStatus("Error Connecting to Server");
	    channels.clear();
	}else{
	    if(readThread==null || !readThread.isRunning()){
		readThread=null;
		readThread = new ReadThread(server,this,channels);
		readThread.start();
		try{server.sendMessage("GETLIST");}catch(Exception e){}
	    }
	}
	Tabs.setStatus("");
    }
    
    public void initServer(){
	if(server!=null){
	    try {server.resetSocket(serverName,portNumber);} catch (Exception e) {server=null;}
	}
	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	server = new ServerReal(serverName, portNumber, cm);
	Log.d("Server", "Intializing Server");
    }
    
    protected void fetchPreferenceData() {
	Tabs.setStatus("Fetching preference data...");
	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
	String serverNamePref = pref.getString("serverPref", "pc.cs.purdue.edu");
	String portNumberPref = pref.getString("editTextPref", "4242");
	serverName = serverNamePref;
	portNumber = Integer.parseInt(portNumberPref);
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<ChannelItem> getChannels(){
	ArrayList<ChannelItem> clone = null;
	synchronized(channels){
	    clone = (ArrayList<ChannelItem>)channels.clone();
	}
	return clone;
    }
    
    public Handler getHandler(){
	return h;
    }
    
    public void clear(){
	channels.clear();
    }
    
    public void doToast(String message) {
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
