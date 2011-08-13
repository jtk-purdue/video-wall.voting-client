package edu.purdue.cs.vw;

public class ChannelItem implements Comparable<ChannelItem> {
    String id;
    private int rank;
    private String name;
    String detail;

    public ChannelItem(String i, int r, String n) {
	this.id = i;
	this.setRank(r);
	this.setName(n);
	detail = "no info about this channel yet";
    }

    @Override
    public int compareTo(ChannelItem another) {
	return getRank()-another.getRank();
    }

    @Override
    public String toString() {
	return id;
    }

    public void setDetail(String item) {
	detail = item;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setRank(int rank) {
	this.rank = rank;
    }

    public int getRank() {
	return rank;
    }
    
}
