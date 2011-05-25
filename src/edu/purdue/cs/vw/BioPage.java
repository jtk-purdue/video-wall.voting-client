package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BioPage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	return Tabs.doCreateOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	return Tabs.doOptionsItemSelected(this, item);
    }
}