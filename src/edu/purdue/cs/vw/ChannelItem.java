package edu.purdue.cs.vw;

public class ChannelItem implements Comparable<ChannelItem> {
    private String id;
    private int rank;
    private String name;
    private String detail;
    private int personal_rank;

    public ChannelItem(String i, int r, String n) {
	this.setId(i);
	this.setRank(r);
	this.setName(n);
	detail = "no info about this channel yet";
    }

    @Override
    public int compareTo(ChannelItem another) {
	return rank-another.rank;
    }

    @Override
    public String toString() {
	return getId();
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

    public void setId(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }
    
}
