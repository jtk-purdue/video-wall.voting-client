package android.button;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import java.util.Collections;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/*When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and send across what was voted for.
 */

public class Voting extends ListActivity {

	Socket requestSocket;
	PrintWriter out;
	BufferedReader in;
	String message;
	ArrayList<String> voteList;
	Animation anim = null;
	ProgressDialog myProgressDialog = null;
	boolean internetcheck = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		internetcheck = checkInternetConnection();// check if Internet
													// connection is present and
													// set to true if it is.

		/*
		 * // Creating a custom dialog box Context mContext =
		 * getApplicationContext(); Dialog dialog = new Dialog(mContext);
		 * 
		 * dialog.setContentView(R.layout.custom_dialog);
		 * dialog.setTitle("Custom Dialog");
		 * 
		 * TextView text = (TextView) dialog.findViewById(R.id.text);
		 * text.setText("Hello, this is a custom dialog!"); ImageView image =
		 * (ImageView) dialog.findViewById(R.id.image);
		 * image.setImageResource(R.drawable.icon3); //
		 * setContentView(R.layout.custom_dialog);
		 */

		myProgressDialog = ProgressDialog.show(Voting.this, "Please wait...",
				"Populating List...", true);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		voteList = new ArrayList<String>();

		new Thread() {
			@Override
			public void run() {

				try {
					sleep(1700);

				} catch (Exception e) {
				}

				// Dismiss the Dialog
				myProgressDialog.dismiss();
			}

		}.start();

		connect("GET"); // Contacts server and gets list of available shows
		setListAdapter(new ArrayAdapter<String>(this, R.layout.vote, voteList));// Sets vote list as layout
		anim = AnimationUtils.loadAnimation(this, R.anim.shake); // Sets the animation to shake
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text

				Log.d("TOAST", "Before");
				String vi = (String) ((TextView) view).getText();
				Toast.makeText(getApplicationContext(), "Voted for " + vi,
						Toast.LENGTH_SHORT).show();// Show the item which has been voted for through a toast
				view.startAnimation(anim); // Show animation when clicked
				connect(vi); // Sends server information on what was voted for
			}
		});

	}

	// Checks for Internet connection through Wifi or 3g
	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			Log.d("Connection State", "Internet Connection Not Present");
			return false;
		}
	}

	public void connect(String voteitem) {
		boolean check = false;

		if (voteitem.equals("GET")) {
			check = true;
		}

		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket("lore.cs.purdue.edu", 4500);
			Log.d("Connection", "Connected to localhost in port 4500");

			// 2. get Input and Output streams
			out = new PrintWriter(requestSocket.getOutputStream(), true);
			out.flush();
			in = new BufferedReader(new InputStreamReader(
					requestSocket.getInputStream()));

			// 3: Communicating with the server
			Log.d("DO", "Test1");
			do {
				message = in.readLine();
				System.out.println("server>" + message);
				message = "END";
				sendMessage(voteitem);
				sendMessage("END");
				do {
					message = in.readLine();
					Log.d("server>", message);

					if (check == true && !message.equals("END"))
						voteList.add(message);
					Collections.sort(voteList);// Sorting Array List in Alpha
												// order

				} while (!message.equals("END"));
			} while (!message.equals("END"));
		}

		// Handle exception if server was not found
		catch (ConnectException e) {
			Log.d("SERVER", "Server NOT FOUND");
			Toast.makeText(getApplicationContext(), "Server not running",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent();
			i.setClass(this, Welcome.class);
			startActivity(i);
			finish();

		}

		// Handles unknown host exception
		catch (UnknownHostException unknownHost) {
			Log.d("Error", "You are trying to connect to an unknown host!");
		}
		// Catch Input Output exception
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		// Catch general exception
		catch (Exception e) {
			Log.d("Error", e.getMessage());
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	public void sendMessage(String msg) {
		out.println(msg);
		out.flush();
		Log.d("client>", msg);
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
			i.setClass(Voting.this, Help.class);
			startActivity(i);
			break;

		case R.id.info:
			Intent i2 = new Intent();
			i2.setClass(Voting.this, Information.class);
			startActivity(i2);
			break;

		case R.id.settings:
			Intent i3 = new Intent();
			i3.setClass(Voting.this, Settings.class);
			startActivity(i3);
			break;

		case R.id.quit:
			finish();
			break;
		}
		return true;
	}
}