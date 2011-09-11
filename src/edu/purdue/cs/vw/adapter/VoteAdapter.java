package edu.purdue.cs.vw.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import edu.purdue.cs.vw.ChannelItem;
import edu.purdue.cs.vw.R;
import edu.purdue.cs.vw.Voting;

public class VoteAdapter extends BaseAdapter {
    
    Voting ctx;
    ArrayList<ChannelItem> list;
    
    public VoteAdapter(ArrayList<ChannelItem> list,Voting ctx){
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
    public View getView(final int position, View arg1, ViewGroup arg2) {
	LayoutInflater inflater = (LayoutInflater)ctx.getLayoutInflater();
	View view = inflater.inflate(R.layout.votinglistitem, null);
	final ChannelItem item =list.get(position);
	TextView txt = (TextView)view.findViewById(R.id.text); 
	txt.setText(item.getName());
	txt.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		String vote = item.getId();
		ctx.registerServerVote(vote,1);
	    }
	});
	RadioGroup k =(RadioGroup) view.findViewById(R.id.radioGroup1);
	k.setOnCheckedChangeListener(new OnCheckedChangeListener(){
	    @Override
	    public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch(checkedId){
		  case R.id.radio0:
		      if(item.getPersonalRank()!=1){
		      checkAndErase(1);
		      item.setPersonalRank(1);
		      ctx.registerServerVote(item.getId(),1);
		      }
		      break;
		  case R.id.radio1:
		      if(item.getPersonalRank()!=2){
		      checkAndErase(2);
		      item.setPersonalRank(2);
		      ctx.registerServerVote(item.getId(),2);
		      }
		      break;
		  case R.id.radio2:
		      if(item.getPersonalRank()!=3){
		      checkAndErase(3);
		      item.setPersonalRank(3);
		      ctx.registerServerVote(item.getId(),3);
		      }
		      break;
		  case R.id.radio3:
		      if(item.getPersonalRank()!=4){
		      checkAndErase(4);
		      item.setPersonalRank(4);
		      ctx.registerServerVote(item.getId(),4);
		      }
		      break;
		}
	    }
	});
	
	RadioButton rb = null;
	switch(item.getPersonalRank()){
	  case 1:
	      rb=(RadioButton)view.findViewById(R.id.radio0);
	      rb.setChecked(true);
	      break;
	  case 2:
	      rb=(RadioButton)view.findViewById(R.id.radio1);
	      rb.setChecked(true);
	      break;
	  case 3:
	      rb=(RadioButton)view.findViewById(R.id.radio2);
	      rb.setChecked(true);
	      break;
	  case 4:
	      rb=(RadioButton)view.findViewById(R.id.radio3);
	      rb.setChecked(true);
	      break;
	}
	
	return view;
    }

    @Override
    public void notifyDataSetChanged() {
	synchronized(list){
	super.notifyDataSetChanged();
	}
    }
    
    public void checkAndErase(int rank){
	for(int i=0;i<list.size();i++){
	    if(list.get(i).getPersonalRank()==rank){
		list.get(i).setPersonalRank(-1);
	    }
	}
    }

}
