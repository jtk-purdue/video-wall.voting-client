package edu.purdue.cs.vw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.os.Handler;
import android.util.Log;
import edu.purdue.cs.vw.server.Server;

public class ReadThread extends Thread {

    ArrayList<ChannelItem> list;
    Server server;
    boolean running;
    BaseActivity act;
    Handler h;

    public ReadThread(Server s, BaseActivity act,ArrayList<ChannelItem> l) {
	server = s;
	list = l;
	running=true;
	this.act=act;
	this.h=act.getHandler();
    }

    public void processServerInput(String input) {
	//handle null case of server
	server.setResponse(1);
	if(input == null) return;
	String[] line = input.split(" ");
	if (line[0].equals("CHANNEL")) {
	    String name = "";
	    for (int i = 3; i < line.length; i++)
		name += line[i] + " ";
	    list.add(new ChannelItem(line[1], Integer.parseInt(line[2]), name));
	    Collections.sort(list);
	} else if (line[0].equals("RANK")) {
	    for (int i = 0; i < list.size(); i++)
		if (list.get(i).getId().equals(line[1])) {
		    list.get(i).setRank(Integer.parseInt(line[2]));
		}
	    Collections.sort(list);
	}
    }

    //waits for server input and parses input
    public void run() {
	String m = new String();
	boolean process = true;
	while (running) {
	    Log.d(Server.TAG, "Server Loop Starting");
	    try{m = server.readLine();}catch(Exception e){process=false; Log.d(Server.TAG, " readLine throws this error"+e.toString());}
	    if(m==null){
		server.reconnect();
	    }
	    if(process){
		processServerInput(m);
		if(m!=null)
		Log.d("Server", m);
	    }else{
		Log.d(Server.TAG, "Error Connecting to server.");
		running=false;
		h.post(new  Runnable(){
		    public void run(){
			Tabs.setStatus("Please reconect to Internet");
			act.disconnect();
		    }
		});
	    }
	}
    }
    
    public boolean isRunning(){
	return running;
    }

}
