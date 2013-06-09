package com.aretha.slidemenu.utils;

import android.view.View;

import com.aretha.slidemenu.utils.ScrollDetectors.IScrollDetector;

public interface ScrollDetectorFactory {
	public IScrollDetector newScrollDetector(View v);
}
