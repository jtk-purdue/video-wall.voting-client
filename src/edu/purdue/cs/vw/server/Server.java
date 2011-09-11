package edu.purdue.cs.vw.server;

import java.io.IOException;
import java.net.ConnectException;

import android.content.Context;

public interface Server {
    public final static String TAG = "Server";
    
    public void openSocket() throws ConnectException;

    public void vote(String name,int rank) throws IOException;

    public void sendMessage(String msg) throws IOException;
    
    public void resetSocket(String serverName, int portNumber);
        
    public String readLine() throws IOException;
    
    public boolean isConnected();
    
    public void updateContext(Context c);
    
    public void reconnect();

}