package edu.purdue.cs.lawson.vw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class Audio extends Activity  {
	Animation anim = null;
	MediaPlayer mp;

	// ---the images to display---
	Integer[] imageIDs = { R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon };

	private Integer[] thumbnails = { R.drawable.audio, R.drawable.audio,
			R.drawable.audio, R.drawable.audio, R.drawable.audio, R.drawable.audio,
			R.drawable.audio, R.drawable.audio, R.drawable.audio };

	//private ImageSwitcher imageSwitcher;
	private TextView ins;
	private boolean internetcheck;
	
	private String[] songUrls = { "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound21.mp3pk.com/indian/patialahouse/patialahouse01(www.songs.pk).mp3",
								  "http://creamteam.info/wp-content/uploads/2010/08/01-Club-Cant-Handle-Me-Feat.-David-Guetta.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3"};;

	private static final String TAG = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);

		ins = (TextView) findViewById(R.id.audio_instructions);
		internetcheck = checkInternetConnection();
		mp = new MediaPlayer();
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Button button = (Button) findViewById(R.id.screen1);// Creating a button
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(0);
			}
		});

		// Above process is repeated for each audio track.
		Button button2 = (Button) findViewById(R.id.screen2);
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(1);
			}
		});

		Button button3 = (Button) findViewById(R.id.screen3);
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(2);
			}
		});
		
		Button button4 = (Button) findViewById(R.id.screen4);// Creating a button
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(3);
			}
		});

		// Above process is repeated for each audio track.
		Button button5 = (Button) findViewById(R.id.screen5);
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(4);
			}
		});

		Button button6 = (Button) findViewById(R.id.screen6);
		button6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(5);
			}
		});

		Button button7 = (Button) findViewById(R.id.screen7);// Creating a button
		button7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(6);
			}
		});

		// Above process is repeated for each audio track.
		Button button8 = (Button) findViewById(R.id.screen8);
		button8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(7);
			}
		});

		Button button9 = (Button) findViewById(R.id.screen9);
		button9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(8);
			}
		});

		Button button10 = (Button) findViewById(R.id.screen10);// Creating a button
		button10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(9);
			}
		});

		// Above process is repeated for each audio track.
		Button button11 = (Button) findViewById(R.id.screen11);
		button11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(10);
			}
		});

		Button button12 = (Button) findViewById(R.id.screen12);
		button12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(11);
			}
		});

		Button button13 = (Button) findViewById(R.id.screen13);// Creating a button
		button13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(12);
			}
		});

		// Above process is repeated for each audio track.
		Button button14 = (Button) findViewById(R.id.screen14);
		button14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(13);
			}
		});

		Button button15 = (Button) findViewById(R.id.screen15);
		button15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(14);
			}
		});
		
		Button button16 = (Button) findViewById(R.id.screen16);
		button16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSong(15);
			}
		});


		
		
		
		
		/*
		//Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		//gallery.setAdapter(new ImageAdapter(this));
		//gallery.setSelection(4);
		//ins.setImageResource(R.drawable.icon);

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				
			   String url = songUrls[position];
			   //String url = "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3";
			   mp.stop();
			   mp.reset();
		       if (internetcheck) {
		       try {
		            mp.setDataSource(url);
		            mp.prepare();
		            mp.start();
		       } catch (Exception e) {
		            Log.i("Exception", "Exception in streaming mediaplayer e = " +
		            e);
		       }
		      }	//imageSwitcher.setImageResource(imageIDs[position]);
			}
		});*/

	}
	
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
	
	public void playSong(int position)
	{
		String url = songUrls[position];
		   mp.stop();
		   mp.reset();
	       if (internetcheck) {
	       try {
	            mp.setDataSource(url);
	            mp.prepare();
	            mp.start();
	       } catch (Exception e) {
	            Log.i("Exception", "Exception in streaming mediaplayer e = " +
	            e);
	       }
	      }
	}
	
	public void onPause()
	{
		super.onPause();
		mp.stop();
	}
	
	public void onStop()
	{
		super.onStop();
		mp.stop();
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		mp.start();
	}
	
	public void onResume()
	{
		super.onResume();
		//mp = new MediaPlayer();
		mp.reset();
	}
	

	/*public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;

		public ImageAdapter(Context c) {
			context = c;

			// ---setting the style---
			TypedArray a = obtainStyledAttributes(R.styleable.GalleryTheme);
			itemBackground = a.getResourceId(
					R.styleable.GalleryTheme_android_galleryItemBackground, 0);
			a.recycle();
		}

		// ---returns the number of images---
		public int getCount() {
			return imageIDs.length;
		}

		// ---returns the ID of an item---
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		// ---returns an ImageView view---
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(thumbnails[position]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(270, 275));
			imageView.setBackgroundResource(itemBackground);
			return imageView;
		}
	}*/
}
