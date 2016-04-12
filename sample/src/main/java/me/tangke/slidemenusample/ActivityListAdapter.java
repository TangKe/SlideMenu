package me.tangke.slidemenusample;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ActivityListAdapter extends BaseAdapter {
	private List<ResolveInfo> mData;
	private LayoutInflater mInflater;
	private PackageManager mPackageManager;

	public ActivityListAdapter(Context context, List<ResolveInfo> data,
			PackageManager packagetManager) {
		mData = data;
		mInflater = LayoutInflater.from(context);
		mPackageManager = packagetManager;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ResolveInfo getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ResolveInfo resolveInfo = getItem(position);
		if (null == convertView) {
			convertView = mInflater.inflate(
					android.R.layout.simple_list_item_1, parent, false);
		}
		((TextView) convertView)
				.setText(resolveInfo.loadLabel(mPackageManager));
		((TextView) convertView).setCompoundDrawables(
				resolveInfo.loadIcon(mPackageManager), null, null, null);
		return convertView;
	}
}
