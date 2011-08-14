package edu.purdue.cs.vw;

import android.os.Bundle;

public class BioPage extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(getIntent().getIntExtra("layout", R.layout.splash)); // Could report an error... :-)
    }

}