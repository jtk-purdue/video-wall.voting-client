package edu.purdue.cs.vw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/*
 * This file sets up the splash screen when the application starts.
 */

public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash);
    }
    
    @Override
    public void onResume() {
	super.onResume();
	
	Handler h = new Handler();
	h.postDelayed(new Runnable(){
	    @Override
	    public void run() {
		Splash.this.finish();		
	    }
	}, 1000);

    }
}
