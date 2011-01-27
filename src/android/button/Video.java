package android.button;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Video extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Video Tab");
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
	            case R.id.help:    /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
									builder.setMessage(R.string.help)
									.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
									});
									AlertDialog alert = builder.create();
									alert.show();
									*/
	            					Intent i = new Intent();
	            					i.setClass(Video.this,Help.class);
	            					startActivity(i);
									break;
	            
	            case R.id.info:    	/*AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
	            					builder2.setMessage(R.string.information)
	            					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            					public void onClick(DialogInterface dialog, int id) {
	            						dialog.cancel();
	            					}
	            					});
	            					AlertDialog alert2 = builder2.create();
	            					alert2.show();
	            					*/
	            				Intent i2 = new Intent();
	            				i2.setClass(Video.this,Information.class);
	            				startActivity(i2);
	            					break;
	        }
	        return true;
	    }	
}