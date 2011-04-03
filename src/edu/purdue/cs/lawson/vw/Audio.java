package edu.purdue.cs.lawson.vw;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
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
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class Audio extends Activity implements ViewFactory {
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
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3",
								  "http://sound17.mp3pk.com/indian/dummaarodum/dummaardum01%28www.songs.pk%29.mp3"};;

	private static final String TAG = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		ins = (TextView) findViewById(R.id.audio_instructions);
		internetcheck = checkInternetConnection();
		mp = new MediaPlayer();
		
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setSelection(4);
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
		});

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
	

	public View makeView() {
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
	}
}
