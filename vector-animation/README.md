# Ⅷ. AnimatedVectorDrawable / 矢量图动画

要想彻底搞明白 Android 中的矢量动画，先理解一下关于 SVG 的相关知识还是很有必要的：  
可以点击 [SVG 讲解](https://github.com/OCNYang/Android-Animation-Set/blob/master/vector-animation/SVG.md) 
或 [Wiki](https://github.com/OCNYang/Android-Animation-Set/wiki/SVG-%E8%AE%B2%E8%A7%A3) 查看。

我们在这里只讲解 Android SVG 动画 相关的内容。相信大家一定看到过下图这个矢量动画：  

![图钉暴走](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/vector_anim.gif?token=AQ83MkaFkHlYPvrYr6vNPoJBfJAJjuspks5axebVwA%3D%3D)  

## Android SVG 动画

AnimatedVectorDrawable 类使用 ObjectAnimator 和 AnimatorSet 来促使 VectorDrawable 属性渐变，来生成动画效果。

通常可以在3个 XML 文件中定义添加动画的矢量图片：

* 在 `res/drawable/` 中拥有元素的矢量图片；
* 在 `res/drawable/` 中拥有元素且已添加动画的矢量图片；
* 在 `res/anim/` 中拥有元素的一个或多个对象动画；

添加动画的矢量图片可为 `<group>` 以及 `<path>` 元素的属性添加动画。`<group>` 元素定义路径集或子组，
而 `<path>` 元素则定义将绘制的路径。

完成一个 AnimatedVectorDrawable 通常需要定义三种不同类型文件：

**(1).** `android.graphics.drawable.VectorDrawable` 对应的 XML 文件，它以 `<vector>` 为根。我们可能让 path 
或 group 的属性进行动画，因此需要对进行动画的 path 或 group 命名。

    <vector xmlns:android="http://schemas.android.com/apk/res/android"
        android:height="64dp"
        android:width="64dp"
        android:viewportHeight="600"
        android:viewportWidth="600" >
        <group
            android:name="rotationGroup"
            android:pivotX="300.0"
            android:pivotY="300.0"
            android:rotation="45.0" >
            <path
                android:name="v"
                android:fillColor="#000000"
                android:pathData="M300,70 l 0,-70 70,70 0,0 -70,70z" />
        </group>
    </vector>

**(2).** `android.graphics.drawable.AnimatedVectorDrawable` 对应的 XML 文件，它以 `<animated-vector>` 为根。
这里定义需动画的 path 或 group 的 `<target>`，`<target>`的 animation 属性指定为一般的 ObjectAnimator 或 
AnimatorSet 对应的 XML。

    <animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
        android:drawable="@drawable/vectordrawable" >
        <target
            android:name="rotationGroup"
            android:animation="@anim/rotation" />
        <target
            android:name="v"
            android:animation="@anim/path_morph" />
    </animated-vector>

**(3).** `android.graphics.drawable.Animator` 对应的 XML 文件，它以 `<set>`，`<objectAnimator>` 等为根，
对应 AnimatorSet 和 ObjectAnimator。

    <objectAnimator
        android:duration="6000"
        android:propertyName="rotation"
        android:valueFrom="0"
        android:valueTo="360" />

或

    <set xmlns:android="http://schemas.android.com/apk/res/android">
        <objectAnimator
            android:duration="3000"
            android:propertyName="pathData"
            android:valueFrom="M300,70 l 0,-70 70,70 0,0   -70,70z"
            android:valueTo="M300,70 l 0,-70 70,0  0,140 -70,0 z"
            android:valueType="pathType"/>
    </set>

### 1. VectorDrawable

VectorDrawable 一般是以 `<vector>` 为根标签定义的 XML 文件，`<vector>`、`<group>`、`<clip-path>`、`<path>` 
元素都有各自可以播放动画的属性，查阅 [SDK/docs/reference/android/graphics/drawable/VectorDrawable.html](https://developer.android.com/reference/android/graphics/drawable/VectorDrawable.html) 
你会找到每种元素到底有那些属性，以便针对这些属性播放特定的动画。比如 trimPathStart 和 trimPathEnd 能够让已画的 path 
以百分比的形式渐变出现。


| &lt;vector&gt;           |        定义这个矢量图 |
| :------------------- | :------------------ |
|    android:name            | 矢量图的名字 | 
|    android:width           | 矢量图的内部(intrinsic)宽度,支持所有Android系统支持的尺寸，通常使用dp | 
|    android:height          | 矢量图的内部(intrinsic)高度 | 
|    android:viewportWidth   | 矢量图视图的宽度，视图就是矢量图path路径数据所绘制的虚拟画布 | 
|    android:viewportHeight  | 矢量图视图的高度 | 
|    android:tint            | 矢量图的tint颜色。默认是没有tint颜色的 | 
|    android:tintMode        | 矢量图tint颜色的Porter-Duff混合模式，默认值为src_in。(src_in,src_over,src_atop,add,screen,multiply) | 
|    android:autoMirrored    | 设置当系统为RTL(right-to-left)布局的时候，是否自动镜像该图片。比如阿拉伯语。 | 
|    android:alpha           | 该图片的透明度属性 | 

| &lt;group&gt;                    | 设置路径做动画的关键属性的 | 
| :------------------- | :------------------ |
|    android:name            | 定义group的名字 | 
|    android:rotation        | 定义该group的路径旋转多少度 | 
|    android:pivotX          | 定义缩放和旋转该group时候的X参考点。该值相对于vector的viewport值来指定的。 | 
|    android:pivotY          | 定义缩放和旋转该 group 时候的Y参考点。该值相对于vector的viewport值来指定的。 | 
|    android:scaleX          | 定义X轴的缩放倍数 | 
|    android:scaleY          | 定义Y轴的缩放倍数 | 
|    android:translateX      | 定义移动X轴的位移。相对于vector的viewport值来指定的。 | 
|    android:translateY      | 定义移动Y轴的位移。相对于vector的viewport值来指定的。 | 

| &lt;path&gt;     | 路径 |
| :------------------- | :------------------ |
|    android:name            | 定义该path的名字，这样在其他地方可以通过名字来引用这个路径 | 
|    android:pathData        | 和SVG中d元素一样的路径信息。 | 
|    android:fillColor       | 定义填充路径的颜色，如果没有定义则不填充路径 | 
|    android:strokeColor     | 定义如何绘制路径边框，如果没有定义则不显示边框 | 
|    android:strokeWidth     | 定义路径边框的粗细尺寸 | 
|    android:strokeAlpha     | 定义路径边框的透明度 | 
|    android:fillAlpha       | 定义填充路径颜色的透明度 | 
|    android:trimPathStart   | 从路径起始位置截断路径的比率，取值范围从0到1；注意从一半到起始动画为from-0.5-to-0 | 
|    android:trimPathEnd     | 从路径结束位置截断路径的比率，取值范围从0到1；注意从一半到结束动画为from-0.5-to-1.0 | 
|    android:trimPathOffset  | 设置路径截取的范围，取值范围从0到1 | 
|    android:strokeLineCap   | 设置路径线帽的形状，取值为 butt, round, square. | 
|    android:strokeLineJoin  | 设置路径交界处的连接方式，取值为 miter,round,bevel. | 
|    android:strokeMiterLimit  | 设置斜角的上限

| &lt;clip-path&gt;                 | 定义当前绘制的剪切路径。注意，clip-path 只对当前的 group 和子 group 有效 | 
| :------------------- | :------------------ |
|    android:name            | 定义clip-path的名字 | 
|    android:pathData        | android:pathData的取值一样。 | 

根元素 `<vector>` 上有两个宽高设置，其中 viewport 是指矢量图里面的画布大小，而 `android:width和android:height` 是指该矢量图所对应的 VectorDrawable 的大小。

> 关于 tintMode:  
在5.0以后我们就可以为 bitmap 或者是 9-patch 定义一个透明的遮罩。BitmapDrawable 和 NinePatchDrawable 使用 setTint() 方法。
而在 xml 文件中使用 android:tint 和 android:tintMode 这两个属性。

> 注意点：使用 android:tint 指定颜色时一定要带透明度。#50ff00ff 也就是说是8位的色值而不是6位的。

属性说明:  
* android:tint： 设置的是颜色 
* android:tintMode：设置的是类型 (src_in,src_over,src_atop,add,screen,multiply)

类型说明：  
* src_in 只显示设置的遮罩颜色。 相当于遮罩在里面。 
* src_over 遮罩颜色和图片都显示。相当于遮罩在图片上方。(特别是色值带透明度的) 
* src_atop 遮罩在图片上方 
* multiply 混合色遮罩 

screen  
add 混合遮罩，drawable 颜色和透明度。

  
例:

    <vector xmlns:android="http://schemas.android.com/apk/res/android"
         android:height="64dp"
         android:width="64dp"
         android:viewportHeight="600"
         android:viewportWidth="600" >
         <group
             android:name="rotationGroup"
             android:pivotX="300.0"
             android:pivotY="300.0"
             android:rotation="45.0" >
             <path
                 android:name="v"
                 android:fillColor="#000000"
                 android:pathData="M300,70 l 0,-70 70,70 0,0 -70,70z" />
         </group>
    </vector>


##　2. AnimatedVectorDrawable

我们还可以用 AnimatedVectorDrawable　给矢量图添加动画。AnimatedVectorDawable　可以实现一些很特别的效果，对　VectorDrawable　里的　pathData　做动画，
可以从一个图形渐变到另一个图形，比如　Material Design　里的　toolbar icon；对　trimPathStart、trimPathEnd　做动画，可以得到图形的绘制轨迹。  

![导航图标](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/md_toolbar_icon.gif?token=AQ83MjZ3ICbs7datlN4_w46xsoJkFJj-ks5axebywA%3D%3D)  

AnimatedVectorDrawable　通过　ObjectAnimator　或　AnimatorSet　对　VectorDrawable　的某个属性作一个矢量资源的动画。

你通常在三个　XML　文件中定义矢量资源的动画载体：

* `<vector>` 元素的矢量资源，在 res/drawable/(文件夹)
* `<animated-vector>` 元素的矢量资源动画，在 res/drawable/(文件夹)
* `<objectAnimator>` 元素的一个或多个对象动画器，在 res/animator/(文件夹)

矢量资源动画能创建  `<group>` 和 `<path>` 元素属性的动画。`<group>` 元素定义了一组路径或子组，并且 `<path>` 元素定义了要被绘制的路径。

当你想要创建动画时去定义矢量资源，使用 `android:name` 属性分配一个唯一的名字给组和路径，这样你可以从你的动画定义中查询到它们。

从 API-25 开始，AnimatedVectorDrawable 运行在 RenderThread (相反地，早期 API 是运行在 UI 线程)。这也就是说 AnimatedVectorDrawable 在UI线程繁忙时也能保持流畅运行。

如果UI线程没有反应，RenderThread 会持续动画计算，直到UI线程有能力推进下一帧。因此，没有办法准确地同步 RenderThread-enabled 的 
AnimatedVectorDrawable 和 UI 线程中的 Animations。此外，`Animatable2.AnimationCallback.onAnimationEnd(drawable)` 
肯定会在 RenderThread 渲染完 AnimatedVectorDrawable 最后一帧时被调用。

AnimatedVectorDrawable 可以被定义在三个XML文件或一个XML中。

**定义在一个文件中:**

[查看代码](https://github.com/OCNYang/Android-Animation-Set/blob/master/vector-animation/src/main/res/drawable/drawable_animated_vector.xml#L1-L45)

**定义在 3 个文件中**  

`animated-vector 代码(drawable 是对应的 vector 矢量图资源)`  

    <?xml version="1.0" encoding="utf-8"?>
    <animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
                     android:drawable="@drawable/vectordrawable">
        <target
            android:name="rotationGroup"
            android:animation="@animator/rotation"/>
        <target
            android:name="v"
            android:animation="@animator/path_morph"/>
    </animated-vector>
    
`rotation 代码`

    <?xml version="1.0" encoding="utf-8"?>
    <set xmlns:android="http://schemas.android.com/apk/res/android">
        <objectAnimator
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:duration="6000"
            android:propertyName="rotation"
            android:valueFrom="0"
            android:valueTo="360"/>
    </set>
    
`path_morph 代码`

    <?xml version="1.0" encoding="utf-8"?>
    <set xmlns:android="http://schemas.android.com/apk/res/android">
        <objectAnimator
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:duration="3000"
            android:propertyName="pathData"
            android:valueFrom="M300,70 l 0,-70 70,70 0,0   -70,70z"
            android:valueTo="M300,70 l 0,-70 70,0  0,140 -70,0 z"
            android:valueType="pathType"/>
    </set>



AnimatedVectorDrawable 主要方法有：

    void draw(Canvas canvas)
    //Draws the AnimatedVectorDrawable into the given canvas. 
    
    int getOpacity()
    //Return the opacity/transparency of this Drawable. 
    
    int getAlpha()
    //For API 25 and later, AnimatedVectorDrawable runs on RenderThread. 
    
    void setAlpha(int alpha)
    //Specify an alpha value for the drawable. 
    
    boolean setVisible(boolean visible, boolean restart)
    //Set whether this Drawable is visible.
    
    int getIntrinsicHeight()
    //Returns the drawable's intrinsic height.
    
    int getIntrinsicWidth()
    //Returns the drawable's intrinsic width. 
    
    Drawable mutate()
    //Make this drawable mutable. 
    
    boolean isRunning()
    //Indicates whether the animation is running.
    
    void reset()
    //Resets the AnimatedVectorDrawable to the start state as specified in the animators.
    
    void start()
    //Starts the drawable's animation.
    
    void stop()
    //Stops the drawable's animation.
    
    void clearAnimationCallbacks()
    //Removes all existing animation callbacks. 
    
    boolean unregisterAnimationCallback(Animatable2.AnimationCallback callback)
    //Removes the specified animation callback. 

代码中使用：

    mImageView.setImageResource(R.drawable.animated_vector);
    Drawable drawable = mImageView.getDrawable();
    if (drawable instanceof Animatable) {
        ((Animatable) drawable).start();
    }

**附录**  
本文摘录自：[原文地址](https://blog.csdn.net/nemo__/article/details/71099839)