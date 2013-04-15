package com.aretha.slidemenudemo.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;
import com.aretha.slidemenudemo.widget.DragableWebView;

public class SlideMenuWithWebView extends BaseSlideMenuActivity {
	private WebViewClient mViewClient = new WebViewClient() {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		};
	};

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_web_view);
		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		DragableWebView webView = (DragableWebView) findViewById(R.id.webView);
		webView.setWebViewClient(mViewClient);
		webView.loadUrl("http://www.github.com");
		getSlideMenu().setDragDetector(webView);
	}
}
