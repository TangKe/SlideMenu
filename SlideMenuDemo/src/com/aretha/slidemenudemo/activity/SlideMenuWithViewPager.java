package com.aretha.slidemenudemo.activity;

import android.support.v4.view.ViewPager;

import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;
import com.aretha.slidemenudemo.widget.DragableFragmentPagerAdapter;

public class SlideMenuWithViewPager extends BaseSlideMenuActivity {
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_view_pager);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new DragableFragmentPagerAdapter(this,
				getSupportFragmentManager()));
	}

}
