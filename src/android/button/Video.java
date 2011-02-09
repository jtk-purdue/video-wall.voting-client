package android.button;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Video extends Activity {
	
	public VideoView video;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        
        Button button = (Button) findViewById(R.id.startvid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	//setContentView(R.layout.startvideo);
            	//video = (VideoView) findViewById(R.id.videoview);
            	//video.setVideoPath("/sdcard/batman.3gp");
            	//video.start();
            	Intent i = new Intent();
				i.setClass(Video.this, CameraDemo.class);
				startActivity(i);
            }
        });
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
    	if(video.onTouchEvent(e))
    	{
    		video.pause();
    		return true;
    	}
    	else
    		return false;
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
	            					i.setClass(Video.this, Help.class);
	            					startActivity(i);
									break;
	            
	            case R.id.info:     Intent i2 = new Intent();
	            					i2.setClass(Video.this, Information.class);
	            					startActivity(i2);
	            					break;
	            					
	            case R.id.settings:     Intent i3 = new Intent();
										i3.setClass(Video.this, Settings.class);
										startActivity(i3);
										break;
	        }
	        return true;
	    }	
}