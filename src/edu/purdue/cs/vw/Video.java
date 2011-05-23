package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import edu.purdue.cs.vw.R;

/*
 * This tab allows users to start video streaming from their phone to the video wall. 
 * When the “Start Video” button is clicked it jumps to the CameraPreview Activity.
 */
public class Video extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.video);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	Button button = (Button) findViewById(R.id.startvid);
	button.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// Perform action on click
		Intent i = new Intent();
		i.setClass(Video.this, CameraPreview.class);
		startActivity(i);
	    }
	});
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	return Tabs.doCreateOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	return Tabs.doOptionsItemSelected(this, item);
    }
}
