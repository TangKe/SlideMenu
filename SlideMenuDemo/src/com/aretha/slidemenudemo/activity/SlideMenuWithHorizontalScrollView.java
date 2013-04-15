package com.aretha.slidemenudemo.activity;

import com.aretha.slidemenu.DragDetector;
import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;

public class SlideMenuWithHorizontalScrollView extends BaseSlideMenuActivity {
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_horizontal_scroll_view);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		getSlideMenu().setDragDetector(
				(DragDetector) findViewById(R.id.horizontalScrollView));
	}
}
