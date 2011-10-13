package edu.purdue.cs.vw;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Admin extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.admin);

    }

    @Override
    protected void onResume() {
	super.onResume();
	Button on = (Button)findViewById(R.id.button1);
	Button off = (Button)findViewById(R.id.button2);
	on.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		try {
		    server.sendMessage("POWER ON");
		} catch (Exception e) {
		    Log.d("Server", "error in Admin: "+e.toString());
		    e.printStackTrace();
		}
	    }    
	});
	off.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		try {
		    server.sendMessage("POWER OFF");
		} catch (Exception e) {
		    Log.d("Server", "error in Admin: "+e.toString());
		    e.printStackTrace();
		}
	    }    
	});
    }

}
