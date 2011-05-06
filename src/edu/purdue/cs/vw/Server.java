package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;

public interface Server {
	public void sendMessage(String msg);
	public void vote(String name) throws IOException;
	public void getList(ArrayList<String> voteList) throws IOException;
	public void getCount(ArrayList<String> votes) throws IOException;
}
