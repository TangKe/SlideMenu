package com.aretha.slidemenudemo.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

import com.aretha.slidemenu.SlideMenu;
import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;

public class SlideMenuAttributeActivity extends BaseSlideMenuActivity implements
		OnSeekBarChangeListener, OnCheckedChangeListener,
		android.widget.RadioGroup.OnCheckedChangeListener, OnClickListener {
	private SlideMenu mSlideMenu;
	private SeekBar mPrimaryShadowWidth;
	private SeekBar mSecondaryShadowWidth;
	private RadioGroup mSlideMode;
	private ToggleButton mSlideLeft;
	private ToggleButton mSlideRight;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_attribute);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);
		mSlideMenu = getSlideMenu();

		mPrimaryShadowWidth = (SeekBar) findViewById(R.id.primaryShadowWidth);
		mSecondaryShadowWidth = (SeekBar) findViewById(R.id.secondaryShadowWidth);
		mSlideMode = (RadioGroup) findViewById(R.id.slideMode);
		mSlideLeft = (ToggleButton) findViewById(R.id.slideLeft);
		mSlideRight = (ToggleButton) findViewById(R.id.slideRight);

		mPrimaryShadowWidth.setOnSeekBarChangeListener(this);
		mSecondaryShadowWidth.setOnSeekBarChangeListener(this);
		mSlideMode.setOnCheckedChangeListener(this);
		mSlideLeft.setOnCheckedChangeListener(this);
		mSlideRight.setOnCheckedChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (mPrimaryShadowWidth == seekBar) {
			mSlideMenu.setPrimaryShadowWidth(progress);
		} else if (mSecondaryShadowWidth == seekBar) {
			mSlideMenu.setSecondaryShadowWidth(progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int slideDirectionFlag = mSlideMenu.getSlideDirection();
		if (mSlideLeft == buttonView) {
			if (isChecked) {
				slideDirectionFlag |= SlideMenu.FLAG_DIRECTION_LEFT;
			} else {
				slideDirectionFlag &= ~SlideMenu.FLAG_DIRECTION_LEFT;
			}
		} else if (mSlideRight == buttonView) {
			if (isChecked) {
				slideDirectionFlag |= SlideMenu.FLAG_DIRECTION_RIGHT;
			} else {
				slideDirectionFlag &= ~SlideMenu.FLAG_DIRECTION_RIGHT;
			}
		}
		mSlideMenu.setSlideDirection(slideDirectionFlag);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.slideContent:
			mSlideMenu.setSlideMode(SlideMenu.MODE_SLIDE_CONTENT);
			break;
		case R.id.slideWindow:
			mSlideMenu.setSlideMode(SlideMenu.MODE_SLIDE_WINDOW);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.open:
			mSlideMenu.open(true, true);
			break;
		case R.id.close:
			mSlideMenu.close(true);
			break;
		}
	}
}
