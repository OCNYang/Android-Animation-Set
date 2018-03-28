# Ⅵ. Transition Animation / 转场动画 & 共享元素

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
更多具体实现请看 [**WIKI:实现 Activity 的切换动画**]()  

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




