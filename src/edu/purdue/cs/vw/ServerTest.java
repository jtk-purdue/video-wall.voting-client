package edu.purdue.cs.vw;

import java.util.ArrayList;

import android.util.Log;

public class ServerTest implements Server {
	ServerTest() {
		
	}

	@Override
	public void sendMessage(String msg) {
		// TODO Auto-generated method stub
		Log.d("ServerTest", "sendMessage " + msg);
	}

	@Override
	public void vote(String name) {
		// TODO Auto-generated method stub
		Log.d("ServerTest", "vote " + name);
	}

	@Override
	public ArrayList<String> getList() {
		Log.d("ServerTest", "get");
		ArrayList<String> voteList = new ArrayList<String>();
		voteList.add("Hello There");
		return voteList;
	}

	@Override
	public ArrayList<String> getCount() {
		Log.d("ServerTest", "getCount");
		ArrayList<String> votes= new ArrayList<String>();
		votes.add("42");
		return votes;
	}
}
