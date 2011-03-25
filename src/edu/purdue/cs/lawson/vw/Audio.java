package edu.purdue.cs.lawson.vw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.purdue.cs.lawson.vw.R;

/*This activity will allow the user to stream audio from a screen (or a set of screens) 
 * to his/her phone. 
 */

public class Audio extends Activity {
	private static final String TAG = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);
		//String url = "http://live.wx5fwd.net/voipwx.mp3";
		 //MediaPlayer mp = new MediaPlayer();
		 try
		 {
		 //mp.setDataSource(url);
		 //mp.prepare();
		// mp.start();
		 }
		 catch(Exception e)
		 {
			// Log.i("Exception", "Exception in streaming mediaplayer e = " + e);
		 }
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// boolean internet=checkInternetConnection();
	}

	// Will check if the phone is connected to the internet through Wifi or 3g
	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			Log.d("Connection State", "Internet Connection Not Present");
			return false;
		}
	}

	// @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu((android.view.Menu) menu);
	}

	// @Override
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// Performs appropriate action when Menu option is selected.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.help:
			Intent i = new Intent();
			i.setClass(Audio.this, Help.class); // Switch to help activity
			startActivity(i);
			break;

		case R.id.info:
			Intent i2 = new Intent();
			i2.setClass(Audio.this, Information.class); // Switch to info
														// activity
			startActivity(i2);
			break;

		case R.id.settings:
			Intent i3 = new Intent();
			i3.setClass(Audio.this, Settings.class); // Switch to settings
														// activity
			startActivity(i3);
			break;

		case R.id.quit:
			finish(); // Finish the current activity
			break;
		}
		return true;
	}
}