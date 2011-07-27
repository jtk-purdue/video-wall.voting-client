package edu.purdue.cs.vw;

public class ChannelItem implements Comparable<ChannelItem> {
    String id;
    double rank;
    String name;
    String detail;

    public ChannelItem(String i, double r, String n) {
	this.id = i;
	this.rank = r;
	this.name = n;
	detail = "no info about this channel yet";
    }

    @Override
    public int compareTo(ChannelItem another) {
	return this.name.compareTo(another.name);
    }

    @Override
    public String toString() {
	return name + " " + detail;
    }

    public void setDetail(String item) {
	detail = item;
    }
}
