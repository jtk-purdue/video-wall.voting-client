package edu.purdue.cs.vw;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import edu.purdue.cs.vw.adapter.VoteAdapter;
import edu.purdue.cs.vw.server.Server;

/*
 * When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and sends across what was voted for.
 */

public class Voting extends BaseActivity {
    
    private VoteAdapter va;
    private ListView lv;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.list);
    }

    @Override
    protected void onResume() {
	super.onResume();
	
	if(null==lv){
	    lv = (ListView)findViewById(android.R.id.list);
	}
		
	
	if(va==null){
	    va = new VoteAdapter(channels,this);
	    lv.setAdapter(va);
	}
	
	if(server==null || !server.isConnected()){
	    lv.setVisibility(View.GONE);
	    Button b = (Button)findViewById(R.id.retry);
	    b.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
		    connect();
		    if(server!=null && server.isConnected()){
			lv.setVisibility(View.VISIBLE);
			va.notifyDataSetChanged();
		    }
		}
	    });
	}else{
	    if(lv.getVisibility()==View.GONE){
		lv.setVisibility(View.VISIBLE);
		va.notifyDataSetChanged();
	    }
	}
	//update list list newest info from server
	Runnable r = new Runnable(){
	    @Override
	    public void run() {
		if(lv.getVisibility()==View.VISIBLE){
			va.notifyDataSetChanged();
			h.postDelayed(this, 2000);
		}
	    }
	};
	h.postDelayed(r, 2000);
    }
    
    public void disconnect(){
	super.disconnect();
	if(va!=null)
	    va.notifyDataSetChanged();
	lv.setVisibility(View.GONE);
    }
    
    public void retry(View v){
	Toast.makeText(this, "Test for reconection button", Toast.LENGTH_SHORT).show();
    }
    
    public void registerServerVote(final String vote,int rank) {
	try {
	    server.vote(vote, rank);
	    Log.d(Server.TAG, "Voted for "+vote);
	} catch (Exception e) {Log.d(Server.TAG, "Error: "+e.toString());}
    }

}