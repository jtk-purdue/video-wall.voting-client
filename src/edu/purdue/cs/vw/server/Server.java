package edu.purdue.cs.vw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public interface Server {    
    public void vote(String name,int rank) throws IOException;

    public void sendMessage(String msg) throws IOException;
    
    public void resetSocket(String serverName, int portNumber);
    
    public String readLine();
}