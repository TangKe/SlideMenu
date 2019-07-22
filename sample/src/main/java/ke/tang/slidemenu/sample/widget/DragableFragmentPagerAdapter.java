package ke.tang.slidemenu.sample.widget;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ke.tang.slidemenu.sample.fragment.BaseListFragment;

public class DragableFragmentPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;

	public DragableFragmentPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		return Fragment.instantiate(mContext,
				BaseListFragment.class.getName());
	}

	@Override
	public int getCount() {
		return 5;
	}

}
