package com.aretha.slidemenudemo.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.aretha.slidemenu.DragDetector;

public class DragableViewPager extends ViewPager implements DragDetector {

	public DragableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragableViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean isDragLeftable() {
		PagerAdapter pagerAdapter = getAdapter();
		if (null == pagerAdapter) {
			return true;
		} else {
			return getCurrentItem() == pagerAdapter.getCount() - 1;
		}
	}

	@Override
	public boolean isDragRightable() {
		PagerAdapter pagerAdapter = getAdapter();
		if (null == pagerAdapter) {
			return true;
		} else {
			return getCurrentItem() == 0;
		}
	}

}
