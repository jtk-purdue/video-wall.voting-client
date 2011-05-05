package edu.purdue.cs.vw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class ServerReal implements Server {
	Socket requestSocket;
	PrintWriter out;
	BufferedReader in;
	String serverLocation;
	int portnum;
	ConnectivityManager cm;

	ServerReal(String serverLocation, int portnum, ConnectivityManager cm) {
		requestSocket = null;
		this.serverLocation = serverLocation;
		this.portnum = portnum;
		this.cm = cm;
	}

	void openSocket() throws ConnectException {
		if (!haveInternetConnection()) {
			Log.d("ServerReal", "No Internet connection");
			throw new ConnectException("No Internet");
		}
		try {
			requestSocket = new Socket(serverLocation, portnum);
			Log.d("Connection", "Connected to localhost in port " + portnum);
			out = new PrintWriter(requestSocket.getOutputStream(), true);
			out.flush();
			in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
			String message = in.readLine();
			Log.d("Server", message);
		} catch (UnknownHostException e) {
			Log.d("Server", "UnknownHostException: " + serverLocation + "@" + portnum);
			requestSocket = null;
		} catch (IOException e) {
			Log.d("Server", "IOException: server down?");
			requestSocket = null;
			throw new ConnectException("Server down?");
		}
	}

	boolean haveInternetConnection() {
		return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
	}

	public void closeSocket() throws IOException {
		in.close();
		out.close();
		requestSocket.close();
	}

	public void sendMessage(String msg) {
		out.println(msg);
		out.flush();
		Log.d("client>", msg);
	}

	public void vote(String name) throws IOException {
		openSocket();
		sendMessage("VOTE");
		sendMessage(name);
		sendMessage("END");
		flushEnd();
		closeSocket();
	}

	// TODO: should be named getList
	public void getList(ArrayList<String> voteList) throws IOException {
		openSocket();
		sendMessage("GET");
		sendMessage("END");
		String message = in.readLine();
		while (!message.equals("END")) {
			voteList.add(message);
			message = in.readLine();
		}
		closeSocket();
		Collections.sort(voteList);
	}

	public void getCount(ArrayList<String> votes) throws IOException {
		openSocket();
		sendMessage("GETCOUNT");
		sendMessage("END");
		String message = in.readLine();
		while (!message.equals("END")) {
			votes.add(message);
			message = in.readLine();
		}
		closeSocket();
	}

	void flushEnd() {
		String message;
		do {
			try {
				message = in.readLine();
			} catch (IOException e) {
				message = "END";
			}
		} while (!message.equals("END"));
	}
}
