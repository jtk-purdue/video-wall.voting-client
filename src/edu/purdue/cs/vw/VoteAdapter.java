package edu.purdue.cs.vw;

import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VoteAdapter extends BaseAdapter {
    
    Context ctx;
    ArrayList<ChannelItem> list;
    
    public VoteAdapter(ArrayList<ChannelItem> list,Context ctx){
	this.list=list;
	this.ctx=ctx;
    }

    @Override
    public int getCount() {
	// TODO Auto-generated method stuber
	return list.size();
    }

    @Override
    public Object getItem(int arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long getItemId(int arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
	//LayoutInflater inflater = (LayoutInflater)ctx.getLayoutInflater();
	//view = inflater.inflate(R.layout.addclasslistview, null);
	TextView txt = new TextView(ctx); 
	txt.setText(list.get(position).getName()+" "+list.get(position).getRank());
	txt.setTextSize(30);
	return txt;
    }

}
