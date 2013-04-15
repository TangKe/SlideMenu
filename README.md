SlideMenu
=========
SlideMenu is a open source android library proivde slide effect to the layout and show the hidden menu behind the layout

#Setup
-----
To enable fantasitic feature to your project with the following steps:
* Download the project from [GitHub][1]
* Import it to your Eclipse workspace
* Set your project properties to add a android project library, and select SlideMenu

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
*NOTE*: the child view of SlideMenu must specified layout_role attribute

[1]: http://www.github.com/TangKe/SlideMenu.git
