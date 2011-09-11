package edu.purdue.cs.vw.server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import android.content.Context;

public class ServerMock implements Server {
    private static final int NUM_CHANNELS = 100;
    ArrayList<String> voteList;
    ArrayList<String> votes;
    String[] channels;

    public ServerMock() {
	channels = new String[NUM_CHANNELS];
	voteList = new ArrayList<String>();
	votes = new ArrayList<String>();

	for (int i = 0; i < NUM_CHANNELS; i++) {
	    String channel = "Channel " + i;
	    channels[i] = channel;
	    voteList.add(channel);
	    votes.add("0");
	}
    }

    public String[] getChannels() {
	return channels;
    }

    public int getNumChannels() {
	return NUM_CHANNELS;
    }

//    @Override
//    public void vote(String name) {
//	Log.d("MockServerTest", "vote " + name);
//	for (int i = 0; i < channels.length; i++)
//	    if (channels[i].equals(name)) {
//		votes.set(i, String.valueOf(Integer.parseInt(votes.get(i)) + 1));
//		break;
//	    }
//    }
//
//    @Override
//    public ArrayList<String> getList() {
//	Log.d("MockServerTest", "get");
//	return voteList;
//    }
//
//    @Override
//    public ArrayList<String> getCount() {
//	Log.d("MockServerTest", "getCount");
//	return votes;
//    }

    @Override
    public void resetSocket(String serverName, int portNumber) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void vote(String name, int rank) throws IOException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void sendMessage(String msg) throws IOException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String readLine() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void openSocket() throws ConnectException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public boolean isConnected() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void updateContext(Context c) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void reconnect() {
	// TODO Auto-generated method stub
	
    }


}
