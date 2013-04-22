package com.aretha.slidemenudemo.activity;

import android.widget.TextView;

import com.aretha.slidemenu.SlideMenu;
import com.aretha.slidemenu.SlideMenu.OnSlideStateChangeListener;
import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;

public class SlideMenuCallbackActivity extends BaseSlideMenuActivity implements
		OnSlideStateChangeListener {
	private int mSlideState;
	private float mOffsetPercent;
	private TextView mTextView;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);
		setSlideRole(R.layout.layout_slidemenu_callback);

		mTextView = (TextView) findViewById(R.id.textView);
		getSlideMenu().setOnSlideStateChangeListener(this);
		updateText();
	}

	@Override
	public void onSlideStateChange(int slideState) {
		mSlideState = slideState;
		updateText();
	}

	@Override
	public void onSlideOffsetChange(float offsetPercent) {
		mOffsetPercent = offsetPercent;
		updateText();
	}

	private void updateText() {
		int slideStateRes;
		switch (mSlideState) {
		default:
		case SlideMenu.STATE_CLOSE:
			slideStateRes = R.string.slidemenu_state_close;
			break;
		case SlideMenu.STATE_OPEN:
			slideStateRes = R.string.slidemenu_state_open;
			break;
		case SlideMenu.STATE_DRAG:
			slideStateRes = R.string.slidemenu_state_drag;
			break;
		case SlideMenu.STATE_SCROLL:
			slideStateRes = R.string.slidemenu_state_scroll;
			break;
		}

		mTextView.setText(getString(R.string.slidemenu_state,
				getString(slideStateRes), mOffsetPercent));
	}
}
