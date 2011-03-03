package android.button;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class ConfirmVote extends Activity
{
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       //setContentView(R.layout.confirmvote);
	       
	    // prepare the alert box
           AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

           // set the message to display
           alertbox.setMessage("This is the alertbox!");

           // add a neutral button to the alert box and assign a click listener
           alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

               // click listener on the alert box
               public void onClick(DialogInterface arg0, int arg1) {
                   // the button was clicked
                   Toast.makeText(getApplicationContext(), "OK button clicked", Toast.LENGTH_LONG).show();
               }
           });
           // show it
           alertbox.show();
	       
	   }


}
