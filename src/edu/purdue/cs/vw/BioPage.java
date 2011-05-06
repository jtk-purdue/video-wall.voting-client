package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.purdue.cs.vw.R;

public class BioPage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Intent i = getIntent();
		String n = i.getStringExtra("name");
		Log.d("NAME", n);

		if (n.equals("TylerH"))
			setContentView(R.layout.tylerh);

		if (n.equals("TylerW"))
			setContentView(R.layout.tylerw);

		if (n.equals("Jaye"))
			setContentView(R.layout.jaye);

		if (n.equals("Maaz"))
			setContentView(R.layout.maaz);

		if (n.equals("Jon"))
			setContentView(R.layout.jon);

		if (n.equals("Sohail"))
			setContentView(R.layout.sohail);

		if (n.equals("Nick"))
			setContentView(R.layout.nick);

		if (n.equals("Rick"))
			setContentView(R.layout.rick);

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
			i.setClass(BioPage.this, Help.class);
			startActivity(i);
			break;

		case R.id.info:
			Intent i2 = new Intent();
			i2.setClass(BioPage.this, Information.class);
			startActivity(i2);
			break;

		case R.id.settings:
			Intent i3 = new Intent();
			i3.setClass(BioPage.this, Settings.class);
			startActivity(i3);
			break;

		case R.id.quit:
			finish();
			break;
		}
		return true;
	}
}