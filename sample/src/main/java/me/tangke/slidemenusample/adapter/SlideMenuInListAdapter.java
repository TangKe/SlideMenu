package me.tangke.slidemenusample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import me.tangke.slidemenu.SlideMenu;
import me.tangke.slidemenusample.R;

public class SlideMenuInListAdapter extends BaseAdapter {
	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.layout_slidemenu_item,
					parent, false);
		}

		((SlideMenu) convertView).close(false);
		return convertView;
	}

}
