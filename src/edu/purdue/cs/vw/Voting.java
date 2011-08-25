package edu.purdue.cs.vw;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import edu.purdue.cs.vw.adapter.VoteAdapter;

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

    /* (non-Javadoc)
     * @see edu.purdue.cs.vw.BaseActivity#onResume()
     */
    @Override
    protected void onResume() {
	super.onResume();
	
	if(null==lv)
	    lv = (ListView)findViewById(android.R.id.list);
	
	if(va==null){
	    va = new VoteAdapter(channels,this);
	    lv.setAdapter(va);
	}
	
	if(server==null || !server.isConnected()){
	    //lv.setVisibility(View.GONE);
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
	Runnable r = new Runnable(){
	    @Override
	    public void run() {
		va.notifyDataSetChanged();
		h.postDelayed(this, 2000);
	    }
	};
	h.postDelayed(r, 2000);
    }
    
    public void clear(){
	super.clear();
	if(va!=null)
	    va.notifyDataSetChanged();
    }

    public void onListItemClick(ListView parent, View v, final int position, long id){
	if(lv.getVisibility()==View.VISIBLE){
	    String vote = channels.get(position).getId();
	    registerServerVote(vote,1);
	}
    }
    
    private void registerServerVote(final String vote,int rank) {
	try {
	    server.vote(vote, rank);
	} catch (Exception e) {Log.d("Server", "Error: "+e.toString());}
    }

}