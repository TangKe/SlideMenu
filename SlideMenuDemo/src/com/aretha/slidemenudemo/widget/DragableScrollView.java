package com.aretha.slidemenudemo.widget;

import com.aretha.slidemenu.DragDetector;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class DragableScrollView extends HorizontalScrollView implements DragDetector {

	public DragableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DragableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragableScrollView(Context context) {
		super(context);
	}

	@Override
	public boolean isDragLeftable() {
		return getChildAt(0).getWidth() - getWidth() <= getScrollX();
	}

	@Override
	public boolean isDragRightable() {
		return getScrollX() == 0;
	}

}
