package edu.purdue.cs.vw;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import edu.purdue.cs.vw.R;

/*
 * This file sets up the tab view that the application uses.
 */

public class Tabs extends TabActivity {
    static TextView status;
    
    public static void setStatus(String s) {
	status.setText(s);
    }
    
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tabs);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
	status = (TextView) findViewById(R.id.status);
	setStatus("Starting up...");
	
	Resources res = getResources(); // Resource object to get Drawables
	TabHost tabHost = getTabHost(); // The activity TabHost
	TabHost.TabSpec spec; // Resusable TabSpec for each tab
	Intent intent; // Reusable Intent for each tab

	// Tab: Team photos and biographies

	intent = new Intent().setClass(this, Bios.class);
	spec = tabHost.newTabSpec("team").setIndicator("Team", res.getDrawable(R.drawable.team_tab)).setContent(intent);
	tabHost.addTab(spec);

	// Tab: Vote casting

	intent = new Intent().setClass(this, Voting.class);
	spec = tabHost.newTabSpec("vote").setIndicator("Vote", res.getDrawable(R.drawable.vote_tab)).setContent(intent);
	tabHost.addTab(spec);

	// Tab: Audio streaming from wall

	intent = new Intent().setClass(this, Audio.class);
	spec = tabHost.newTabSpec("audio").setIndicator("Audio", res.getDrawable(R.drawable.audio_tab)).setContent(intent);
	tabHost.addTab(spec);

	// Tab: Video streaming to wall
	
	intent = new Intent().setClass(this, Video.class);
	spec = tabHost.newTabSpec("video").setIndicator("Video", res.getDrawable(R.drawable.video_tab)).setContent(intent);
	tabHost.addTab(spec);

	tabHost.setCurrentTab(0);
	setStatus("");
    }

    // @Override
    public boolean onPrepareOptionsMenu(Tabs menu) {
	return super.onPrepareOptionsMenu((android.view.Menu) menu);
    }

    // @Override
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.menu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.help:
	    Intent i = new Intent();
	    i.setClass(Tabs.this, Help.class);
	    startActivity(i);
	    break;

	case R.id.info:
	    Intent i2 = new Intent();
	    i2.setClass(Tabs.this, Information.class);
	    startActivity(i2);
	    break;

	case R.id.settings:
	    Intent i3 = new Intent();
	    i3.setClass(Tabs.this, Settings.class);
	    startActivity(i3);
	    break;

	case R.id.quit:
	    finish();
	    break;
	}
	return true;
    }
}