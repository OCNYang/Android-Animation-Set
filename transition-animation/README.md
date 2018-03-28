# Ⅵ. Transition Animation / 转场动画 & 共享元素

> 本节暂时未提供 Demo,如果想查看相关代码可以先参考项目 [Material-Animations](https://github.com/lgvalle/Material-Animations) .  
Material-Animations 项目的作者虽早已停止维护这个项目，但相关代码还能够参考。我也会尽快补上最新的 Demo。

## 1. 什么是 Transition?

安卓 5.0 中 Activity 和 Fragment 变换是建立在名叫 Transitions 的安卓新特性之上的。这个诞生于 4.4 的 transition 框架
为在不同的 UI 状态之间产生动画效果提供了非常方便的 API。  

该框架主要基于两个概念：场景（scenes）和变换（transitions）。  
* 场景（scenes）定义了当前的 UI 状态
* 变换（transitions）则定义了在不同场景之间动画变化的过程。

虽然 transition 翻译为变换似乎很确切，但是总觉得还是没有直接使用 transition 直观，为了更好的理解下面个别地方直接用 transition 
代表变换。

当一个场景改变的时候，transition 主要负责：

（1）捕捉在开始场景和结束场景中每个 View 的状态。
（2）根据视图一个场景移动到另一个场景的差异创建一个 Animator。

在 Android 5.0 中 Transition 可以被用来实现 Activity 或者 Fragment 切换时的异常复杂的动画效果。虽然在以前的版本中，
已经可以使用 Activity 的 overridePendingTransition() 和 FragmentTransaction 的 setCustomAnimation() 来实现 
Activity 或者 Fragment 的动画切换，但是他们仅仅局限与将整个视图一起动画变换。新的 Lollipop API更进了一步，让单独的 view 
也可以在进入或者退出其布局容器中时发生动画效果，甚至还可以在不同的 activity/Fragment 中共享一个 view。

这里先看一张官方的效果图：  

![共享元素转场动画](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_animation.gif?token=AQ83MnpsS0QFIfOphNBZ--ahkoxDvan1ks5axHvFwA%3D%3D)  

![共享元素转场动画 过程细化](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/SceneTransition.png?token=AQ83Mn28u76WmQPF2MNnecm8kmcNCzVbks5axHv_wA%3D%3D)  

对转场动画更加详细的介绍可以查看[官方介绍](https://developer.android.com/training/transitions/index.html)。

**Transitions Framework 主要作用或者应用场景**  
* 可以在activity之间跳转的时候添加动画
* 动画共享元素之间的转换活动
* activity中布局元素的过渡动画。

下面分别以这些场景一一介绍。

## 2. Transitions 在 Activity 切换过程中的效果

首先，我们回顾下，我们之前是如何设置 Activity 切换过程中的动画，毫无疑问，通过 overridePendingTransition，
更多具体实现请看 [**WIKI**](https://github.com/OCNYang/Android-Animation-Set/wiki/%E5%AE%9E%E7%8E%B0-Activity-%E7%9A%84%E5%88%87%E6%8D%A2%E5%8A%A8%E7%94%BB)
或到[实现 Activity 的切换动画](https://github.com/OCNYang/Android-Animation-Set/blob/master/transition-animation/ActivitySkipAnimation.md)  

这里我们直接介绍通过 Transitions 实现的切换效果。

![transition_A_to_B](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_A_to_B.png?token=AQ83Mhs5y3VQZ-RwFXpL_62sYpQiKMOOks5axHwcwA%3D%3D)

当从 Activity A 切换到  Activity B 的时候，Activity 布局的内容会按照预先定义好的动画来执行过渡动画。
在 android.transition 包中，已经有三种现成的动画可以用: Explode，Slide 和 Fade。所有这些过渡都会跟踪 
Activity 布局中可见的目标 Views，驱动这些 Views 按照过渡的规则产生响应的动画效果。

| Explode | Slide |　Fade　|
| :-----: | :----: | :----: |
| ![transition_explode](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_explode.gif?token=AQ83Ml7jMuekilxc7547E0ytJLeYkm8wks5axHxcwA%3D%3D) | ![transition_slide](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_slide.gif?token=AQ83Mr2PgHwzU2Rkmqzeo9tvvrIRz36gks5axHx_wA%3D%3D) | ![transition_fade](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_fade.gif?token=AQ83Mn7qK6FrN9Yshyb0L0LHdWw04fMnks5axHyNwA%3D%3D) |

你可以在 xml 中创建这些过渡效果，也可以通过代码来创建。对于 Fade 过渡效果来说，它看起来是这样子的：

### 在 style 中设置

在 style 中添加 `android:windowContentTransitions` 属性启用窗口内容转换( Material-theme 应用默认为 true )，
指定该 Activity 的 Transition

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- enable window content transitions -->
        <item name="android:windowContentTransitions">true</item>
        
        <!-- specify enter and exit transitions -->
        <!-- options are: explode, slide, fade -->
        <item name="android:windowEnterTransition">@transition/activity_fade</item>
        <item name="android:windowExitTransition">@transition/activity_slide</item>
    </style>

### xml 中创建

过渡效果定义在 xml 中，目录是 res/transition

`res/transition/activity_fade.xml`

    <?xml version="1.0" encoding="utf-8"?>
    <fade xmlns:android="http://schemas.android.com/apk/res/"
        android:duration="1000"/>
        
`res/transition/activity_slide.xml`

    <?xml version="1.0" encoding="utf-8"?>
    <slide xmlns:android="http://schemas.android.com/apk/res/"
        android:duration="1000"/>

要使用这些 xml 中定义的过渡动画，你需要使用 TransitionInflater 来实例化它们。

`MainActivity.java`

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Slide slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }

`TransitionActivity.java`

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Fade fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }

### 在代码中创建

`MainActivity.java`

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

`TransitionActivity.java`

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

不管哪种创建方法都会产生如下的效果:

![transition_fade](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_fade.gif?token=AQ83Mn7qK6FrN9Yshyb0L0LHdWw04fMnks5axHyNwA%3D%3D)

**那么这里面一步一步的到底发生了什么:**  

1. Activity A 启动 Activity B
2. Transition Framework 发现 A 中定义了Exit Transition (slide) 然后就会对它的所有可见的View使用这个过渡动画.
3. Transition Framework 发现 B 中定义了Enter Transition (fade) 然后机会对它所有可见的Views使用这个过渡动画.
4. On Back Pressed(按返回键) Transition Framework 会执行把 Enter and Exit 过渡动画反过来执行(但是如果定义了 
returnTransition 和 reenterTransition，那么就会执行这些定义的动画)

> 更加详细的分析请到 [**深入理解 Content Transition**](https://github.com/OCNYang/Android-Animation-Set/blob/master/transition-animation/ContentTransition.md) 
或 [**Wiki**](https://github.com/OCNYang/Android-Animation-Set/wiki/%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3-Content-Transition) 查看。

> 译注:
> * Exit Transition: 可以理解为 activity 进入后台的过渡动画
> * Enter Transition: 可以理解为创建 activity 并显示时的过渡动画
> * Return Transition:可以理解为销毁 activity 时的过渡动画
> * Reenter Transition: 可以理解为 activity 从后台进入前台时的过渡动画
> * 要使这些过渡动画生效，我们需要调用 `startActivity(intent，bundle)` 方法来启动 Activity。bundle 需要通过 
`ActivityOptionsCompat.makeSceneTransitionAnimation().toBundle()` 的方式来生成

### ReturnTransition & ReenterTransition

Return and Reenter Transitions 是与进入和退出动画相对应的.

* EnterTransition <--> ReturnTransition
* ExitTransition <--> ReenterTransition

如果 Return or Reenter 没有创建, Android 会把 Enter and Exit Transitions 反过来执行. 但是如果你创建了 
Return or Reenter，那 Android 就会执行你创建的动画，并且这些动画可以不同.

![b back a](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_B_to_A.png?token=AQ83MmuLIaE4S4KUdEuCNQmgFIaBjzX3ks5axHw1wA%3D%3D)

我们可以修改下前面 Fade 的例子，给 TransitionActivity 创建 ReturnTransition。这里我们就拿 Slide 过渡效果来举例子。
这样，如果我们从 B 返回到 A 的时候，B 就会执行一个 Slide 的过渡效果。

`TransitionActivity.java`

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
        
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);        
    }

可以看到，如果没有创建 Return Transition，退出的时候会执行 Enter Transtion 相反的动画。如果创建了 Return Transition，
那么就会执行这个创建的动画效果。

| 没有Return Transition | 有Return Transition |
| :-------------------: | :----------------: |
| Enter: Fade In | Enter: Fade In | 
| Exit: Fade Out | Exit: Slide out | 
| ![transition_fade](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_fade.gif?token=AQ83Mn7qK6FrN9Yshyb0L0LHdWw04fMnks5axHyNwA%3D%3D) | ![transition_fade2](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_fade2.gif?token=AQ83Mp57J5U15fqDFnimNrMgacq6hI_Tks5axHzDwA%3D%3D) | 


## 3. Activity 之间共享元素 (Share Elements)

这里的思想就是通过动画的形式将两个不同布局中的两个不同的View联系起来。  
然后 Transition framework 就会在用户从一个View切换到另一个View的时候给用户展现一些必要的动画。  
但你要记住:发生动画的View并不是从一个布局中移动到另一个布局。他们是两个独立的View。

![A Start B with shared](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/shared_element.png?token=AQ83Mvh-_twlcYFredjHZjl6mYeTMskZks5axIYXwA%3D%3D)  

### 3_a 设置 Window Content Transition 属性

你需要在 app 的 `styles.xml` 中进行设置.[ps]我没有设置也没问题

`values/styles.xml`

    <style name="MaterialAnimations" parent="@style/Theme.AppCompat.Light.NoActionBar">
        ...
        <item name="android:windowContentTransitions">true</item
        ...
    </style>

如果你愿意的话，你也可以给整个app设置一个默认的转场动画和共享元素动画。

    <style name="MaterialAnimations" parent="@style/Theme.AppCompat.Light.NoActionBar">
        ...
        <!-- specify enter and exit transitions -->
        <item name="android:windowEnterTransition">@transition/explode</item>
        <item name="android:windowExitTransition">@transition/explode</item>
    
        <!-- specify shared element transitions -->
        <item name="android:windowSharedElementEnterTransition">@transition/changebounds</item>
        <item name="android:windowSharedElementExitTransition">@transition/changebounds</item>
        ...
    </style>

### 3_b 设置相同的 transition name

为了使共享元素动画生效，你需要给共享元素的两个View设置相同的android:transitionName属性值。不过他们的id和其他属性可以不同。

`layout/activity_a.xml`

    <ImageView
        android:id="@+id/small_blue_icon"
        style="@style/MaterialAnimations.Icon.Small"
        android:src="@drawable/circle"
        android:transitionName="@string/blue_name" />
        
`layout/activity_b.xml`

    <ImageView
        android:id="@+id/big_blue_icon"
        style="@style/MaterialAnimations.Icon.Big"
        android:src="@drawable/circle"
        android:transitionName="@string/blue_name" />
    
### 3_c 用共享元素来启动 activity

使用 `ActivityOptions.makeSceneTransitionAnimation()` 方法指定要共享元素的 View 和 
`android:transitionName` 属性的值

`MainActivity.java`

    blueIconImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, SharedElementActivity.class);
    
            View sharedView = blueIconImageView;
            String transitionName = getString(R.string.blue_name);
    
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
            startActivity(i, transitionActivityOptions.toBundle());
        }
    });

