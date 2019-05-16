package ke.tang.slidemenu.sample.activity;

import android.app.ListActivity;
import android.os.Bundle;

import ke.tang.slidemenu.sample.adapter.SlideMenuInListAdapter;

public class SlideMenuInListView extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new SlideMenuInListAdapter());
	}
}
