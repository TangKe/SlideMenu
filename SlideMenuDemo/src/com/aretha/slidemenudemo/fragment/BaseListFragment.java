package com.aretha.slidemenudemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.aretha.slidemenudemo.R;

public class BaseListFragment extends ListFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setBackgroundColor(Color.WHITE);
		setListAdapter(ArrayAdapter.createFromResource(getActivity(),
				R.array.data, android.R.layout.simple_list_item_1));
	}
}
