package edu.purdue.cs.vw;

import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Acknowledgements extends ListActivity {

    ArrayList<String> nameList;
    Animation anim = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	anim = AnimationUtils.loadAnimation(this, R.anim.shake);
	
	nameList = new ArrayList<String>();
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
	setListAdapter(new ArrayAdapter<String>(this, R.layout.acknowledgements, nameList));

	ListView lv = getListView();
	lv.setTextFilterEnabled(true);
    }

    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
	TextView title = (TextView) v.findViewById(R.id.ack);
	String temp = (String) ((TextView) title).getText();
	String name = temp.substring(0, temp.indexOf(','));

	Toast.makeText(getApplicationContext(), "A toast to " + name, Toast.LENGTH_SHORT).show();
	v.startAnimation(anim); // Show animation when clicked
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