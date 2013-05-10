/* Copyright (c) 2011-2013 Tang Ke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aretha.slidemenu;

import android.view.MotionEvent;
import android.view.View;

/**
 * A helper interface to tell {@link SlideMenu} should/shouldn't intercept
 * {@link MotionEvent}, ViewCompat.canScrollHorizontally(View v, int direction)
 * is not implement below API level 9, so provide this interface to detect
 * scroll
 * 
 * @author Tank
 * 
 */
public interface ScrollDetector {
	/**
	 * This method will invoke many times unless return true or no view can
	 * scroll
	 * 
	 * @param v
	 *            all of children inside SlideMenu which contain the touch point
	 * @param dx
	 *            the changes of x
	 * @param x
	 *            x coordinate relative to the v(first parameter)'s parent
	 * @param y
	 *            y coordinate relative to the v(first parameter)'s parent
	 * @return
	 */
	public boolean isScrollable(View v, float dx, float x, float y);
}
