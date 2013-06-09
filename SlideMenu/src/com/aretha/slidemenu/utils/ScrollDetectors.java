package com.aretha.slidemenu.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;

/**
 * Provide base implements of {@link ViewPager}, {@link HorizontalScrollView},
 * {@link WebView} scroll detect, You can use {@link ScrollDetectorFactory} to
 * add your own implements of {@link ScrollDetector}
 * 
 * @author Tank
 * 
 */
public class ScrollDetectors {
	private static final WeakHashMap<Class<?>, ScrollDetector> IMPLES = new WeakHashMap<Class<?>, ScrollDetector>();
	private static ScrollDetectorFactory mFactory;

	/**
	 * Check the view is horizontal scrollable
	 * 
	 * @param v
	 * @param direction
	 * @return
	 */
	public static boolean canScrollHorizontal(View v, int direction) {
		ScrollDetector imples = getImplements(v);
		if (null == imples) {
			return false;
		}
		return imples.canScrollHorizontal(v, direction);
	}

	/**
	 * Check the view is vertical scrollable
	 * 
	 * @param v
	 * @param direction
	 * @return
	 */
	public static boolean canScrollVertical(View v, int direction) {
		ScrollDetector imples = getImplements(v);
		if (null == imples) {
			return false;
		}
		return imples.canScrollVertical(v, direction);
	}

	private static ScrollDetector getImplements(View v) {
		Class<?> clazz = v.getClass();
		ScrollDetector imple = IMPLES.get(clazz);

		if (null != imple) {
			return imple;
		}

		if (v instanceof ViewPager) {
			imple = new ViewPagerScrollDetector();
		} else if (v instanceof HorizontalScrollView) {
			imple = new HorizontalScrollViewScrollDetector();
		} else if (v instanceof WebView) {
			imple = new WebViewScrollDetector();
		} else if (null != mFactory) {
			imple = mFactory.newScrollDetector(v);
		} else {
			return null;
		}

		IMPLES.put(clazz, imple);
		return imple;
	}

	/**
	 * Base implements of {@link ScrollDetector} for {@link ViewPager}
	 * 
	 * @author Tank
	 * 
	 */
	private static class ViewPagerScrollDetector implements ScrollDetector {

		@Override
		public boolean canScrollHorizontal(View v, int direction) {
			ViewPager viewPager = (ViewPager) v;
			PagerAdapter pagerAdapter = viewPager.getAdapter();
			if (null == pagerAdapter) {
				return false;
			}

			final int currentItem = viewPager.getCurrentItem();
			return (direction < 0 && currentItem < pagerAdapter.getCount() - 1)
					|| (direction > 0 && currentItem > 0);
		}

		@Override
		public boolean canScrollVertical(View v, int direction) {
			// TODO
			return false;
		}

	}

	/**
	 * Base implements of {@link ScrollDetector} for {@link WebView}
	 * 
	 * @author Tank
	 * 
	 */
	private static class WebViewScrollDetector implements ScrollDetector {

		@Override
		public boolean canScrollHorizontal(View v, int direction) {
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

				return (direction > 0 && v.getScrollX() > 0)
						|| (direction < 0 && horizontalScrollOffset < horizontalScrollRange
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
			return false;
		}

		@Override
		public boolean canScrollVertical(View v, int direction) {
			// TODO
			return false;
		}

	}

	/**
	 * Base implements of {@link ScrollDetector} for
	 * {@link HorizontalScrollView}
	 * 
	 * @author Tank
	 * 
	 */
	private static class HorizontalScrollViewScrollDetector implements
			ScrollDetector {

		@Override
		public boolean canScrollHorizontal(View v, int direction) {
			HorizontalScrollView horizontalScrollView = (HorizontalScrollView) v;
			final int scrollX = horizontalScrollView.getScrollX();

			// Without scroll wrapper, can't scroll
			if (0 == horizontalScrollView.getChildCount()) {
				return false;
			}
			return (direction < 0 && scrollX < horizontalScrollView.getChildAt(
					0).getWidth()
					- horizontalScrollView.getWidth())
					|| (direction > 0 && scrollX > 0);
		}

		@Override
		public boolean canScrollVertical(View v, int direction) {
			// TODO
			return false;
		}

	}

	/**
	 * Factory for create new scroll detector
	 * 
	 * @param factory
	 */
	public static void setScrollDetectorFactory(ScrollDetectorFactory factory) {
		mFactory = factory;
	}

	/**
	 * 
	 * @author Tank
	 * 
	 */
	public interface ScrollDetector {
		public boolean canScrollHorizontal(View v, int direction);

		public boolean canScrollVertical(View v, int direction);
	}
}
