package android.button;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

/*This activity sets up the tab that allows the user to view the bio-pages of team members. Contains invisible buttons overlaid on the images. 
 * Switches to an activity called BioPage, which sets the appropriate layout depending on which button is clicked. Each layout represents an individiual's Bio. 
 */

public class Bios extends Activity {
	Animation anim = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bios);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Button button = (Button) findViewById(R.id.member1);// Creating a button
		button.setBackgroundColor(Color.TRANSPARENT); // Make button invisible

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "TylerH");// PutExtra is used to send
												// information between
												// activities. Sends name to
												// BioPage activity.
				i.setClass(Bios.this, BioPage.class);// Switch to BioPage
														// activity.
				startActivity(i);
			}
		});

		// Above process is repeated for each picture.
		Button button2 = (Button) findViewById(R.id.member2);
		button2.setBackgroundColor(Color.TRANSPARENT);
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "TylerW");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

		Button button3 = (Button) findViewById(R.id.member3);
		button3.setBackgroundColor(Color.TRANSPARENT);
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent();
				i.putExtra("name", "Jaye");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

		Button button4 = (Button) findViewById(R.id.member4);
		button4.setBackgroundColor(Color.TRANSPARENT);
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "Maaz");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

		Button button5 = (Button) findViewById(R.id.member5);
		button5.setBackgroundColor(Color.TRANSPARENT);
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "Others");
				i.setClass(Bios.this, Acknowledgements.class);
				startActivity(i);
			}
		});

		Button button6 = (Button) findViewById(R.id.member6);
		button6.setBackgroundColor(Color.TRANSPARENT);
		button6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "Jon");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

		Button button7 = (Button) findViewById(R.id.member7);
		button7.setBackgroundColor(Color.TRANSPARENT);
		button7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "Sohail");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

		Button button8 = (Button) findViewById(R.id.member8);
		button8.setBackgroundColor(Color.TRANSPARENT);
		button8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "Nick");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

		Button button9 = (Button) findViewById(R.id.member9);
		button9.setBackgroundColor(Color.TRANSPARENT);
		button9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("name", "Rick");
				i.setClass(Bios.this, BioPage.class);
				startActivity(i);
			}
		});

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
			i.setClass(Bios.this, Help.class);
			startActivity(i);
			break;

		case R.id.info:
			Intent i2 = new Intent();
			i2.setClass(Bios.this, Information.class);
			startActivity(i2);
			break;

		case R.id.settings:
			Intent i3 = new Intent();
			i3.setClass(Bios.this, Settings.class);
			startActivity(i3);
			break;

		case R.id.quit:
			finish();
			break;
		}
		return true;
	}
}