这样就可以有下面漂亮的过渡效果了:

![a to b with shared element](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/shared_element_anim.gif?token=AQ83MlHk7M9_AVjW54jAMd9pWMrDXmzQks5axI2wwA%3D%3D)  

可以看到, Transition framework 创建并执行了一个动画。动画的视觉效果就是一个View从一个activity移动到另一个activity中
并伴随着形状的变化。

## 4. 在 fragment 之间实现 Shared elements 过渡动画

这个activity中的做法几乎是一样的。

步骤 a) 和步骤 b)完全一样， 只有步骤 c)有点区别

### 4_c 启动带有共享元素的 fragment

在你使用 FragmentTransaction 启动 fragment 的时候，你需要同时带上共享元素过渡动画的先关信息。

    FragmentB fragmentB = FragmentB.newInstance(sample);
    
    // Defines enter transition for all fragment views
    Slide slideTransition = new Slide(Gravity.RIGHT);
    slideTransition.setDuration(1000);
    sharedElementFragment2.setEnterTransition(slideTransition);
    
    // Defines enter transition only for shared element
    ChangeBounds changeBoundsTransition = TransitionInflater.from(this).inflateTransition(R.transition.change_bounds);
    fragmentB.setSharedElementEnterTransition(changeBoundsTransition);
    
    getFragmentManager().beginTransaction()
            .replace(R.id.content, fragmentB)
            .addSharedElement(blueView, getString(R.string.blue_name))
            .commit();
        
