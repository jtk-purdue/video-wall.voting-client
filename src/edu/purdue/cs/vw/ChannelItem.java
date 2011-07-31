package edu.purdue.cs.vw;

public class ChannelItem implements Comparable<ChannelItem> {
    String id;
    int rank;
    String name;
    String detail;

    public ChannelItem(String i, int r, String n) {
	this.id = i;
	this.rank = r;
	this.name = n;
	detail = "no info about this channel yet";
    }

    @Override
    public int compareTo(ChannelItem another) {
	return rank-another.rank;
    }

    @Override
    public String toString() {
	return id;
    }

    public void setDetail(String item) {
	detail = item;
    }
    
}
