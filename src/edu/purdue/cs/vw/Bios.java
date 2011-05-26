package edu.purdue.cs.vw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * This activity sets up the tab that allows the user to view the bio-pages of team members. Contains invisible 
 * buttons overlaid on the images.  Switches to an activity called BioPage, which sets the appropriate layout 
 * depending on which button is clicked. Each layout represents an individiual's biography.
 */
public class Bios extends Activity {
    Animation anim = null;

    private Integer[] biopicIds = { 
	    R.drawable.tylerh, 
	    R.drawable.tylerw, 
	    R.drawable.jaye, 
	    R.drawable.maaz, 
	    R.drawable.harris, 
	    R.drawable.jon, 
	    R.drawable.sohail, 
	    R.drawable.nick, 
	    R.drawable.rick, 
	    };

    class ImageAdapter extends BaseAdapter {
	private Context context;

	public ImageAdapter(Context context) {
	    this.context = context;
	}

	@Override
	public int getCount() {
	    return biopicIds.length;
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ImageView iv;
	    if (convertView == null) {
		iv = new ImageView(context);
		
//		if (position == 0)
//		    iv.setLayoutParams(new GridView.LayoutParams(85, 85));
//		else if (position == 4)
//		    iv.setLayoutParams(new GridView.LayoutParams(100, 100));
//		else if (position == 8)
//		    iv.setLayoutParams(new GridView.LayoutParams(190, 190));
//		else

		iv.setLayoutParams(new GridView.LayoutParams(135, 135));

		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		iv.setPadding(0, 0, 0, 0);
	    } else 
		iv = (ImageView) convertView;
	    iv.setImageResource(biopicIds[position]);
	    return iv;
	}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.bios_grid);

	GridView gridview = (GridView) findViewById(R.id.grid);
	gridview.setAdapter(new ImageAdapter(this));
//	gridview.setStretchMode(GridView.NO_STRETCH);

	gridview.setOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0: displayBio(R.id.member1, "TylerH"); break;
		case 1: displayBio(R.id.member2, "TylerW"); break;
		case 2: displayBio(R.id.member3, "Jaye"); break;
		case 3: displayBio(R.id.member4, "Maaz"); break;
		case 4: displayBio(R.id.member5, "Others"); break;
		case 5: displayBio(R.id.member6, "Jon"); break;
		case 6: displayBio(R.id.member7, "Sohail"); break;
		case 7: displayBio(R.id.member8, "Nick"); break;
		case 8: displayBio(R.id.member9, "Rick"); break;
		}
	    }
	});
    }

    private void displayBio(int member, final String name) {
	Intent i = new Intent();
	i.putExtra("name", name);
	if (name.equals("Others"))
	    i.setClass(Bios.this, Acknowledgements.class);
	else
	    i.setClass(Bios.this, BioPage.class);
	startActivity(i);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	return Tabs.doCreateOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	return Tabs.doOptionsItemSelected(this, item);
    }
}