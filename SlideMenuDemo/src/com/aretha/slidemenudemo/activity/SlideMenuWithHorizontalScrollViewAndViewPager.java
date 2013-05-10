package com.aretha.slidemenudemo.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.aretha.slidemenu.ScrollDetector;
import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;
import com.aretha.slidemenudemo.widget.DragableFragmentPagerAdapter;

public class SlideMenuWithHorizontalScrollViewAndViewPager extends
		BaseSlideMenuActivity implements ScrollDetector {
	private HorizontalScrollView mHorizontalScrollView;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlideRole(R.layout.layout_slidemenu_with_horizontal_scroll_view_and_view_pager);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new DragableFragmentPagerAdapter(this,
				getSupportFragmentManager()));
		getSlideMenu().setScrollDetector(this);
	}

	@Override
	public boolean isScrollable(View v, float dx, float x, float y) {
		if (v == mHorizontalScrollView) {
			HorizontalScrollView horizontalScrollView = mHorizontalScrollView;
			final int scrollX = horizontalScrollView.getScrollX();
			return (dx < 0 && scrollX < horizontalScrollView.getChildAt(0)
					.getWidth() - horizontalScrollView.getWidth())
					|| (dx > 0 && scrollX > 0);
		} else if (v == mViewPager) {
			ViewPager viewPager = mViewPager;
			PagerAdapter pagerAdapter = mViewPager.getAdapter();
			if (null == pagerAdapter) {
				return false;
			}
			final int currentItem = viewPager.getCurrentItem();
			return (dx < 0 && currentItem < pagerAdapter.getCount() - 1)
					|| (dx > 0 && currentItem > 0);

		}
		return false;
	}
}
