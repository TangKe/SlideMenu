SlideMenu
=========
SlideMenu is a open source android library proivde slide effect to the layout and show the hidden menu behind the layout

#Setup
-----
To enable fantasitic feature to your project with the following steps:
1. Download the project from [GitHub][1]
2. Import it to your Eclipse workspace
3. Set your project properties to add a android project library, and select SlideMenu

#Usage
-----
Use the SlideMenu as a View as usual, Java and xml both support
##Java example:
```java
public class SlideMenuActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SlideMenu slideMenu = new SlideMenu(this);
		setContentView(slideMenu);

		// Setup the content
		View contentView = new View(this);
		slideMenu.addView(contentView, new SlideMenu.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				LayoutParams.ROLE_CONTENT));

		// Setup the primary menu
		View primaryMenu = new View(this);
		slideMenu.addView(primaryMenu, new SlideMenu.LayoutParams(300,
				LayoutParams.MATCH_PARENT, LayoutParams.ROLE_PRIMARY_MENU));

		// Setup the secondary menu
		View secondaryMenu = new View(this);
		slideMenu.addView(secondaryMenu, new SlideMenu.LayoutParams(300,
				LayoutParams.MATCH_PARENT, LayoutParams.ROLE_SECONDARY_MENU));
	}
}
```

##XML layout example:
```xml
<com.aretha.slidemenu.SlideMenu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:slidemenudemo="http://schemas.android.com/apk/res-auto"
    android:id="@+id/slideMenu"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    slidemenudemo:primaryShadowDrawable="reference"
    slidemenudemo:primaryShadowWidth="dimension"
    slidemenudemo:secondaryShadowWidth="dimension"
    slidemenudemo:sencondaryShadowDrawable="reference"
    slidemenudemo:slideDirection="left|right" >

    <View
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_role="content" />

    <View
        android:layout_width="200dip"
        android:layout_height="match_parent"
        android:layout_role="primaryMenu" />

    <View
        android:layout_width="300dip"
        android:layout_height="match_parent"
        android:layout_role="secondaryMenu" />

</com.aretha.slidemenu.SlideMenu>
```
*_NOTE_: the child view of SlideMenu must specified layout_role attribute, otherwise the SlideMenu will throw a Exception. SlideMenu must the root of layout.*
* `primaryShadowWidth` the shadow width above primary menu, left side of content
* `secondaryShadowWidth` the shadow width above secondary menu, right side of content
* `primaryShadowDrawable` the shadow drawable above primary menu, left side of content
* `sencondaryShadowDrawable` the shadow drawable above secondary menu, right side of content
* `slideDirection` specified the slide direction of SlideMenu, left, right, left|right

[1]: http://www.github.com/TangKe/SlideMenu.git
