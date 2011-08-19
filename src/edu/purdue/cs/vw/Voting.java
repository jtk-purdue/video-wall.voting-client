package edu.purdue.cs.vw;

import android.os.Bundle;
import android.widget.ListView;
import edu.purdue.cs.vw.adapter.VoteAdapter;

/*
 * When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and sends across what was voted for.
 */

public class Voting extends BaseActivity {
    
    private VoteAdapter va;

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
	
	ListView lv = (ListView)findViewById(android.R.id.list);
	va = new VoteAdapter(channels,this);
	lv.setAdapter(va);
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