package android.button;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Help extends Activity
{
	@Override
	   public void onCreate(Bundle savedInstanceState) 
	{
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.help);
	       /*
	       Context mContext = getApplicationContext();
	       Dialog dialog = new Dialog(mContext);

	       dialog.setContentView(R.layout.help);
	       dialog.setTitle("Custom Dialog");

	       TextView text = (TextView) dialog.findViewById(R.id.text);
	       text.setText("Hello, this is a custom dialog!");
	       ImageView image = (ImageView) dialog.findViewById(R.id.icon);
	       image.setImageResource(R.drawable.icon);
	       */
	   }


}
