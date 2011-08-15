package edu.purdue.cs.vw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.net.ConnectivityManager;
import android.util.Log;

public class ServerReal implements Server {
    Socket requestSocket;
    PrintWriter out;
    BufferedReader in;
    String serverLocation;
    int portnum;
    ConnectivityManager cm;
    
    public ServerReal() {
	requestSocket = null;
	serverLocation = null;
	portnum = 0;
	cm = null;
    }

    public ServerReal(String serverLocation, int portnum, ConnectivityManager cm) {
	requestSocket = null;
	this.serverLocation = serverLocation;
	this.portnum = portnum;
	this.cm = cm;
	Log.d(Server.TAG, "Intializing Server");
	try{ openSocket();}catch(Exception e){}
    }
    
    public void resetSocket(String serverLocation, int portnum) {
	this.serverLocation = serverLocation;
	this.portnum = portnum;
	try{ closeSocket();}catch(IOException e){Log.d(Server.TAG, "Socket refused to close due to "+e.toString());}
	try{ openSocket();}catch(Exception e){Log.d(Server.TAG, "Socket refused to open due to "+e.toString());}
    }

    public void openSocket() throws ConnectException {
	if (!haveInternetConnection()) {
	    Log.d("ServerReal", "No Internet connection");
	    throw new ConnectException("No Internet");
	}
	
	if(requestSocket==null || !requestSocket.isConnected())
	try {
	    requestSocket = new Socket(serverLocation, portnum);
	    Log.d(Server.TAG, "Connected to " + serverLocation + " in port " + portnum);
	    out = new PrintWriter(requestSocket.getOutputStream(), true);
	    out.flush();
	    in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
	    String message = in.readLine();
	    Log.d(Server.TAG, message);
	} catch (UnknownHostException e) {
	    Log.d(Server.TAG, "UnknownHostException: " + serverLocation + "@" + portnum);
	    requestSocket = null;
	    throw new ConnectException("Unknown host");
	} catch (IOException e) {
	    Log.d(Server.TAG, "IOException: server down?");
	    requestSocket = null;
	    throw new ConnectException("Server down?");
	}
    }

    boolean haveInternetConnection() {
	return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm
		.getActiveNetworkInfo().isConnected());
    }

    void closeSocket() throws IOException {
	in.close();
	out.close();
	requestSocket.close();
	in=null;
	out=null;
	requestSocket=null;
	//to slow take this out
	System.gc();
    }

    public synchronized void sendMessage(String msg) throws IOException {
	openSocket();
	out.println(msg);
	out.flush();
	Log.d("client>", msg);
    }

    synchronized public void vote(String id,int rank) throws IOException {
	openSocket();
	sendMessage("VOTE "+id+" "+rank);
    }

    @Override
    public String readLine(){
	String m = "";
	try{
	    openSocket();
	    m=in.readLine();
	}catch(Exception e){
	    m=null;
	    Log.d(Server.TAG, e.toString());
	}
	return m;
    }
    
    public boolean isConnected(){
	if(requestSocket==null || !requestSocket.isConnected())
	    return false;
	if(in==null || out==null)
	    return false;
	    
	return true;
    }

}
