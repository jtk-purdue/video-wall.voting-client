package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public interface Server {
    public void vote(String name,int rank) throws IOException;

    public ArrayList<ChannelItem> getList() throws IOException;

    public void resetSocket(String serverName, int portNumber);
}