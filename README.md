SlideMenu([Demo][1])<img src="https://api.travis-ci.org/TangKe/SlideMenu.png?branch=master" />
=========
SlideMenu is a open source android library provide slide effect to the layout and show the hidden menu behind the layout

<img src="https://github.com/TangKe/SlideMenu/wiki/images/snap1.png" width="140" />
<img src="https://github.com/TangKe/SlideMenu/wiki/images/snap2.png" width="140" />
<img src="https://github.com/TangKe/SlideMenu/wiki/images/snap3.png" width="140" />
<img src="https://github.com/TangKe/SlideMenu/wiki/images/snap4.png" width="140" />
<img src="https://github.com/TangKe/SlideMenu/wiki/images/snap5.png" width="140" />

#Setup
To enable fantastic feature in your project just add follow line to your build.gradle file
```groovy
compile 'ke.tang:slidemenu:1.2.2'
```

#Usage
Use the SlideMenu as a View as usual, Java and XML are both supported
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
<ke.tang.slidemenu.SlideMenu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:slidemenudemo="http://schemas.android.com/apk/res-auto"
    android:id="@+id/slideMenu"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    slidemenudemo:primaryShadowDrawable="reference"
    slidemenudemo:primaryShadowWidth="dimension"
    slidemenudemo:secondaryShadowWidth="dimension"
    slidemenudemo:secondaryShadowDrawable="reference"
    slidemenudemo:slideDirection="left|right" >

    <View
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        slidemenudemo:layout_role="content" />

    <View
        android:layout_width="200dip"
        android:layout_height="match_parent"
        slidemenudemo:layout_role="primaryMenu" />

    <View
        android:layout_width="300dip"
        android:layout_height="match_parent"
        slidemenudemo:layout_role="secondaryMenu" />

</ke.tang.slidemenu.SlideMenu>
```
*NOTE: the children of SlideMenu must be specified layout_role attribute, otherwise the SlideMenu will throw a Exception. SlideMenu must be the root of layout.*
* `primaryShadowWidth` the shadow width above primary menu, left side of content
* `secondaryShadowWidth` the shadow width above secondary menu, right side of content
* `primaryShadowDrawable` the shadow drawable above primary menu, left side of content
* `secondaryShadowDrawable` the shadow drawable above secondary menu, right side of content
* `slideDirection` specified the slide direction of SlideMenu, left, right, left|right

#Author
Tang Ke
tang.ke@me.com

#Trello
Please visit this [board][2] to see the plan of this repo

#License
    Copyright (c) 2011-2013 Tang Ke
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/TangKe/SlideMenu/wiki/SlideMenu.apk
[2]: https://trello.com/board/slidemenu/514932078a91614c640056df
[3]: https://travis-ci.org/TangKe/SlideMenu
