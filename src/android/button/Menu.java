package android.button;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

 
public class Menu extends TabActivity{
   /** Called when the activity is first created. */
   
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    // where the initial connect for the server will be
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, Welcome.class);

	    spec = tabHost.newTabSpec("artists").setIndicator("Welcome",
                res.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, Voting.class);
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("artists").setIndicator("Vote",
	                      res.getDrawable(R.drawable.ic_tab_artists))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, Audio.class);
	    spec = tabHost.newTabSpec("albums").setIndicator("Audio",
                res.getDrawable(R.drawable.ic_tab_artists))
            .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, Video.class);
	    spec = tabHost.newTabSpec("artists").setIndicator("Video",
                res.getDrawable(R.drawable.ic_tab_artists))
            .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
	
	//@Override 
	 public boolean onPrepareOptionsMenu(Menu menu) { 
	  return super.onPrepareOptionsMenu((android.view.Menu) menu); 
	 } 
	
	  //@Override
	    public boolean onCreateOptionsMenu(android.view.Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.help:     Intent i = new Intent();
	            					i.setClass(Menu.this, Help.class);
	            					startActivity(i);
									break;
	            
	            case R.id.info:     Intent i2 = new Intent();
	            					i2.setClass(Menu.this, Information.class);
	            					startActivity(i2);
	            					break;
	            					
	            case R.id.settings:     Intent i3 = new Intent();
										i3.setClass(Menu.this, Settings.class);
										startActivity(i3);
										break;
	        }
	        return true;
	    }	
}