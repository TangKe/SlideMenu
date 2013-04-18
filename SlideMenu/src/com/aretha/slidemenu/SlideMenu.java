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

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

/**
 * User can swipe left/right to show the hide menu
 * 
 * @author Tank
 * 
 */
public class SlideMenu extends ViewGroup {
	private final static int MAX_DURATION = 500;
	private static int STATUS_BAR_HEIGHT;

	public final static int FLAG_DIRECTION_LEFT = 1 << 0;
	public final static int FLAG_DIRECTION_RIGHT = 1 << 1;

	public final static int MODE_SLIDE_WINDOW = 1;
	public final static int MODE_SLIDE_CONTENT = 2;

	private final static int STATE_OPEN = 1;
	private final static int STATE_CLOSE = 2;
	private final static int STATE_DRAG = 3;
	private final static int STATE_SCROLL = 4;

	private final static int POSITION_LEFT = -1;
	private final static int POSITION_MIDDLE = 0;
	private final static int POSITION_RIGHT = 1;
	private int mCurrentContentPosition;
	private int mCurrentState;

	private DragDetector mDragDetector;

	private View mContent;
	private View mPrimaryMenu;
	private View mSecondaryMenu;

	private int mTouchSlop;

	private float mPressedX;
	private float mLastMotionX;
	private volatile int mCurrentContentOffset;

	private int mContentBoundsLeft;
	private int mContentBoundsRight;

	private boolean mIsTapContent;
	private Rect mContentHitRect;

	@ExportedProperty
	private Drawable mPrimaryShadowDrawable;
	@ExportedProperty
	private Drawable mSecondaryShadowDrawable;
	@ExportedProperty
	private float mPrimaryShadowWidth;
	@ExportedProperty
	private float mSecondaryShadowWidth;
	private int mSlideDirectionFlag;

	private int mSlideMode = MODE_SLIDE_CONTENT;

	private int mWidth;
	private int mHeight;

	private VelocityTracker mVelocityTracker;
	private Scroller mScroller;

