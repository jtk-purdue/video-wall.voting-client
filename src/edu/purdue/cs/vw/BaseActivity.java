package edu.purdue.cs.vw;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
    protected int connected=0;
    
    public static final int ERROR = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Log.d(this.getClass().getSimpleName(), "onCreate");

    }

    @Override
    protected void onResume() {
	super.onResume();
	if(h==null)
	    h=new Handler();
	if(channels==null)
	    channels = new ArrayList<ChannelItem>();
	Log.d(this.getClass().getSimpleName(), "onResume");
	fetchPreferenceData();
	//Tabs.setStatus("Connecting to the server.");
	//connect();
    }
    
    public void connect(){
	if (server == null || !server.isConnected())
	    initServer();
	if(server == null ){
	    //Tabs.setStatus("Error Connecting to server.");
	    connected=ERROR;
	    disconnect();
	}else if(!server.isConnected()){
	    connected=ERROR;
	    //Tabs.setStatus("Error Connecting to server.");
	}
	else{
	    server.updateContext(this);
	    if(readThread==null || !readThread.isRunning()){
		readThread=null;
		readThread = new ReadThread(server,this,channels);
		readThread.start();
		try{server.sendMessage("GETLIST");}catch(Exception e){Log.d(Server.TAG, e.toString());}
	    }
	}
	
    }
    
    public boolean isOnline() {
	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo = cm.getActiveNetworkInfo();
	if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    return true;
	}
	return false;
    }
     
    public void initServer(){
	if(server!=null){
	    try {server.resetSocket(serverName,portNumber);} catch (Exception e) {server=null;}
	}else{
	//ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	server = new ServerReal(serverName, portNumber, this);
	}
	Log.d("Server", "Intializing Server");
    }
    
    protected void fetchPreferenceData() {
	//Tabs.setStatus("Fetching preference data...");
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
    
    //overide to added better functionality for when there is no connection to the server
    public void disconnect(){
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
