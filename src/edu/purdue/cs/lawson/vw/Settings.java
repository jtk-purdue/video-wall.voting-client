package edu.purdue.cs.lawson.vw;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import edu.purdue.cs.lawson.vw.R;

/*This file sets up the layout for when the settings button is pressed.
 */

public class Settings extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.settings);
	}

}
