package ke.tang.slidemenu.sample.activity;

import android.os.Bundle;
import android.widget.TextView;

import ke.tang.slidemenu.SlideMenu;
import ke.tang.slidemenu.sample.BaseSlideMenuActivity;
import ke.tang.slidemenu.sample.R;

public class SlideMenuCallback extends BaseSlideMenuActivity implements
        SlideMenu.OnSlideStateChangeListener {
    private final static String OFFSET_PERCENT = "OffsetPercent";
    private final static String SLIDE_STATE = "SlideState";

    private int mSlideState;
    private float mOffsetPercent;
    private TextView mTextView;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setSlideRole(R.layout.layout_primary_menu);
        setSlideRole(R.layout.layout_secondary_menu);
        setSlideRole(R.layout.layout_slidemenu_callback);

        mTextView = findViewById(R.id.textView);
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mOffsetPercent = savedInstanceState.getFloat(OFFSET_PERCENT);
        mSlideState = savedInstanceState.getInt(SLIDE_STATE);
        updateText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(OFFSET_PERCENT, mOffsetPercent);
        outState.putInt(SLIDE_STATE, mSlideState);
    }

    private void updateText() {
        int slideStateRes;
        switch (mSlideState) {
            default:
            case SlideMenu.STATE_CLOSE:
                slideStateRes = R.string.slidemenu_state_close;
                break;
            case SlideMenu.STATE_OPEN_LEFT:
            case SlideMenu.STATE_OPEN_RIGHT:
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
