package edu.purdue.cs.vw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

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
    }
    
    public void resetSocket(String serverLocation, int portnum) {
	this.serverLocation = serverLocation;
	this.portnum = portnum;
    }

    void openSocket() throws ConnectException {
	if (!haveInternetConnection()) {
	    Log.d("ServerReal", "No Internet connection");
	    throw new ConnectException("No Internet");
	}
	if(requestSocket==null || requestSocket.isConnected())
	try {
	    requestSocket = new Socket(serverLocation, portnum);
	    Log.d("Connection", "Connected to " + serverLocation + " in port " + portnum);
	    out = new PrintWriter(requestSocket.getOutputStream(), true);
	    out.flush();
	    in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
	    String message = in.readLine();
	    Log.d("Server", message);
	} catch (UnknownHostException e) {
	    Log.d("Server", "UnknownHostException: " + serverLocation + "@" + portnum);
	    requestSocket = null;
	    throw new ConnectException("Unknown host");
	} catch (IOException e) {
	    Log.d("Server", "IOException: server down?");
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
    }

    void sendMessage(String msg) {
	out.println(msg);
	out.flush();
	Log.d("client>", msg);
    }

    synchronized public void vote(String id,int rank) throws IOException {
	openSocket();
	sendMessage("VOTE "+id+" "+rank);
	sendMessage("END");
	flushEnd();
    }

    synchronized public ArrayList<ChannelItem> getList() throws IOException {
	ArrayList<ChannelItem> voteList = new ArrayList<ChannelItem>();

	openSocket();
	sendMessage("GETLIST");
	sendMessage("END");

	int i=0;
	String message = in.readLine();
	while (!message.contains("END")) {
	    i++;
	    String[] line = message.split(" ");
	    Log.d("Server", message);
	    String name ="";
	    for(int c=3;c<line.length;c++)
		name+=line[c]+" ";
	    if(i>7)
		break;
	    voteList.add(new ChannelItem(line[1],(Double.parseDouble(line[2])),name));
	    message = in.readLine();
	}
	Collections.sort(voteList);
	
	return voteList;
    }

    void flushEnd() {
	String message;
	do {
	    try {
		message = in.readLine();
	    } catch (IOException e) {
		message = "END";
	    }
	} while (!message.equals("END"));
    }
}
