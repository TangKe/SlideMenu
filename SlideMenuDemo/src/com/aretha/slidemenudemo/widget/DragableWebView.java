package com.aretha.slidemenudemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.aretha.slidemenu.DragDetector;

public class DragableWebView extends WebView implements DragDetector {
	public DragableWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DragableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragableWebView(Context context) {
		super(context);
	}

	@Override
	public boolean isDragLeftable() {
		return computeHorizontalScrollOffset() >= computeHorizontalScrollRange()
				- getWidth();
	}

	@Override
	public boolean isDragRightable() {
		return getScrollX() <= 0;
	}

}
