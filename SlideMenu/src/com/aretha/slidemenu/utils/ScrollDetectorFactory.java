package com.aretha.slidemenu.utils;

import android.view.View;

import com.aretha.slidemenu.utils.ScrollDetectors.ScrollDetector;

public interface ScrollDetectorFactory {
	public ScrollDetector newScrollDetector(View v);
}
