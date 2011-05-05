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
	public void getList(ArrayList<String> voteList) {
		// TODO Auto-generated method stub
		Log.d("ServerTest", "get");
		voteList.add("Hello There");
	}

	@Override
	public void getCount(ArrayList<String> votes) {
		// TODO Auto-generated method stub
		Log.d("ServerTest", "getCount");
		votes.add("42");
	}
}
