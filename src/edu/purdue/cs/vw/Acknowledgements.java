package edu.purdue.cs.vw;

import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.purdue.cs.lawson.vw.R;

public class Acknowledgements extends ListActivity {

	ArrayList<String> nameList;
	Animation anim = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		anim = AnimationUtils.loadAnimation(this, R.anim.shake);
		nameList = new ArrayList<String>();
		// nameList.add("SPECIAL THANKS TO:");
		nameList.add("Jim Clamons, Harris Corporation");
		nameList.add("Dan Trinkle, CS Technical Systems Administrator");
		nameList.add("Dave Burford, Purdue Electronics Shop");
		nameList.add("Jeremy Mullenberg, Purdue Electronics Shop");
		nameList.add("Don Hewitt, Harris Corporation");
		nameList.add("John Steinhauer, Purdue Electronics Shop");
		nameList.add("Jim Smith, Purdue Carpenter Shop");
		nameList.add("Scott Cochran, Purdue Carpenter Shop");
		nameList.add("Tim Korb, CS Assistant Head");
		nameList.add("Nick Hirschberg, CS Webmaster");
		nameList.add("Randy Copas, Purdue Crew Chief");
		nameList.add("Tom Barbour, Purdue Electronics Shop");
		nameList.add("Peter Tsegaye, Harris Technical Support");
		nameList.add("Brian Board, CS Hardware Engineer");
		nameList.add("Ron Castongia, CS Facilities Manager");
		nameList.add("Mike Motuliak, CS Hardware Engineer");
		nameList.add("Melanie Church, CS Windows Administrator");
		nameList.add("Bruce Townsend, Harris Technical Support");
		Collections.sort(nameList);
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.acknowledgements, nameList));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		TextView title = (TextView) v.findViewById(R.id.ack);
		String temp = (String) ((TextView) title).getText();
		String name = temp.substring(0, temp.indexOf(','));
		
			Toast.makeText(getApplicationContext(), "A toast to " + name,
					Toast.LENGTH_SHORT).show();
			v.startAnimation(anim); // Show animation when clicked
	}

	// @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu((android.view.Menu) menu);
	}

	// @Override
	@Override
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
			i.setClass(Acknowledgements.this, Help.class);
			startActivity(i);
			break;

		case R.id.info:
			Intent i2 = new Intent();
			i2.setClass(Acknowledgements.this, Information.class);
			startActivity(i2);
			break;

		case R.id.settings:
			Intent i3 = new Intent();
			i3.setClass(Acknowledgements.this, Settings.class);
			startActivity(i3);
			break;

		case R.id.quit:
			finish();
			break;
		}
		return true;
	}
}