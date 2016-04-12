package me.tangke.slidemenusample.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import me.tangke.slidemenusample.BaseSlideMenuActivity;
import me.tangke.slidemenusample.R;
import me.tangke.slidemenusample.widget.DragableFragmentPagerAdapter;


public class SlideMenuWithHorizontalScrollViewAndViewPager extends
		BaseSlideMenuActivity {
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlideRole(R.layout.layout_slidemenu_with_horizontal_scroll_view_and_view_pager);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new DragableFragmentPagerAdapter(this,
				getSupportFragmentManager()));
	}

}
