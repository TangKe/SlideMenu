package com.aretha.slidemenudemo.widget;

import com.aretha.slidemenudemo.fragment.BaseListFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DragableFragmentPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;

	public DragableFragmentPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		return BaseListFragment.instantiate(mContext,
				"com.aretha.slidemenudemo.fragment.BaseListFragment");
	}

	@Override
	public int getCount() {
		return 5;
	}

}
