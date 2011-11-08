package edu.purdue.cs.vw;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class WebCamera extends Activity {

    ImageView image;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.webcamera);
	image = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void onResume() {
	super.onResume();
	 Uri uri = Uri.parse("http://lwsn1130n-cam.cs.purdue.edu/mjpg/2/video.mjpg");
	image.setImageURI(uri);
//	new AsyncTask(){
//
//	    Bitmap b;
//	    
//	    @Override
//	    protected Object doInBackground(Object... params) {
//		 String url = String.format("http://lwsn1130s-cam.cs.purdue.edu/axis-cgi/jpg/image.cgi?camera=3&resolution=%dx%d", 320,  240);
//		b = downloadFile(url);
//		return null;
//	    }
//
//	    @Override
//	    protected void onPostExecute(Object result) {
//		super.onPostExecute(result);
//		if(image!=null)
//		    image.setImageBitmap(b);
//	    }
//	    
//	}.execute(null);
	//image.loadUrl("http://lwsn1130n-cam.cs.purdue.edu/mjpg/2/video.mjpg");
		 
	    //image.setVideoURI(uri);
	    //image.start();

	 
//	 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//	 startActivity(intent);

    }

    public Bitmap downloadFile(String fileUrl) {
	URL myFileUrl = null;
	try {
	    myFileUrl = new URL(fileUrl);
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try {
	    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
	    conn.setDoInput(true);
	    conn.connect();
	    int length = conn.getContentLength();
	    int[] bitmapData = new int[length];
	    byte[] bitmapData2 = new byte[length];
	    InputStream is = conn.getInputStream();

	    return BitmapFactory.decodeStream(is);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

}
