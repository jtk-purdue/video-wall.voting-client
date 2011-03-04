package android.button;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Sohail extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.sohail);

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
	            					i.setClass(Sohail.this, Help.class);
	            					startActivity(i);
									break;
	            
	            case R.id.info:     Intent i2 = new Intent();
	            					i2.setClass(Sohail.this, Information.class);
	            					startActivity(i2);
	            					break;
	            					
	            case R.id.settings:     Intent i3 = new Intent();
										i3.setClass(Sohail.this, Settings.class);
										startActivity(i3);
										break;
										
	            case R.id.quit:     finish();
									break;
	        }
	        return true;
	    }	
}