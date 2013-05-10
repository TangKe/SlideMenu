package com.aretha.slidemenudemo.activity;

import android.view.View;
import android.widget.HorizontalScrollView;

import com.aretha.slidemenu.ScrollDetector;
import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;

public class SlideMenuWithHorizontalScrollView extends BaseSlideMenuActivity
		implements ScrollDetector {
	private HorizontalScrollView mHorizontalScrollView;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_horizontal_scroll_view);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
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
		}
		return false;
	}
}
