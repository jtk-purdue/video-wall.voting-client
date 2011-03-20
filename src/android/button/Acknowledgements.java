package android.button;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Acknowledgements extends ListActivity {

	ArrayList<String> nameList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		nameList = new ArrayList<String>();
		nameList.add("SPECIAL THANKS TO:");
		nameList.add("X");
		nameList.add("Y");
		nameList.add("Z");
		nameList.add("Dr. John Tim Korb, Course Instructor");
		nameList.add("A");
		nameList.add("B");
		nameList.add("C");
		nameList.add("P");
		nameList.add("Q");
		nameList.add("R");
		nameList.add("L");
		nameList.add("M");
		nameList.add("N");
		nameList.add("I");
		nameList.add("J");
		nameList.add("K");
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