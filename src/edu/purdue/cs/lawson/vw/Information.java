package edu.purdue.cs.lawson.vw;

import android.app.Activity;
import android.os.Bundle;
import edu.purdue.cs.lawson.vw.R;

/*This file sets up the layout for when the information button is pressed.
 */

public class Information extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	}

}
