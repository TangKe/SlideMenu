package com.aretha.slidemenudemo.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aretha.slidemenu.ScrollDetector;
import com.aretha.slidemenudemo.BaseSlideMenuActivity;
import com.aretha.slidemenudemo.R;

public class SlideMenuWithWebView extends BaseSlideMenuActivity implements
		ScrollDetector {
	private WebView mWebView;
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

		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.setWebViewClient(mViewClient);
		mWebView.loadUrl("http://www.github.com/TangKe/SlideMenu");
		getSlideMenu().setScrollDetector(this);
	}

	@Override
	public boolean isScrollable(View v, float dx, float x, float y) {
		if (v == mWebView) {
			try {
				// Because this method is protected
				Method computeHorizontalScrollOffsetMethod = WebView.class
						.getDeclaredMethod("computeHorizontalScrollOffset");
				Method computeHorizontalScrollRangeMethod = WebView.class
						.getDeclaredMethod("computeHorizontalScrollRange");
				computeHorizontalScrollOffsetMethod.setAccessible(true);
				computeHorizontalScrollRangeMethod.setAccessible(true);

				final int horizontalScrollOffset = (Integer) computeHorizontalScrollOffsetMethod
						.invoke(v);
				final int horizontalScrollRange = (Integer) computeHorizontalScrollRangeMethod
						.invoke(v);

				return (dx > 0 && v.getScrollX() > 0)
						|| (dx < 0 && horizontalScrollOffset < horizontalScrollRange
								- v.getWidth());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}
		return false;
	}
}
