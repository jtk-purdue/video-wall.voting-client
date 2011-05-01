package edu.purdue.cs.vw;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import edu.purdue.cs.lawson.vw.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/*This file sets up the layout for when the information button is pressed.
 */

public class Stats extends Activity {
	
	ImageView image;
	ArrayList<String> list;
	ArrayList<String> intvalues;
	boolean get = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		
		setContentView(R.layout.stats);
		image = (ImageView) findViewById(R.id.image);
		
		Bundle data = getIntent().getExtras();
		
		 list =(ArrayList<String>) data.get("votes");
		 intvalues = (ArrayList<String>)data.get("votesint");
		
		if(list == null || intvalues == null){
			get=false;
		}else{
			//Toast.makeText(this, list.toString()+" \n  "+intvalues.toString(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();

		final Context ctx = this;
		
		if(get)
		new AsyncTask() {
			
			Boolean online;
			
			Bitmap b = null;
			
			protected Object doInBackground(Object[] params) {
				online = isOnline();
				if(online)
				try {
					
					String chart = "https://chart.googleapis.com/chart?cht=pc&chd=t:";
					//"5,5,6&chs=250x100&chl=Hello|World|Life";
					
					int i;
					
					for(i=0;i<intvalues.size()-1;i++)
						chart+=intvalues.get(i)+",";
					
					chart+=intvalues.get(i)+"";
					
					chart+="&chs=250x100&chl=";
					
					for(i=0;i<list.size()-1;i++)
						chart+=list.get(i)+"|";
					
					chart+=list.get(i);
					
					chart+="&chco=000000|0000FF|FFEAC0|00FF00|E8E8E8|FF9900|FF00FF|FF0000";
					
					Log.d("Stats", chart);
					
					b = loadImage(chart);


					//image[2].setImageBitmap(list.getImage(ctx, 2));
				} catch (Exception e) {
				}
				
				return null;
			}
			
			protected void onPostExecute(Object result) {
				super.onPostExecute(result);
				
				if(online & b!=null){
					image.setImageBitmap(b);
				}
			}
		}.execute(null);
		
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	public Bitmap loadImage(String url){
		
		BufferedInputStream in;
		Bitmap image = null;
		try {
            in = OpenHttpConnection(url);
            image= BitmapFactory.decodeStream(new FlushedInputStream(in));
            in.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return image;
	}
	
	private BufferedInputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        int length = 0;
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setDoInput(true);
            httpConn.connect();
            length = httpConn.getContentLength();

            response = httpConn.getResponseCode();                 
           // if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
           // }                     
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            throw new IOException("Error connecting");            
        }
        return new BufferedInputStream(in,length);     
    }
	
	static class FlushedInputStream extends FilterInputStream {
	    public FlushedInputStream(InputStream inputStream) {
	        super(inputStream);
	    }

	    @Override
	    public long skip(long n) throws IOException {
	        long totalBytesSkipped = 0L;
	        while (totalBytesSkipped < n) {
	            long bytesSkipped = in.skip(n - totalBytesSkipped);
	            if (bytesSkipped == 0L) {
	                  int byt = read();
	                  if (byt < 0) {
	                      break;  // we reached EOF
	                  } else {
	                      bytesSkipped = 1; // we read one byte
	                  }
	           }
	            totalBytesSkipped += bytesSkipped;
	        }
	        return totalBytesSkipped;
	    }
	}

}
