package ke.tang.slidemenu.sample;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import ke.tang.slidemenu.SlideMenu;

public class BaseSlideMenuActivity extends FragmentActivity {
    private SlideMenu mSlideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mSlideMenu = findViewById(R.id.slideMenu);
    }

    public void setSlideRole(int res) {
        if (null == mSlideMenu) {
            return;
        }

        getLayoutInflater().inflate(res, mSlideMenu, true);
    }

    public SlideMenu getSlideMenu() {
        return mSlideMenu;
    }
}
