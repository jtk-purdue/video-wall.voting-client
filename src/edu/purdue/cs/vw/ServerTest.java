package edu.purdue.cs.vw;

import java.util.ArrayList;

import android.util.Log;

public class ServerTest implements Server {
    ServerTest() {

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
	voteList.add("ABC");
	voteList.add("CBS");
	voteList.add("FOX");
	voteList.add("NBC");
	return voteList;
    }

    @Override
    public ArrayList<String> getCount() {
	Log.d("ServerTest", "getCount");
	ArrayList<String> votes = new ArrayList<String>();
	votes.add("0");
	votes.add("0");
	votes.add("0");
	votes.add("0");
	return votes;
    }

    @Override
    public void waitForData() {
	// TODO Auto-generated method stub
	return;
    }
}
