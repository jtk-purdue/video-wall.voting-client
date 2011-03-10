package android.button;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class GalleryExample extends Activity {

    private Gallery gallery;
    private ImageView imgView;

    private Integer[] Imgid = {
            R.drawable.nh, R.drawable.mh, R.drawable.a_3, R.drawable.a_4, R.drawable.a_5, R.drawable.a_6, R.drawable.a_7
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        imgView = (ImageView)findViewById(R.id.ImageView01);	
        imgView.setImageResource(Imgid[0]);
        
         gallery = (Gallery) findViewById(R.id.examplegallery);
         gallery.setAdapter(new AddImgAdp(this));

         gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                imgView.setImageResource(Imgid[position]); 
            }
        });

    }

    public class AddImgAdp extends BaseAdapter {
        int GalItemBg;
        private Context cont;

        public AddImgAdp(Context c) {
            cont = c;
            TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
            GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
            typArray.recycle();
        }

        public int getCount() {
            return Imgid.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imgView = new ImageView(cont);

            imgView.setImageResource(Imgid[position]);
            imgView.setLayoutParams(new Gallery.LayoutParams(80, 70));
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgView.setBackgroundResource(GalItemBg);

            return imgView;
        }
    }

}
