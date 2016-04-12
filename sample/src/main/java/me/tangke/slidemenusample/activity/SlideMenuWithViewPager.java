package me.tangke.slidemenusample.activity;

import android.support.v4.view.ViewPager;

import me.tangke.slidemenusample.BaseSlideMenuActivity;
import me.tangke.slidemenusample.R;
import me.tangke.slidemenusample.widget.DragableFragmentPagerAdapter;


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
