package edu.purdue.cs.vw;

import java.io.IOException;
import java.util.ArrayList;

public interface Server {
    public void vote(String name) throws IOException;

    public ArrayList<String> getList() throws IOException;

    public ArrayList<String> getCount() throws IOException;

    public void resetSocket(String serverName, int portNumber);
}