最终的效果就是这样的:

![shared_element_no_overlap](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/shared_element_no_overlap.gif?token=AQ83MggDmMsVVZXZIzq8QR4MrDkNioWDks5axI3LwA%3D%3D)

## 5. 允许过渡效果之间的重叠

You can define if enter and exit transitions can overlap each other.  
你可以设置一个 activity 的退出效果和另一个 activity 的进入效果产生重叠部分。

[Android 文档](https://developer.android.com/reference/android/app/Fragment.html?hl=ko#getAllowEnterTransitionOverlap()) 这么说的:

> 当设置为 true, enter transition 会立马执行>
> 当设置为 false, enter transition 会等到退出 exit transition 结束后再执行.

这对Fragments和Activities的共享元素过渡效果都是有用的。

    FragmentB fragmentB = FragmentB.newInstance(sample);
    
    // Defines enter transition for all fragment views
    Slide slideTransition = new Slide(Gravity.RIGHT);
    slideTransition.setDuration(1000);
    sharedElementFragment2.setEnterTransition(slideTransition);
    
    // Defines enter transition only for shared element
    ChangeBounds changeBoundsTransition = TransitionInflater.from(this).inflateTransition(R.transition.change_bounds);
    fragmentB.setSharedElementEnterTransition(changeBoundsTransition);
    
    // Prevent transitions for overlapping
    fragmentB.setAllowEnterTransitionOverlap(overlap);
    fragmentB.setAllowReturnTransitionOverlap(overlap);
    
    getFragmentManager().beginTransaction()
            .replace(R.id.content, fragmentB)
            .addSharedElement(blueView, getString(R.string.blue_name))
            .commit();
        
可以看到，效果还是挺明显的:

| Overlap True | Overlap False |
| :-----------: | :----------: |
| Fragment_2 出现在Fragment_1的上面 | Fragment_2 等到Fragment_1消失后才出现 | 
| ![shared_element_overlap](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/shared_element_overlap.gif?token=AQ83Mp2_73_X0IHlShlWuyTLD6oWqswJks5axI3qwA%3D%3D) | ![shared_element_no_overlap](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/shared_element_no_overlap.gif?token=AQ83MggDmMsVVZXZIzq8QR4MrDkNioWDks5axI3LwA%3D%3D) | 

## 6. 布局元素动画

### 6_1 Scenes (场景)  

也可以用来驱动一个activity中的布局发生变化时，让其中的View产生过渡动画。

过渡效果发生在场景之间。场景就是一个定义了静态UI的普通布局。Transition Framework可以在两个场景之间添加切换过渡动画。

    scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene1, this);
    scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene2, this);
    scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene3, this);
    scene4 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene4, this);
    
    (...)
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                TransitionManager.go(scene1, new ChangeBounds());
                break;
            case R.id.button2:
                TransitionManager.go(scene2, TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds));
                break;
            case R.id.button3:
                TransitionManager.go(scene3, TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential));
                break;
            case R.id.button4:
                TransitionManager.go(scene4, TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators));
                break;  
        }
    }

