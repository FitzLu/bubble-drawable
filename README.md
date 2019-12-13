# BubbleDrawable
Build a drawable with triangle indication in android

[![](https://jitpack.io/v/FitzLu/bubble-drawable.svg)](https://jitpack.io/#FitzLu/bubble-drawable)

| | | | |
|:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:|
|![sample1](https://raw.githubusercontent.com/FitzLu/bubble-drawable/master/images/bubblesample1.png)|![sample2](https://raw.githubusercontent.com/FitzLu/bubble-drawable/master/images/bubblesample2.png)|![sample3](https://raw.githubusercontent.com/FitzLu/bubble-drawable/master/images/bubblesample3.png)|![sample4](https://raw.githubusercontent.com/FitzLu/bubble-drawable/master/images/bubblesample4.png)

## Usage
```
val drawable = BubbleDrawable().also {
            it.setTriangleWidth(resources.getDimension(R.dimen.bbd_triangle_width))
            it.setTriangleHeight(resources.getDimension(R.dimen.bbd_triangle_height))
            it.setCorners(floatArrayOf(resources.getDimension(R.dimen.bbd_dp_10), resources.getDimension(R.dimen.bbd_dp_10),
                resources.getDimension(R.dimen.bbd_dp_10), resources.getDimension(R.dimen.bbd_dp_10)))
            it.setStrokeColor(Color.BLUE)
            it.setStrokeWidth(resources.getDimension(R.dimen.bbd_dp_4))
            it.setSolidColor(Color.GREEN)
            it.setTriangleBias(0.618f)
            it.setTriangleLocation(BubbleDrawable.TriangleLocation.locTop)
        }
```

## Import
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.FitzLu:bubble-drawable:1.0.1'
}
```

## License
This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/FitzLu/bubble-drawable/blob/master/LICENSE) file for details