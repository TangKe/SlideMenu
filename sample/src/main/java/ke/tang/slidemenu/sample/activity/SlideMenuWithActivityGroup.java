package ke.tang.slidemenu.sample.activity;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ke.tang.slidemenu.SlideMenu;
import ke.tang.slidemenu.sample.R;

public class SlideMenuWithActivityGroup extends ActivityGroup {
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

        final LocalActivityManager activityManager = getLocalActivityManager();
        View primary = activityManager.startActivity("PrimaryActivity",
                new Intent(this, PrimaryActivity.class)).getDecorView();
        mSlideMenu.addView(primary, new SlideMenu.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));

        View secondary = activityManager.startActivity("SecondaryActivity",
                new Intent(this, SecondaryActivity.class)).getDecorView();
        mSlideMenu.addView(secondary, new SlideMenu.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_SECONDARY_MENU));

        View content = activityManager.startActivity("ContentActivity",
                new Intent(this, ContentActivity.class)).getDecorView();
        mSlideMenu.addView(content, new SlideMenu.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));
    }
}
