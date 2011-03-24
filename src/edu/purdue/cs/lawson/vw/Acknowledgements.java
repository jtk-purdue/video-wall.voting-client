package edu.purdue.cs.lawson.vw;

import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.purdue.cs.lawson.vw.R;

public class Acknowledgements extends ListActivity {

	ArrayList<String> nameList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		nameList = new ArrayList<String>();
		//nameList.add("SPECIAL THANKS TO:");
		nameList.add("Jim Clamons, Harris Corporation");
		nameList.add("Don Hewitt, Harris Corporation");
		nameList.add("Jim Smith, Purdue Carpenter Shop");
		nameList.add("Dr. John Tim Korb, Course Instructor");
		nameList.add("Scott Cochran, Purdue Carpenter Shop");
		nameList.add("Tim Korb, CS Assistant Head");
		nameList.add("Brian Board, CS Hardware Engineer");
		nameList.add("Ron Castongia, CS Facilities Manager");
		nameList.add("Mike Motuliak, CS Hardware Engineer");
		nameList.add("Melanie Church, CS Windows Administrator");
		Collections.sort(nameList);
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.acknowledgements, nameList));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
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