package ke.tang.slidemenu.sample.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import ke.tang.slidemenu.sample.BaseSlideMenuActivity;
import ke.tang.slidemenu.sample.R;
import ke.tang.slidemenu.sample.widget.DragableFragmentPagerAdapter;

public class SlideMenuWithHorizontalScrollViewAndViewPager extends
        BaseSlideMenuActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideRole(R.layout.layout_slidemenu_with_horizontal_scroll_view_and_view_pager);
        setSlideRole(R.layout.layout_primary_menu);
        setSlideRole(R.layout.layout_secondary_menu);

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new DragableFragmentPagerAdapter(this,
                getSupportFragmentManager()));
    }

}
