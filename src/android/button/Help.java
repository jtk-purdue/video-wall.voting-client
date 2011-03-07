package android.button;

import android.app.Activity;
import android.os.Bundle;

/*This file sets up the layout for when the help button is pressed.
 */

public class Help extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
	}

}
