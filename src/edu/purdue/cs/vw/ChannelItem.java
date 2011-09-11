package edu.purdue.cs.vw;

public class ChannelItem implements Comparable<ChannelItem> {
    private String id;
    private int rank;
    private String name;
    private String details;
    private int personal_rank;

    public ChannelItem(String i, int r, String n) {
	this.setId(i);
	this.setRank(r);
	this.setName(n);
	this.setPersonalRank(-1);
	details = "no info about this channel yet";
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
	details = item;
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
    
    public String getDetails(){
	return details;
    }
    public void setDetials(String d){
	details=d;
    }
    
    public int getPersonalRank(){
	return personal_rank;
    }
    public void setPersonalRank(int p){
	personal_rank=p;
    }
}
