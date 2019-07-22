package ke.tang.slidemenu.sample.activity;

import androidx.viewpager.widget.ViewPager;

import ke.tang.slidemenu.sample.BaseSlideMenuActivity;
import ke.tang.slidemenu.sample.R;
import ke.tang.slidemenu.sample.widget.DragableFragmentPagerAdapter;


public class SlideMenuWithViewPager extends BaseSlideMenuActivity {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setSlideRole(R.layout.layout_slidemenu_with_view_pager);
        setSlideRole(R.layout.layout_primary_menu);
        setSlideRole(R.layout.layout_secondary_menu);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new DragableFragmentPagerAdapter(this,
                getSupportFragmentManager()));
    }

}
