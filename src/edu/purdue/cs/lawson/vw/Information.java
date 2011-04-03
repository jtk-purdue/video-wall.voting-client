package edu.purdue.cs.lawson.vw;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

/*This file sets up the layout for when the information button is pressed.
 */

public class Information extends Activity {
	
	ImageView image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		image = (ImageView) findViewById(R.id.image);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();

		final Context ctx = this;
		
		
		new AsyncTask() {
			
			Boolean online;
			
			Bitmap b = null;
			
			protected Object doInBackground(Object[] params) {
				
				if(online=isOnline())
				try {
					b = loadImage("https://chart.googleapis.com/chart?cht=bhs&chs=400x400&chd=t:60,50,40,80,30,40,50&chl=Hello|World|Life|CNN|BoilerCast|ESPN|LifeTime");
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
