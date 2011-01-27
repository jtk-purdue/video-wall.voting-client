package android.button;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// maaz
//sohail
public class Audio extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Audio tab");
        setContentView(textview);
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
	            case R.id.help:    
	            					Intent i = new Intent();
	            					i.setClass(Audio.this,Help.class);
	            					startActivity(i);
	            					/*
	            					Context mContext = getApplicationContext();
	            					Dialog dialog = new Dialog(mContext);
	            					dialog.setContentView(R.layout.help);
	            					dialog.setTitle("Help");
	            					TextView text = (TextView) dialog.findViewById(R.id.text);
	            					text.setText("Hello, this is a custom dialog!");
	            					ImageView image = (ImageView) dialog.findViewById(R.id.image);
	            					image.setImageResource(R.drawable.icon);
									*/
									break;
	            
	            case R.id.info:  
	            				
	            				Intent i2 = new Intent();
	            				i2.setClass(Audio.this,Information.class);
	            				startActivity(i2);

	            				/*
	            				Context mContext2 = getApplicationContext();
	            				Dialog dialog2 = new Dialog(mContext2);
	            				dialog2.setContentView(R.layout.info);
	            				dialog2.setTitle("Information");
	            				TextView text2 = (TextView) dialog2.findViewById(R.id.text);
	            				text2.setText("Hello, this is a custom dialog!");
	            				ImageView image2 = (ImageView) dialog2.findViewById(R.id.image);
	            				image2.setImageResource(R.drawable.icon);
	            				*/
	            					break;
	        }
	        return true;
	    }	
}