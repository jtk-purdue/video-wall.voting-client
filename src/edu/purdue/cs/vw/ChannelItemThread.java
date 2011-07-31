package edu.purdue.cs.vw;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ChannelItemThread extends Thread {

    ArrayList<ChannelItem> list;
    Server server;
    boolean running;

    public ChannelItemThread( Server s) {
	server = s;
	list = new ArrayList<ChannelItem>();
	running=true;
    }

    public void processServerInput(String input) {
	String[] line = input.split(" ");
	if (line[0].equals("CHANNEL")) {
	    String name = "";
	    for (int i = 3; i < line.length; i++)
		name += line[3] + " ";
	    list.add(new ChannelItem(line[1], Integer.parseInt(line[2]), name));
	    Collections.sort(list);
	} else if (line[0].equals("RANK")) {
	    for (int i = 0; i < list.size(); i++)
		if (list.get(i).id.equals(line[1])) {
		    list.get(i).rank = Integer.parseInt(line[2]);
		}
	}
    }

    //Sends "GETLIST" command to the server, then waits for server input and parses input
    public void run() {
	//Sending "GETLIST" command to server
	//boolean connected=false;
	//do{
	try {
	    server.sendMessage("GETLIST");
	  //  connected=true;
	} catch (Exception e1) {
	    Log.d("Server", "GETLIST command to server failed."+e1);
	   // connected=false;
	}
	//if(!connected)
	  //  try{Thread.sleep(50);}catch(Exception e){}
	//}while(!connected);

	String message = new String();
	BufferedReader in = server.getBufferedReader();
	try {
	    message = in.readLine();
	} catch (IOException e1) {
	    Log.d("Server", "in.readline() has broken "+e1);
	}

	while (running) {
	    Log.d("Server", message);
	    processServerInput(message);
	    try {
		message = in.readLine();
	    } catch (Exception e) {
		Log.d("Server", "failing to read line"+e);
	    }
	}

    }

}