上面的代码会在同一个activity中的4个场景切换时产生过渡动画。每一次切花的动画都是不一样的。

Transition Framework会考虑当前场景内所有可见的View并计算需出需要的动画来安排两个场景之间的view的位置。

![scenes_anim](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/scenes_anim.gif?token=AQ83Mtdyms3whHdwFywgrs2DIFVbloupks5axI4CwA%3D%3D)

### 6_2 布局的改变

也可以用来给布局属性的变化加上过渡效果。你只需要做你想要的改变然后其他的就交给Transition Framework，它会为你添加动画。

**a) 开启演示过渡效果**

下面这行代码告诉framework我们将要对UI进行一些改变，请你给我加上过渡效果。

    TransitionManager.beginDelayedTransition(sceneRoot);

**b) 改变布局参数**  

    ViewGroup.LayoutParams params = greenIconView.getLayoutParams();
    params.width = 200;
    greenIconView.setLayoutParams(params);

改变View的宽度属性让他变小，这会触发layoutMeasure。这个点上，Transition framework会记录开始和结束时的相关值，
并给这个变化加上过渡效果。

![view layout animation](https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/view_layout_anim.gif?token=AQ83Mv91EWsQVDHAy8cdj0x3WPdc7ldOks5axI4bwA%3D%3D)



**附录**  
本文转载自  
The address of the article in English：  
[Material-Animations](https://github.com/lgvalle/Material-Animations) 





