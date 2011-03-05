package android.button;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/*This file sets up the layout for when the help button is pressed.
 */

public class Help extends Activity
{
	@Override
	   public void onCreate(Bundle savedInstanceState) 
	{
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.help);
	}


}
