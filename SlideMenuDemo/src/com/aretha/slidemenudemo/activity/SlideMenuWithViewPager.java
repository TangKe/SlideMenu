package com.aretha.slidemenudemo.activity;

import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;
import com.aretha.slidemenudemo.widget.DragableFragmentPagerAdapter;
import com.aretha.slidemenudemo.widget.DragableViewPager;

public class SlideMenuWithViewPager extends BaseSlideMenuActivity {
	private DragableViewPager mViewPager;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_view_pager);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		mViewPager = (DragableViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new DragableFragmentPagerAdapter(this,
				getSupportFragmentManager()));
		getSlideMenu().setDragDetector(mViewPager);
	}
}
