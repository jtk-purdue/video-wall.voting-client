package edu.purdue.cs.vw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/*
 * This activity sets up the tab that allows the user to view the bio-pages of team members. 
 * Switches to an activity called BioPage, which sets the appropriate layout 
 * depending on which button is clicked. Each layout represents an individual's biography.
 */
public class Bios extends BaseActivity {
    /*
     * These dimensions are adjusted for a 480px wide display.  Team photos are scaled (down slightly) to
     * the size of a large touch-screen button.
     */
    public static final int PHOTO_DIMENSION = 138;  
//    public static final int PHOTO_DIMENSION = 100;  // TODO: Layout still not centered; use this setting to see.  
    private static final int SPACING = 2;
    private static final int LEFT_MARGIN = 24;
    
    /*
     * Pull together the individual drawable with bio information and the corresponding team member picture.
     * 
     * Note that the middle item (=4) is a special case: Harris logo and Acknowledgments activity.
     */
    private class TeamData {
	int picture;
	int layout;
	TeamData(int picture, int layout) {
	    this.picture = picture;
	    this.layout = layout;
	}
    }
    
    private TeamData[] teamData = {
	    new TeamData(R.drawable.tylerh, R.layout.tylerh),
	    new TeamData(R.drawable.tylerw, R.layout.tylerw),
	    new TeamData(R.drawable.jaye, R.layout.jaye),
	    new TeamData(R.drawable.maaz, R.layout.maaz),
	    new TeamData(R.drawable.harris, R.layout.acknowledgements),
	    new TeamData(R.drawable.jon, R.layout.jon),
	    new TeamData(R.drawable.sohail, R.layout.sohail),
	    new TeamData(R.drawable.nick, R.layout.nick),
	    new TeamData(R.drawable.rick, R.layout.rick),
    };

    class ImageAdapter extends BaseAdapter {
	private Context context;

	public ImageAdapter(Context context) {
	    this.context = context;
	}

	@Override
	public int getCount() {
	    return teamData.length;
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
		/*
		 * Scale team photos to desired size.
		 */
		iv.setLayoutParams(new GridView.LayoutParams(PHOTO_DIMENSION, PHOTO_DIMENSION));
		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		iv.setPadding(0, 0, 0, 0);
	    } else 
		iv = (ImageView) convertView;
	    iv.setImageResource(teamData[position].picture);
	    return iv;
	}
    }
    
    //private boolean splash = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.bios);
	
	/*
	 * Strategy is to set column width to be roughly 1/3rd of screen size (although we don't
	 * check the actual screen, assuming it is around 480).  Layout file sets GridVIew to wrap
	 * content inside match parent LinearLayout.  Result is centered, tightly packed grid view.
	 */
	GridView gridview = (GridView) findViewById(R.id.grid);
	gridview.setAdapter(new ImageAdapter(this));
	gridview.setStretchMode(GridView.NO_STRETCH);  // doesn't really matter--dimensions are set
	gridview.setColumnWidth(PHOTO_DIMENSION + SPACING);
	gridview.setVerticalSpacing(SPACING);
	gridview.setHorizontalSpacing(0);  // columnWidth includes the spacing, so no additional spacing needed
	gridview.setSelection(4);  // highlight middle item in table
	
	gridview.setOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent i = new Intent();
		if (position == 4) // Hack: special case the center (acknowledgements) button
		    i.setClass(Bios.this, Acknowledgements.class);
		else {
		    i.putExtra("layout", teamData[position].layout);
		    i.setClass(Bios.this, BioPage.class);
		}
		startActivity(i);
	    }
	});
    }
    
    @Override
    protected void onResume() {
	super.onResume();

	Display display = getWindowManager().getDefaultDisplay(); 
	int screenWidth = display.getWidth();
	
	LinearLayout ll = (LinearLayout) findViewById(R.id.grid_parent);
	ll.setPadding((screenWidth - 3 * (PHOTO_DIMENSION + SPACING)) / 2 - LEFT_MARGIN, 0, 0, 0);
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