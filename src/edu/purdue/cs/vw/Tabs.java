package edu.purdue.cs.vw;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This file sets up the tab view that the application uses.
 */

public class Tabs extends TabActivity {
    
    static Context ctx;
    static ArrayList<String> accounts;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tabs);

	//Getting accounts
	ctx=this;
	accounts = new ArrayList<String>();
	accounts.add("jfranklin773@gmail.com");
	accounts.add("jtkorb@gmail.com");
	accounts.add("jtk.purdue@gmail.com");
	
	//starts splash screen when app is first opened
	this.startActivity(new Intent(Tabs.this,Splash.class));	
	
	status = (TextView) findViewById(R.id.status);
	setStatus("Starting up...");

	Resources res = getResources(); // Resource object to get Drawables
	TabHost tabHost = getTabHost(); // The activity TabHost
	TabHost.TabSpec spec; // Resusable TabSpec for each tab
	Intent intent; // Reusable Intent for each tab

	// Tab: Team photos and biographies

	intent = new Intent().setClass(this, Bios.class);
	spec = tabHost.newTabSpec("team").setIndicator("Team", res.getDrawable(R.drawable.team_tab)).setContent(intent);
	tabHost.addTab(spec);

	// Tab: Vote casting

	intent = new Intent().setClass(this, Voting.class);
	spec = tabHost.newTabSpec("vote").setIndicator("Vote", res.getDrawable(R.drawable.vote_tab)).setContent(intent);
	tabHost.addTab(spec);

//	// Tab: Audio streaming from wall
//
//	intent = new Intent().setClass(this, Audio.class);
//	spec = tabHost.newTabSpec("audio").setIndicator("Audio", res.getDrawable(R.drawable.audio_tab))
//		.setContent(intent);
//	tabHost.addTab(spec);
//
//	// Tab: Video streaming to wall
//
//	intent = new Intent().setClass(this, Video.class);
//	spec = tabHost.newTabSpec("video").setIndicator("Video", res.getDrawable(R.drawable.video_tab))
//		.setContent(intent);
//	tabHost.addTab(spec);

	tabHost.setCurrentTab(0);
	setStatus("");
    }

    /*
     * Static option menu handling routines shared by all activities. TODO: Reconsider how to reduce this
     * interdependency among activities.
     */

    public static boolean doCreateOptionsMenu(Activity activity, Menu menu) {
	MenuInflater inflater = activity.getMenuInflater();
	inflater.inflate(R.menu.menu, menu);
	menu.findItem(R.id.admin).setIcon(android.R.drawable.ic_menu_gallery);
	return true;
    }

    public static boolean doOptionsItemSelected(Activity activity, MenuItem item) {
	Intent i = new Intent();

	switch (item.getItemId()) {
	case R.id.help:
	    i.setClass(activity, Help.class);
	    activity.startActivity(i);
	    break;

	case R.id.info:
	    i.setClass(activity, Information.class);
	    activity.startActivity(i);
	    break;

	case R.id.admin:
	    Account[] a = AccountManager.get(ctx).getAccounts();
	    for (Account account : a) {
	      // TODO: Check possibleEmail against an email regex or treat
	      // account.name as an email address only for certain account.type values.
	      String possibleEmail = account.name;
	      if(accounts.contains("jfranklin773@gmail.com")){
		  i.setClass(activity, Admin.class);
		  activity.startActivity(i);
		  break;
	      }
	      Log.d("Tabs", possibleEmail);
	    }

	    break;

	case R.id.settings:
	    i.setClass(activity, Settings.class);
	    activity.startActivity(i);
	    break;
	 
	case R.id.message:
	    Context mContext = ctx.getApplicationContext();
	    final Dialog dialog = new Dialog(ctx);

	    dialog.setContentView(R.layout.messagedialog);
	    final EditText edit = (EditText)dialog.findViewById(R.id.edit);
	    Button b = (Button)dialog.findViewById(R.id.b1);
	    b.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View arg0) {
		    Toast.makeText(ctx, edit.getEditableText().toString(), Toast.LENGTH_SHORT).show();
		    dialog.cancel();
		}
	    });
	    dialog.setTitle("POST A MESSAGE TO THE WALL");
	    dialog.show();
	    //i.setClass(activity, cls)

//	case R.id.quit:
//	    activity.finish();
//	    break;
	}
	return true;
    }

    /*
     * The "status line" (at the bottom of all screens in the application) is part of the Tabs activity, accessible
     * through this static method. The null check is in case the Tabs activity is not running (e.g., if testing an
     * isolated activity).
     */

    static TextView status;

    public static void setStatus(String s) {
	if (status != null)
	    status.setText(s);
    }

    /*
     * Beginning of standard options menu boilerplate.
     */

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