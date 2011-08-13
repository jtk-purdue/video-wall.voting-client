package edu.purdue.cs.vw;

import java.util.ArrayList;
import java.util.Collections;

import android.util.Log;
import edu.purdue.cs.vw.server.Server;

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

	String m = new String();	
	while (running) {
	    Log.d("Server", m);
	    m = server.readLine();
	    if(m!=null)
		processServerInput(m);
	    else{
		running=false;
		
	    }

	}

    }

}
