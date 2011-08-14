package edu.purdue.cs.vw;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import edu.purdue.cs.vw.server.Server;

public class ReadThread extends Thread {

    ArrayList<ChannelItem> list;
    Server server;
    boolean running;
    Activity act;
    Handler h;

    public ReadThread(Server s, final Activity act, Handler h) {
	server = s;
	list = new ArrayList<ChannelItem>();
	running=true;
	this.act=act;
	this.h=h;
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

    //Sends "GETLIST" command to the server, then waits for server input and parses input
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
		h.post(new Runnable(){
		    @Override
		    public void run() {
			TextView txt = (TextView)act.findViewById(android.R.id.empty);
			txt.setText("Error Connecting to Server!");
			txt.setTextSize(30);
			txt.setTextColor(Color.RED);			
		    }
		});
	    }

	}

    }

}
