package edu.purdue.cs.vw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import edu.purdue.cs.vw.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * When this activity starts, it pulls a list of available shows that can be voted on. 
 * When a vote is cast it makes a connection with the server and sends across what was voted for.
 */

public class Voting extends ListActivity {
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	CustomAdapter adapter;
	RowData rd;
	ArrayList<String> voteList;
	ArrayList<String> votes;
	Runnable update;
	Animation anim = null;
	ProgressDialog myProgressDialog = null;
	String editTextPreference;
	int portnum;
	String serverLocation;
	Handler mHandler;
	boolean voted = false;
	int lastPosition = -1;
	Server server;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		voteList = new ArrayList<String>();
		votes = new ArrayList<String>();

		SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);
		serverLocation = myPreference.getString("serverPref", "pc.cs.purdue.edu");
		Log.d("Server Preference in onCreate", "" + serverLocation);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		editTextPreference = prefs.getString("editTextPref", "4242");
		portnum = Integer.parseInt(editTextPreference);
		Log.d("PORT NUMBER in onCreate", editTextPreference);

		setContentView(R.layout.vote);
		anim = AnimationUtils.loadAnimation(this, R.anim.shake); // Sets the animation to shake
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		server = new ServerReal(serverLocation, portnum, cm);

		try {
			voteList.clear();
			server.get(voteList);
			server.getCount(votes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateData();

/*		update = new Runnable() {
			@Override
			public void run() {
				try {
					RowData r = null;
					try {
						votes.clear();
						server.getCount(votes);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					for (int i = 0; i < voteList.size(); i++) {

						try {
							r = (RowData) data.elementAt(i);
							String temp = "Number of votes: " + votes.get(i);
							r.setDetail(temp);

						}

						catch (ParseException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					adapter.notifyDataSetChanged();
					mHandler.postDelayed(this, 3000);
				}

				catch (Exception e) {
					// do nothing
				}
			}
		};

		mHandler = new Handler();
		// mHandler.removeCallbacks(update);
		mHandler.postDelayed(update, 5000);*/
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("On PAUSE", "TRUE");
		mHandler.removeCallbacks(update);
	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);
		String temp_serverLocation = myPreference.getString("serverPref", "pc2.cs.purdue.edu");
		Log.d("Server Preference", "" + temp_serverLocation);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String temp_editTextPreference = prefs.getString("editTextPref", "4242");

		Log.d("PORT NUMBER", temp_editTextPreference);

		if (!(temp_serverLocation.equals(serverLocation)) || !(temp_editTextPreference).equals(editTextPreference)) {
			serverLocation = temp_serverLocation;
			editTextPreference = temp_editTextPreference;
			portnum = Integer.parseInt(editTextPreference);
			removeData();
			voteList.clear();
			votes.clear();
			try {
				server.get(voteList);
				server.getCount(votes);
				updateData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				updateData();
				adapter.notifyDataSetChanged();
			}
		}

		Log.d("On RESUME", "TRUE");
		mHandler = new Handler();
		mHandler.postDelayed(update, 1000);
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d("On STOP", "TRUE");
		mHandler.removeCallbacks(update);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("On DESTROY", "TRUE");
		mHandler.removeCallbacks(update);
	}

	public void onListItemClick(ListView parent, View v, final int position, long id) {
		TextView title = (TextView) v.findViewById(R.id.title);
		final String vi = (String) ((TextView) title).getText();
		try {
			server.vote(vi);

			voted = true;
			votes.clear();
			server.getCount(votes);
			lastPosition = position;
			Toast.makeText(getApplicationContext(), "Voted for " + vi, Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Contacts server and gets list of available shows

		// Show the item which
		// has been voted for
		// through a toast
		// v.startAnimation(anim); // Show animation when clicked
		// Update vote on display
		RowData r = null;

		try {
			for (int i = 0; i < voteList.size(); i++) {
				// r = new RowData(position, voteList.get(position),
				// "Number of votes: " + votes.get(position));
				r = (RowData) data.elementAt(i);
				String temp = "Number of votes: " + votes.get(i);
				r.setDetail(temp);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		adapter.notifyDataSetChanged();
	}

	public void removeData() {
		data.clear();
	}

	public void updateData() {
		Log.d("Number of votable Items", "" + voteList.size());
		for (int i = 0; i < voteList.size(); i++) {
			try {
				rd = new RowData(i, voteList.get(i), "Number of votes: " + votes.get(i));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.add(rd);
		}
		adapter = new CustomAdapter(this, R.layout.list, R.id.title, data);
		setListAdapter(adapter);

		getListView().setTextFilterEnabled(true);
	}

	private class RowData {
		protected int mId;
		protected String mTitle;
		protected String mDetail;

		RowData(int id, String title, String detail) {
			mId = id;
			mTitle = title;
			mDetail = detail;
		}

		@Override
		public String toString() {
			return mId + " " + mTitle + " " + mDetail;
		}

		public void setDetail(String item) {
			mDetail = item;
		}

	}

	private class CustomAdapter extends ArrayAdapter<RowData> {
		public CustomAdapter(Context context, int resource, int textViewResourceId, List<RowData> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			TextView title = null;
			TextView detail = null;
			ImageView i11 = null;
			RowData rowData = getItem(position);
			if (null == convertView) {
				convertView = mInflater.inflate(R.layout.list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);
			detail = holder.getdetail();
			detail.setText(rowData.mDetail);
			i11 = holder.getImage();
			i11.setImageResource(R.drawable.voteicon);
			return convertView;
		}

		public class ViewHolder {
			private View mRow;
			private TextView title = null;
			private TextView detail = null;
			private ImageView i11 = null;

			public ViewHolder(View row) {
				mRow = row;
			}

			public TextView gettitle() {
				if (null == title) {
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			public TextView getdetail() {
				if (null == detail) {
					detail = (TextView) mRow.findViewById(R.id.detail);
				}
				return detail;
			}

			public ImageView getImage() {
				if (null == i11) {
					i11 = (ImageView) mRow.findViewById(R.id.img);
				}
				return i11;
			}

		}
	}

	// @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu((android.view.Menu) menu);
	}

	// @Override
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.voteoptionsmenu, menu);
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

		case R.id.stats:
			Intent i2 = new Intent();
			i2.putExtra("votes", voteList);
			i2.putExtra("votesint", votes);
			i2.setClass(Voting.this, Stats.class);
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