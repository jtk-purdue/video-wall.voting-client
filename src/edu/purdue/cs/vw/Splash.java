package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import edu.purdue.cs.vw.R;

/*
 * This file sets up the splash screen when the application starts.
 */

public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash);

	// Lock the screen orientation to portrait mode. (Problems when rotating?)
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
    @Override
    public void onResume() {
	super.onResume();

	// Creates a runnable thread in the background. Splash screen appears
	// for a few seconds and then switches to Tabs.
	new AsyncTask<Void, Void, Void>() {
	    @Override
	    protected Void doInBackground(Void... params) {
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) { /* ignore */
		}

		Intent i = new Intent();
		i.setClass(Splash.this, Tabs.class);
		startActivity(i);
		return null;
	    }
	}.execute();
    }
}