	private static final Interpolator mInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t * t * t + 1.0f;
		}
	};

	public SlideMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// we want to draw drop shadow of content
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mVelocityTracker = VelocityTracker.obtain();
		mScroller = new Scroller(context, mInterpolator);
		mContentHitRect = new Rect();
		STATUS_BAR_HEIGHT = (int) getStatusBarHeight(context);
		setWillNotDraw(false);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SlideMenu, defStyle, 0);

		// Set the shadow attributes
		setPrimaryShadowWidth(a.getDimension(
				R.styleable.SlideMenu_primaryShadowWidth, 30));
		setSecondaryShadowWidth(a.getDimension(
				R.styleable.SlideMenu_secondaryShadowWidth, 30));

		Drawable primaryShadowDrawable = a
				.getDrawable(R.styleable.SlideMenu_primaryShadowDrawable);
		if (null == primaryShadowDrawable) {
			primaryShadowDrawable = new GradientDrawable(
					Orientation.LEFT_RIGHT, new int[] { Color.TRANSPARENT,
							Color.argb(99, 0, 0, 0) });
		}
		setPrimaryShadowDrawable(primaryShadowDrawable);

		Drawable secondaryShadowDrawable = a
				.getDrawable(R.styleable.SlideMenu_primaryShadowDrawable);
		if (null == secondaryShadowDrawable) {
			secondaryShadowDrawable = new GradientDrawable(
					Orientation.LEFT_RIGHT, new int[] {
							Color.argb(99, 0, 0, 0), Color.TRANSPARENT });
		}
		setSecondaryShadowDrawable(secondaryShadowDrawable);

		mSlideDirectionFlag = a.getInt(R.styleable.SlideMenu_slideDirection,
				FLAG_DIRECTION_LEFT | FLAG_DIRECTION_RIGHT);

		a.recycle();
	}

	public SlideMenu(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.slideMenuStyle);
	}

	public SlideMenu(Context context) {
		this(context, null);
	}

	/**
	 * Retrieve the height of status bar that defined in system
	 * 
	 * @param context
	 * @return
	 */
	public static float getStatusBarHeight(Context context) {
		Resources resources = context.getResources();
		int statusBarIdentifier = resources.getIdentifier("status_bar_height",
				"dimen", "android");
		if (0 != statusBarIdentifier) {
			return resources.getDimension(statusBarIdentifier);
		}
		return 0;
	}

	/**
	 * Resolve the attribute slideMode
	 */
	protected void resolveSlideMode() {
		final ViewGroup decorView = (ViewGroup) getRootView();
		final ViewGroup contentContainer = (ViewGroup) decorView
				.findViewById(android.R.id.content);
		final View content = mContent;
		if (null == decorView || null == content) {
			return;
		}

		TypedValue value = new TypedValue();
		getContext().getTheme().resolveAttribute(
				android.R.attr.windowBackground, value, true);

		switch (mSlideMode) {
		case MODE_SLIDE_WINDOW: {
			// remove this view from parent
			removeViewFromParent(this);
			// copy the layoutparams of content
			LayoutParams contentLayoutParams = new LayoutParams(
					content.getLayoutParams());
			// remove content view from this view
			removeViewFromParent(content);
			// add content to layout root view
			contentContainer.addView(content);

			// get window with ActionBar
			View decorChild = decorView.getChildAt(0);
			decorChild.setBackgroundResource(0);
			removeViewFromParent(decorChild);
			addView(decorChild, contentLayoutParams);

			// add this view to root view
			decorView.addView(this);
			setBackgroundResource(value.resourceId);
		}
			break;
		case MODE_SLIDE_CONTENT: {
			// remove this view from decor view
			setBackgroundResource(0);
			removeViewFromParent(this);
			// get the origin content view from the content wrapper
			View originContent = contentContainer.getChildAt(0);
			// this is the decor child remove from decor view
			View decorChild = mContent;
			LayoutParams layoutParams = (LayoutParams) decorChild
					.getLayoutParams();
			// remove the origin content from content wrapper
			removeViewFromParent(originContent);
			// remove decor child from this view
			removeViewFromParent(decorChild);
			// restore the decor child to decor view
			decorChild.setBackgroundResource(value.resourceId);
			decorView.addView(decorChild);
			// add this view to content wrapper
			contentContainer.addView(this);
			// add the origin content to this view
			addView(originContent, layoutParams);
		}
			break;
		}
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		if (!(params instanceof LayoutParams)) {
			throw new IllegalArgumentException(
					"LayoutParams must a instance of LayoutParams");
		}

		LayoutParams layoutParams = (LayoutParams) params;
		switch (layoutParams.role) {
		case LayoutParams.ROLE_CONTENT:
			mContent = child;
			break;
		case LayoutParams.ROLE_PRIMARY_MENU:
			mPrimaryMenu = child;
			break;
		case LayoutParams.ROLE_SECONDARY_MENU:
			mSecondaryMenu = child;
			break;
		default:
			return;
		}
		super.addView(child, index, params);
	}

	/**
	 * Remove view child it's parent node, if the view does not have parent.
	 * ignore
	 * 
	 * @param view
	 */
	public static void removeViewFromParent(View view) {
		if (null == view) {
			return;
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (null == parent) {
			return;
		}
		parent.removeView(view);
	}

	/**
	 * Set the shadow drawable of left side
	 * 
	 * @param shadowDrawable
	 */
	public void setPrimaryShadowDrawable(Drawable shadowDrawable) {
		mPrimaryShadowDrawable = shadowDrawable;
	}

	/**
	 * Get the shadow drawable of left side
	 * 
	 * @return
	 */
	public Drawable getPrimaryShadowDrawable() {
		return mPrimaryShadowDrawable;
	}

	/**
	 * Get the shadow drawable of right side
	 * 
	 * @return
	 */
	public Drawable getSecondaryShadowDrawable() {
		return mSecondaryShadowDrawable;
	}

	/**
	 * Set the shadow drawable of right side
	 * 
	 * @param secondaryShadowDrawable
	 */
	public void setSecondaryShadowDrawable(Drawable secondaryShadowDrawable) {
		this.mSecondaryShadowDrawable = secondaryShadowDrawable;
	}

	/**
	 * Get the slide mode current specified
	 * 
	 * @return
	 */
	public int getSlideMode() {
		return mSlideMode;
	}

	/**
	 * Set the slide mode:<br/>
	 * {@link #MODE_SLIDE_CONTENT} {@link #MODE_SLIDE_WINDOW}
	 * 
	 * @param slideMode
	 */
	public void setSlideMode(int slideMode) {
		if (mSlideMode == slideMode) {
			return;
		}
		mSlideMode = slideMode;
		resolveSlideMode();
	}

	/**
	 * Open the SlideMenu
	 * 
	 * @param isSlideLeft
	 * @param isAnimated
	 */
	public void open(boolean isSlideLeft, boolean isAnimated) {
		if (STATE_OPEN == mCurrentState) {
			return;
		}

		int targetOffset = isSlideLeft ? mContentBoundsLeft
				: mContentBoundsRight;

		if (isAnimated) {
			smoothScrollContentTo(targetOffset);
		} else {
			mScroller.abortAnimation();
			setCurrentOffset(targetOffset);
		}
	}

	/**
	 * Close the SlideMenu
	 * 
	 * @param isAnimated
	 */
	public void close(boolean isAnimated) {
		if (STATE_CLOSE == mCurrentState) {
			return;
		}

		if (isAnimated) {
			smoothScrollContentTo(0);
		} else {
			mScroller.abortAnimation();
			setCurrentOffset(0);
		}
	}

	/**
	 * Get current slide direction, {@link #FLAG_DIRECTION_LEFT},
	 * {@link #FLAG_DIRECTION_RIGHT} or {@link #FLAG_DIRECTION_LEFT}|
	 * {@link #FLAG_DIRECTION_RIGHT}
	 * 
	 * @return
	 */
	public int getSlideDirection() {
		return mSlideDirectionFlag;
	}

	/**
	 * Set slide direction
	 * 
	 * @param slideDirectionFlag
	 */
	public void setSlideDirection(int slideDirectionFlag) {
		this.mSlideDirectionFlag = slideDirectionFlag;
	}

	/**
	 * Equals invoke {@link #smoothScrollContentTo(int, float)} with 0 velocity
	 * 
	 * @param targetOffset
	 */
	public void smoothScrollContentTo(int targetOffset) {
		smoothScrollContentTo(targetOffset, 0);
	}

	/**
	 * Perform a smooth slide of content, the offset of content will limited to
	 * menu width
	 * 
	 * @param targetOffset
	 * @param velocity
	 */
	public void smoothScrollContentTo(int targetOffset, float velocity) {
		mCurrentState = STATE_SCROLL;
		int distance = targetOffset - mCurrentContentOffset;
		velocity = Math.abs(velocity);
		int duration = 400;
		if (velocity > 0) {
			duration = 3 * Math.round(1000 * Math.abs(distance / velocity));
		}
		duration = Math.min(duration, MAX_DURATION);
		mScroller.abortAnimation();
		mScroller.startScroll(mCurrentContentOffset, 0, distance, 0, duration);
		invalidate();
	}

	/**
	 * Get current {@link DragDetector}
	 * 
	 * @return
	 */
	public DragDetector getDragDetector() {
		return mDragDetector;
	}

	/**
	 * Set a {@link DragDetector} to detect whether dragable left/right, this is
	 * useful for content with {@link ViewPager}, {@link HorizontalScrollView}
	 * inside
	 * 
	 * @param dragDetector
	 */
	public void setDragDetector(DragDetector dragDetector) {
		this.mDragDetector = dragDetector;
	}

	private boolean isTapContent(float x, float y) {
		final View content = mContent;
		if (null != content) {
			content.getHitRect(mContentHitRect);
			return mContentHitRect.contains((int) x, (int) y);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final float x = ev.getX();
		final float y = ev.getY();
		final int currentState = mCurrentState;
		if (STATE_DRAG == currentState || STATE_SCROLL == currentState) {
			return true;
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPressedX = mLastMotionX = x;
			mIsTapContent = isTapContent(x, y);
			return STATE_OPEN == currentState && mIsTapContent;
		case MotionEvent.ACTION_MOVE:
			float distance = x - mPressedX;
			if (Math.abs(distance) >= mTouchSlop && mIsTapContent) {
				DragDetector dragDetector = mDragDetector;
				if (null == dragDetector) {
					mCurrentState = STATE_DRAG;
					return true;
				}

				if ((distance > 0 && dragDetector.isDragRightable())
						|| (distance < 0 && dragDetector.isDragLeftable())) {
					mCurrentState = STATE_DRAG;
					return true;
				}
			}
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		final float x = event.getX();
		final float y = event.getY();
		final int currentState = mCurrentState;
		final boolean isTapContent = mIsTapContent;

		mVelocityTracker.addMovement(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPressedX = mLastMotionX = x;
			mIsTapContent = isTapContent(x, y);
			if (mIsTapContent) {
				mScroller.abortAnimation();
			}
			mVelocityTracker = VelocityTracker.obtain();
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(x - mLastMotionX) >= mTouchSlop && isTapContent
					&& currentState != STATE_DRAG) {
				mCurrentState = STATE_DRAG;
			}
			if (STATE_DRAG != currentState) {
				return false;
			}

			drag(mLastMotionX, x);
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			if (STATE_DRAG == currentState) {
				mVelocityTracker.computeCurrentVelocity(1000);
				endDrag(x, mVelocityTracker.getXVelocity());
				mVelocityTracker.clear();
			} else if (isTapContent) {
				performContentClick();
			}
			mIsTapContent = false;
			break;
		}

		return true;
	}

	/**
	 * Get current primary menu
	 * 
	 * @return
	 */
	public View getPrimaryMenu() {
		return mPrimaryMenu;
	}

	/**
	 * Get current secondary menu
	 * 
	 * @return
	 */
	public View getSecondaryMenu() {
		return mSecondaryMenu;
	}

	/**
	 * Perform click on the content
	 */
	public void performContentClick() {
		if (STATE_OPEN == mCurrentState) {
			smoothScrollContentTo(0);
		}
	}

	protected void drag(float lastX, float x) {
		mCurrentContentOffset += (int) (x - lastX);
		setCurrentOffset(mCurrentContentOffset);
	}

	private void invalideMenuState() {
		mCurrentContentPosition = mCurrentContentOffset < 0 ? POSITION_LEFT
				: (mCurrentContentOffset == 0 ? POSITION_MIDDLE
						: POSITION_RIGHT);
		switch (mCurrentContentPosition) {
		case POSITION_LEFT:
			invalidateViewVisibility(mPrimaryMenu, View.INVISIBLE);
			invalidateViewVisibility(mSecondaryMenu, View.VISIBLE);
			break;
		case POSITION_MIDDLE:
			invalidateViewVisibility(mPrimaryMenu, View.INVISIBLE);
			invalidateViewVisibility(mSecondaryMenu, View.INVISIBLE);
			break;
		case POSITION_RIGHT:
			invalidateViewVisibility(mPrimaryMenu, View.VISIBLE);
			invalidateViewVisibility(mSecondaryMenu, View.INVISIBLE);
			break;
		}
	}

	@Override
	public boolean shouldDelayChildPressedState() {
		return false;
	}

	private void invalidateViewVisibility(View view, int visibility) {
		if (null != view && view.getVisibility() != visibility) {
			view.setVisibility(visibility);
		}
	}

	protected void endDrag(float x, float velocity) {
		// TODO Not use this to detect whether we should scroll the content
		// temporary
		final int currentContentOffset = mCurrentContentOffset;
		final int currentContentPosition = mCurrentContentPosition;
		boolean velocityMatched = Math.abs(velocity) > 400;
		switch (currentContentPosition) {
		case POSITION_LEFT:
			if ((velocity > 0 && velocityMatched)
					|| (velocity <= 0 && !velocityMatched)) {
				smoothScrollContentTo(0, velocity);
			} else if ((velocity < 0 && velocityMatched)
					|| (velocity >= 0 && !velocityMatched)) {
				smoothScrollContentTo(mContentBoundsLeft, velocity);
			}
			break;
		case POSITION_MIDDLE:
			mCurrentState = STATE_CLOSE;
			break;
		case POSITION_RIGHT:
			if ((velocity > 0 && velocityMatched)
					|| (velocity <= 0 && !velocityMatched)) {
				smoothScrollContentTo(mContentBoundsRight, velocity);
			} else if ((velocity < 0 && velocityMatched)
					|| (velocity >= 0 && !velocityMatched)) {
				smoothScrollContentTo(0, velocity);
			}
			break;
		}
	}

	private void setCurrentOffset(int currentOffset) {
		final int slideDirectionFlag = mSlideDirectionFlag;
		mCurrentContentOffset = Math
				.min((slideDirectionFlag & FLAG_DIRECTION_RIGHT) == FLAG_DIRECTION_RIGHT ? mContentBoundsRight
						: 0,
						Math.max(
								currentOffset,
								(slideDirectionFlag & FLAG_DIRECTION_LEFT) == FLAG_DIRECTION_LEFT ? mContentBoundsLeft
										: 0));

		invalideMenuState();
		invalidate();
		requestLayout();
	}

	@Override
	public void computeScroll() {
		if (STATE_SCROLL == mCurrentState || STATE_OPEN == mCurrentState) {
			if (mScroller.computeScrollOffset()) {
				setCurrentOffset(mScroller.getCurrX());
			} else {
				mCurrentState = mCurrentContentOffset == 0 ? STATE_CLOSE
						: STATE_OPEN;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int count = getChildCount();
		final int slideMode = mSlideMode;
		final int statusBarHeight = STATUS_BAR_HEIGHT;

		int maxChildWidth = 0, maxChildHeight = 0;
		for (int index = 0; index < count; index++) {
			View child = getChildAt(index);
			LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
			switch (layoutParams.role) {
			case LayoutParams.ROLE_CONTENT:
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
				break;
			case LayoutParams.ROLE_PRIMARY_MENU:
			case LayoutParams.ROLE_SECONDARY_MENU:
				measureChild(
						child,
						widthMeasureSpec,
						slideMode == MODE_SLIDE_WINDOW ? MeasureSpec
								.makeMeasureSpec(
										MeasureSpec.getSize(heightMeasureSpec)
												- statusBarHeight,
										MeasureSpec.getMode(heightMeasureSpec))
								: heightMeasureSpec);
				break;
			}

			maxChildWidth = Math.max(maxChildWidth, child.getMeasuredWidth());
			maxChildHeight = Math
					.max(maxChildHeight, child.getMeasuredHeight());
		}
		maxChildWidth += getPaddingLeft() + getPaddingRight();
		maxChildHeight += getPaddingTop() + getPaddingBottom();

		setMeasuredDimension(resolveSize(maxChildWidth, widthMeasureSpec),
				resolveSize(maxChildHeight, heightMeasureSpec));
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		View parent = (View) getParent();
		if (android.R.id.content != parent.getId()
				&& MODE_SLIDE_CONTENT == mSlideMode) {
			throw new IllegalStateException(
					"SlidingMenu must be the root of layout");
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int count = getChildCount();
		final int paddingLeft = getPaddingLeft();
		final int paddingRight = getPaddingRight();
		final int paddingTop = getPaddingTop();
		final int statusBarHeight = mSlideMode == MODE_SLIDE_WINDOW ? STATUS_BAR_HEIGHT
				: 0;
		for (int index = 0; index < count; index++) {
			View child = getChildAt(index);
			final int measureWidth = child.getMeasuredWidth();
			final int measureHeight = child.getMeasuredHeight();
			LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
			switch (layoutParams.role) {
			case LayoutParams.ROLE_CONTENT:
				// we should display the content in front of all other views
				child.bringToFront();
				child.layout(mCurrentContentOffset + paddingLeft, paddingTop,
						paddingLeft + measureWidth + mCurrentContentOffset,
						paddingTop + measureHeight);
				break;
			case LayoutParams.ROLE_PRIMARY_MENU:
				mContentBoundsRight = measureWidth;
				child.layout(paddingLeft, statusBarHeight + paddingTop,
						paddingLeft + measureWidth, statusBarHeight
								+ paddingTop + measureHeight);
				break;
			case LayoutParams.ROLE_SECONDARY_MENU:
				mContentBoundsLeft = -measureWidth;
				child.layout(r - paddingRight - measureWidth, statusBarHeight
						+ paddingTop, r - paddingRight, statusBarHeight + t
						+ measureHeight);
				break;
			default:
				continue;
			}
		}
	}

	public float getPrimaryShadowWidth() {
		return mPrimaryShadowWidth;
	}

	public void setPrimaryShadowWidth(float primaryShadowWidth) {
		this.mPrimaryShadowWidth = primaryShadowWidth;
		invalidate();
	}

	public float getSecondaryShadowWidth() {
		return mSecondaryShadowWidth;
	}

	public void setSecondaryShadowWidth(float secondaryShadowWidth) {
		this.mSecondaryShadowWidth = secondaryShadowWidth;
		invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		drawShadow(canvas);
	}

	private void drawShadow(Canvas canvas) {
		if (null == mContent) {
			return;
		}
		final int left = mContent.getLeft();
		final int width = mWidth;
		final int height = mHeight;
		mPrimaryShadowDrawable.setBounds((int) (left - mPrimaryShadowWidth), 0,
				left, height);
		mPrimaryShadowDrawable.draw(canvas);

		mSecondaryShadowDrawable.setBounds(left + width, 0,
				(int) (width + left + mSecondaryShadowWidth), height);
		mSecondaryShadowDrawable.draw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	@Override
	public android.view.ViewGroup.LayoutParams generateLayoutParams(
			AttributeSet attrs) {
		LayoutParams layoutParams = new LayoutParams(getContext(), attrs);
		return layoutParams;
	}

	/**
	 * Add view role for {@link #SlidingMenu}
	 * 
	 * @author Tank
	 * 
	 */
	public static class LayoutParams extends MarginLayoutParams {
		public final static int ROLE_CONTENT = 0;
		public final static int ROLE_PRIMARY_MENU = 1;
		public final static int ROLE_SECONDARY_MENU = 2;

		public int role;

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);

			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.SlideMenu_Layout, 0, 0);

			final int indexCount = a.getIndexCount();
			for (int index = 0; index < indexCount; index++) {
				switch (a.getIndex(index)) {
				case R.styleable.SlideMenu_Layout_layout_role:
					role = a.getInt(R.styleable.SlideMenu_Layout_layout_role,
							-1);
					break;
				}
			}

			switch (role) {
			// content should match whole SlidingMenu
			case ROLE_CONTENT:
				width = MATCH_PARENT;
				height = MATCH_PARENT;
				break;
			case ROLE_SECONDARY_MENU:
			case ROLE_PRIMARY_MENU:
				height = MATCH_PARENT;
				break;
			default:
				throw new IllegalArgumentException(
						"You must specified a layout_role for this view");
			}
			a.recycle();
		}

		public LayoutParams(int width, int height) {
			super(width, height);
		}

		public LayoutParams(int width, int height, int role) {
			super(width, height);
			this.role = role;
		}

		public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
			super(layoutParams);

			if (layoutParams instanceof LayoutParams) {
				role = ((LayoutParams) layoutParams).role;
			}
		}
	}
}
