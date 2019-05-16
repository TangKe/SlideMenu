package ke.tang.slidemenu.sample.activity;

import ke.tang.slidemenu.sample.BaseSlideMenuActivity;
import ke.tang.slidemenu.sample.R;

public class SlideMenuWithHorizontalScrollView extends BaseSlideMenuActivity {
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_horizontal_scroll_view);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);
	}

}
