package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import edu.purdue.cs.vw.R;

/*This file sets up the splash screen when the application starts.
 */

public class Splash extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash);
	
	// Lock the screen orientation to portrait mode.  (Problems when rotating?)
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	// Creates a runnable thread in the background. Splash screen appears
	// for a few seconds and then switches to Menu.java
	Thread splashThread = new Thread() {
	    @Override
	    public void run() {
		try {
		    int waited = 0;
		    while (waited < 2000) {
			sleep(100);
			waited += 100;
		    }
		} catch (InterruptedException e) {
		    // do nothing
		} finally {
		    finish();

		    Intent i = new Intent();
		    i.setClass(Splash.this, Menu.class);
		    Splash.this.startActivity(i);

		}
	    }
	};
	splashThread.start();
    }
}
