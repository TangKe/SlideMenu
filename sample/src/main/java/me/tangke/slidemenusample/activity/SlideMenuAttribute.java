package me.tangke.slidemenusample.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import me.tangke.slidemenu.SlideMenu;
import me.tangke.slidemenusample.BaseSlideMenuActivity;
import me.tangke.slidemenusample.R;

public class SlideMenuAttribute extends BaseSlideMenuActivity implements
		OnSeekBarChangeListener, OnCheckedChangeListener,
		RadioGroup.OnCheckedChangeListener, OnClickListener,
		OnItemSelectedListener {
	private SlideMenu mSlideMenu;
	private TextView mPrimaryShadowWidthLabel;
	private SeekBar mPrimaryShadowWidth;
	private TextView mSecondaryShadowWidthLabel;
	private SeekBar mSecondaryShadowWidth;
	private RadioGroup mSlideMode;
	private ToggleButton mSlideLeft;
	private ToggleButton mSlideRight;
	private Spinner mInterpolator;
	private ToggleButton mEdgeSlide;
	private TextView mEdgeSlideWidthLabel;
	private SeekBar mEdgeSlideWidth;

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_attribute);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);
		mSlideMenu = getSlideMenu();

		mPrimaryShadowWidthLabel = (TextView) findViewById(R.id.primaryShadowWidthLabel);
		mPrimaryShadowWidth = (SeekBar) findViewById(R.id.primaryShadowWidth);
		mSecondaryShadowWidthLabel = (TextView) findViewById(R.id.secondaryShadowWidthLabel);
		mSecondaryShadowWidth = (SeekBar) findViewById(R.id.secondaryShadowWidth);
		mSlideMode = (RadioGroup) findViewById(R.id.slideMode);
		mSlideLeft = (ToggleButton) findViewById(R.id.slideLeft);
		mSlideRight = (ToggleButton) findViewById(R.id.slideRight);
		mInterpolator = (Spinner) findViewById(R.id.interpolator);
		mEdgeSlide = (ToggleButton) findViewById(R.id.edgeSlide);
		mEdgeSlideWidthLabel = (TextView) findViewById(R.id.edgeSlideWidthLabel);
		mEdgeSlideWidth = (SeekBar) findViewById(R.id.edgeSlideWidth);

		mPrimaryShadowWidth.setOnSeekBarChangeListener(this);
		mSecondaryShadowWidth.setOnSeekBarChangeListener(this);
		mSlideMode.setOnCheckedChangeListener(this);
		mSlideLeft.setOnCheckedChangeListener(this);
		mSlideRight.setOnCheckedChangeListener(this);
		mInterpolator.setOnItemSelectedListener(this);
		mEdgeSlide.setOnCheckedChangeListener(this);
		mEdgeSlideWidth.setOnSeekBarChangeListener(this);

		onProgressChanged(mPrimaryShadowWidth,
				mPrimaryShadowWidth.getProgress(), false);
		onProgressChanged(mSecondaryShadowWidth,
				mSecondaryShadowWidth.getProgress(), false);
		onProgressChanged(mEdgeSlideWidth, mEdgeSlideWidth.getProgress(), false);
		mInterpolator.setAdapter(ArrayAdapter.createFromResource(this,
				R.array.interpolator, android.R.layout.simple_list_item_1));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (mPrimaryShadowWidth == seekBar) {
			mPrimaryShadowWidthLabel.setText(getString(
					R.string.primary_shadow_width, progress));
			mSlideMenu.setPrimaryShadowWidth(progress);
		} else if (mSecondaryShadowWidth == seekBar) {
			mSecondaryShadowWidthLabel.setText(getString(
					R.string.secondary_shadow_width, progress));
			mSlideMenu.setSecondaryShadowWidth(progress);
		} else if (mEdgeSlideWidth == seekBar) {
			mEdgeSlideWidthLabel.setText(getString(R.string.edge_slide_width,
					progress));
			mSlideMenu.setEdgetSlideWidth(progress);
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
		} else if (mEdgeSlide == buttonView) {
			mSlideMenu.setEdgeSlideEnable(isChecked);
			return;
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

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		Interpolator interpolator;
		switch (position) {
		default:
		case 0:
			interpolator = SlideMenu.DEFAULT_INTERPOLATOR;
			break;
		case 1:
			interpolator = new AccelerateDecelerateInterpolator();
		case 2:
			interpolator = new AccelerateInterpolator();
			break;
		case 3:
			interpolator = new AnticipateInterpolator();
			break;
		case 4:
			interpolator = new AnticipateOvershootInterpolator();
		case 5:
			interpolator = new BounceInterpolator();
			break;
		case 6:
			interpolator = new DecelerateInterpolator();
			break;
		case 7:
			interpolator = new LinearInterpolator();
			break;
		case 8:
			interpolator = new OvershootInterpolator();
			break;
		}
		mSlideMenu.setInterpolator(interpolator);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
