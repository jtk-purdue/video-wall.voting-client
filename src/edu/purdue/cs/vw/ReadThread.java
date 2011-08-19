package edu.purdue.cs.vw;

import java.util.ArrayList;
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
	String[] line = input.split(" ");
	if (line[0].equals("CHANNEL")) {
	    String name = "";
	    for (int i = 3; i < line.length; i++)
		name += line[3] + " ";
	    list.add(new ChannelItem(line[1], Integer.parseInt(line[2]), name));
	    Collections.sort(list);
	} else if (line[0].equals("RANK")) {
	    for (int i = 0; i < list.size(); i++)
		if (list.get(i).getId().equals(line[1])) {
		    list.get(i).setRank(Integer.parseInt(line[2]));
		}
	}
    }

    //waits for server input and parses input
    public void run() {
	String m = new String();	
	while (running) {
	    Log.d("Server", "Server Loop Starting");
	    m = server.readLine();
	    if(m!=null){
		processServerInput(m);
		Log.d("Server", m);
	    }else{
		Log.d("Server", "Server Returned Null");
		running=false;
		h.post(new  Runnable(){
		    public void run(){
			Tabs.setStatus("Please Recoonect to Internet");
			act.clear();
		    }
		});
	    }
	}
    }
    
    public boolean isRunning(){
	return running;
    }

}
