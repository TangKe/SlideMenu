package com.aretha.slidemenudemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.aretha.slidemenu.SlideMenu;

public class BaseSlideMenuActivity extends FragmentActivity {
	private SlideMenu mSlideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_slidemenu);
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mSlideMenu = (SlideMenu) findViewById(R.id.slideMenu);
	}

	public void setSlideRole(int res) {
		if (null == mSlideMenu) {
			return;
		}

		getLayoutInflater().inflate(res, mSlideMenu, true);
	}
	
	public SlideMenu getSlideMenu(){
		return mSlideMenu;
	}
}
