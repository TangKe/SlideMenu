package com.aretha.slidemenudemo;

import java.util.List;


import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class DemoBundleActivity extends ListActivity {
	private PackageManager mPackageManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPackageManager = getPackageManager();

		List<ResolveInfo> activities = mPackageManager.queryIntentActivities(
				makeDemoActivityIntent(), PackageManager.GET_META_DATA);
		getListView().setAdapter(
				new ActivityListAdapter(this, activities, mPackageManager));
	}

	protected Intent makeDemoActivityIntent() {
		Intent intent = new Intent(Constants.DEMO_ACTION);
		return intent;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ActivityInfo resolveInfo = ((ResolveInfo) l.getItemAtPosition(position)).activityInfo;
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(resolveInfo.packageName,
				resolveInfo.name));
		startActivity(intent);
	}

}
