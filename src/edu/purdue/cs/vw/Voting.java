package edu.purdue.cs.vw;

import android.os.Bundle;
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
	    Button b = (Button)findViewById(R.id.retry);
	    lv.setVisibility(View.GONE);
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
	
    }
    
    public void clear(){
	super.clear();
	va.notifyDataSetChanged();
    }

//    public void onListItemClick(ListView parent, View v, final int position, long id){
//	String vote = data.get(position).getId();
//	registerServerVote(vote,1);
//    }
//    
//    private void registerServerVote(final String vote,int rank) {
//	try {
//	    server.vote(vote, rank);
//	} catch (IOException e) {e.printStackTrace();}
//    }

}