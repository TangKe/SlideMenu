package me.tangke.slidemenusample.activity;

import android.app.ListActivity;
import android.os.Bundle;

import me.tangke.slidemenusample.adapter.SlideMenuInListAdapter;

public class SlideMenuInListView extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new SlideMenuInListAdapter());
	}
}
