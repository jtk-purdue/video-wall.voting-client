package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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

	// Creates a runnable thread in the background. Splash screen appears
	// for a few seconds and then switches to Tabs.
	new AsyncTask<Void, Void, Void>() {
	    @Override
	    protected Void doInBackground(Void... params) {
		try {
		    Thread.sleep(500);
		} catch (InterruptedException e) { /* ignore */
		}

		return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Splash.this.finish();
	    }
	    
	}.execute();
    }
}
