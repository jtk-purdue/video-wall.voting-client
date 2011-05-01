package edu.purdue.cs.vw;

import edu.purdue.cs.lawson.vw.R;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Audio extends Activity  {
	Animation anim = null;
	MediaPlayer mp;

	// ---the images to display---
	Integer[] imageIDs = { R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon };

	//private ImageSwitcher imageSwitcher;
	private TextView ins;
	private boolean internetcheck;
	
	private String[] songUrls = { "http://www.eoezone.com/Update/2009/MUSICHEAVEN/MV/MV_56_Nickelback_Gotta_Be_Somebody.mp3",
			 "http://vukwap.comli.com/upload/Katy_Perry_-_Firework.mp3",
			 "http://creamteam.info/wp-content/uploads/2010/08/01-Club-Cant-Handle-Me-Feat.-David-Guetta.mp3",
			 "http://davidfoucaud.free.fr/Sons/Questionnaire/LINKIN_PARK___IN_THE_END_2_.mp3",
			 "http://www.shtyle.fm/dynimg/usrsng/D0/6C/10972368_Taio_Cruz_Break_Your_Heart.mp3",
			 "http://statdevices.com/pdtprojects/Metallica/Live-January26-2009-Allstate_Arena/met090126d2_17_Enter_Sandman.mp3",
			 "http://www.fillmorefiredepartment.com/01_-_Let_It_Rock.mp3",
			 "http://decedope.com/wp-content/uploads/2010/08/Tinie-Tempah-Written-In-The-Stars-decentlydope.mp3",
			 "http://www.freewebs.com/demymonsters/Red%20hot%20chili%20peppers%20-%20Californification.mp3",		
			 "http://freshmp3music.ru/music/02.2011/Lady_Gaga_-_Born_This_Way_(www.freshmp3music.ru).mp3",
			 "http://fwap.ru/d/mp3/big/eng/l/Led_Zeppelin/Led_zeppelin-rock_n_roll.mp3",
			 "http://www.freewebs.com/itsall4freeitsthebest/01-r_kelly-ignition_(remix)-cms.mp3",
			 "http://www.freewebs.com/rtkkd/Jimmy_Hendrix_-_Purple_Haze.mp3",
			 "http://thedockingstation.ca/audio/2009-07/We_Be_Steady_Mobbin.mp3",
			 "http://user-dak.moskva.com/data/uf/9980163/18/64/12/18641241_Sting_-_02_-_Desert_Rose.mp3",
			 "http://pds3.egloos.com/pds/200708/22/13/lifehouse_01_Hanging_By_A_Moment.mp3"};;



	private static final String TAG = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);

		ins = (TextView) findViewById(R.id.audio_instructions);
		internetcheck = checkInternetConnection();
		mp = new MediaPlayer();
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);  
		gridview.setAdapter(new ButtonAdapter(this)); 
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(Audio.this, "" + position, Toast.LENGTH_SHORT).show();
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
	
	class ButtonAdapter extends BaseAdapter {  
	    private Context mContext; 
	    
	    View  prev = null;
	    int check=-1;
	     
	    // Gets the context so it can be used later  
	    public ButtonAdapter(Context c) {  
	     mContext = c;  
	    }  
	     
	    // Total number of things contained within the adapter  
	    public int getCount() {  
	     return 16;  
	    }  
	     
	     // Require for structure, not really used in my code.  
	    public Object getItem(int position) {  
	     return null;  
	    }  
	     
	    // Require for structure, not really used in my code. Can  
	    // be used to get the id of an item in the adapter for  
	    // manual control.  
	    public long getItemId(int position) {  
	     return position;  
	    }  
	     
	    public View getView(final int position,  
	                              View convertView, ViewGroup parent) {  
	     Button btn;  
	     if (convertView == null) {  
	      // if it's not recycled, initialize some attributes  
	      btn = new Button(mContext);  
	      btn.setLayoutParams(new GridView.LayoutParams(67, 75));  
	      btn.setPadding(8, 8, 8, 8);  
	      }  
	     else {  
	      btn = (Button) convertView;  
	     }  
	     //exus  
	     btn.setText(position+1 + "");  
	     // filenames is an array of strings  
	     btn.setTextColor(Color.WHITE);  
	     btn.setBackgroundColor(Color.argb(100, 100, 100, 255));
	     //btn.setBackgroundResource(R.drawable.button);  
	     btn.setId(position);  
	     btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            //Toast.makeText(v.getContext(), "" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
				if(check!=position)
				{
	            //Toast.makeText(v.getContext(), "" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
	            v.setBackgroundColor(Color.argb(100, 124, 252, 0));
	            
	            if(prev != null)
	            	{
	            		prev.setBackgroundColor(Color.argb(100, 100, 100, 255));
	            		
	            	}
	            prev=v;
	            Toast.makeText(Audio.this, "Now Loading Song...", Toast.LENGTH_SHORT).show();
	            new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
			            playSong(position);
					}
	            }).start();
	            check=position;
	 
				}
				else
				{
		            Toast.makeText(Audio.this, "Stopping Song...", Toast.LENGTH_SHORT).show();
					check=-1;
            		prev.setBackgroundColor(Color.argb(100, 100, 100, 255));
					prev=null;
					mp.stop();
					mp.reset();
				}
			}
				
	    	 
	     });
	     
	     return btn;  
	    }  
	   } 
	
}

 
