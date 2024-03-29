package edu.purdue.cs.vw;

import edu.purdue.cs.vw.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/*
 * This file sets up the layout for when the settings button is pressed.
 */

public class Settings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.layout.settings);
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

	OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
	    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key.equals("portPref")) {
		    String valueString = prefs.getString(key, "");
		    int value = Integer.parseInt(valueString);
		    if (value < 0 || value > 65536) {
			EditTextPreference p = (EditTextPreference) findPreference(key);
			p.setText("4242");
			display();
		    }
		}
	    }
	};
	prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void display() {
	Toast t = Toast.makeText(this, "Invalid Port. Reverting to 4242. ", Toast.LENGTH_SHORT);
	t.show();
    }
}
