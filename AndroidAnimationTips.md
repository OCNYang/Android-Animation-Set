# Android 一共有多少种动画？准确告诉你！

![Android 动画](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/FnCS.gif)  

Android 动画在开发中是不可或缺的功能，或者说是界面灵动的添加剂。那你是否总结过 Android 中总共为开发者提供了多少种方式的动画呢？今天就为大家总结归纳一下。

![报告老师，我知道](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/JRYHqwhI.jpg)  

我话音未落，前排那位骚气不减当年、故事布满双眼的大胸弟就激动得高高举起了那只满是老茧的右手：“我知道，我知道！都有平移动画、缩放动画、旋转动画、渐变动画、还有......”  

看着这位兄弟回答时专注又冥思苦想的表情，我真不忍心伤害他那颗纯真的幼小心灵。于是我决定说一个谎：“这位同学，你说的太对了！你都知道的这么全面了还出来听课真是太辛苦了。这么热的天，不如回家吹吹空调、吃吃西瓜，把这次学习的机会让给更需要的人不挺好吗！”

看着那位兄弟一脸满足后远去的欢快背影，我终于可以放心的开始今天重点了。好了，上面内容纯属扯淡，转入正题。  

> 本文章一部分教程图片来自网络，在这里先对这些图片的作者表示感谢。

## 动画种类

Android 动画可以归纳为以下几种：  

* [视图动画（View 动画）](#1)
* [帧动画（Frame 动画、Drawable 动画）](#2)
* [属性动画](#3)
* [触摸反馈动画（Ripple Effect）](#4)
* [揭露动画（Reveal Effect）](#5)
* [转场动画 & 共享元素（Activity 切换动画）](#6)
* [视图状态动画（Animate View State Changes）](#7)
* [矢量图动画（Vector 动画）](#8)
* [约束布局实现的关键帧动画（ConstraintSet 动画）](#9)

上面动画分类是个人通过每种动画种类概念的独立性来划分的，目前能想到的只有这么多，如果有所遗漏大家可以指点出来以供我后续完善。  

可能有很多人迅速的反应出，缺少了目前使用也相对较多的 **[airbnb/lottie-android](https://github.com/airbnb/lottie-android)** 动画。不可置疑，Lottie 库目前在 Android 开发中尤其复杂动画效果上地位显著。但我们今天要是的 Android 原生上为我们提供的能使用的动画方式，Lottie 动画今天暂且搁置。同时对于 RecyclerView item 加载动画今天也暂且不提。我们且把这些动画归为其他，并不是把它们遗忘了。  

## 详尽教程

对于上面列举的动画种类，可能大家对部分较常用的动画早已熟练应用，比如 View 动画、属性动画等。而对部分较少使用（比如 揭露动画）、或者常常使用却从未意识到它也属于动画的一种（比如 触摸反馈动画）知道的并不是那么全面。“那么今天就一一为大家详细讲解每种动画的概念”，那是不可能滴~~，就这么点篇幅，这么可能把每种动画都细说下来。  

要这些动画一一梳理清晰，那将是一项浩大的工作，而我已经为大家总结成了一个**《详尽 Android 动画系列教程》**，大家可以到  
[https://github.com/OCNYang/Android-Animation-Set](https://github.com/OCNYang/Android-Animation-Set)  
进行查看，由于动画知识点涉及的太多而教程详细程度令人发指，大家可以收藏起来慢慢查看。另外总结的教程中每种动画都提供了动画示例，大家可以结合源码细细品味。（上面总结的系列教程，大多数都是借用前人总结的教程，选用的都是针对每种动画网上流传的最详细全面的教程，在梳理中对部分错误也进行了更正。）  

那今天的任务是什么呢？接下来主要通过粗略的介绍来讲解每种动画在开发中都适用在哪种场景。  

<h2 id="1">视图动画（View 动画）</h2>  

自从有了属性动画，View 动画的处境就非常凄凉，但有时我们需要的仅仅就是简易的动画效果，那我们使用 View 动画起来就十分便捷。  

View 动画的一个特点就是，他的动画仅仅是动的 View 的绘制地方，View 真正的位置并没有一起动画。

View 一般会用作直接作用页面中的 View 上，实现基本的动画效果：平移、旋转、缩放、透明度、或前几者的交集：  

![view_animation_base](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_base.png)  

除了这几种用法还有几种特殊的使用场景：  

* 1. **给 PopupWindow 设置显示隐藏的动画效果**：

大家可以对比一下默认动画和设置后的动画效果对比：  

<img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_popup1.gif"  width="400px" alt="默认效果"/> <img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_popup2.gif"  width="400px" alt="自定义效果"/>  

* 2. **给 Activity 设置页面跳转、退出动画效果**：

Activity 过场动画效果可以通过很多方式设置，而使用 View 动画实现的方式就是借助设置 
`overridePendingTransition(int enterAnim, int exitAnim)` 方法。跟在 startActivity() 或 finish() 后面，在页面转换时就显示上面方法设置的切换动画效果。  

效果对比：  

<img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_skip1.gif"  width="400px" alt="默认效果"/> <img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_skip2.gif"  width="400px" alt="自定义效果"/>  

* 3. **给 ViewGroup 设置子控件的进场动画效果**：  

就是通过给 ViewGroup 控件设置一条 ` android:layoutAnimation="@anim/anim_layout" ` 的属性。而 `anim_layout` 就是 ViewGroup 中子控件在第一次显示时的进场动画效果。  

效果如下：  

<img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_layoutanimation1.gif"  width="400px" alt="默认效果"/> <img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/view_animation_layoutanimation2.gif"  width="400px" alt="自定义效果"/>  

> LayoutAnimation 适用于所有的 ViewGroup ，自然也包含 ListView、RecyclerView 等控件。上面说过 LayoutAnimation 提供的是进场动画效果，所以只在 ViewGroup 第一次加载子 View 时显示一次，所以列表控件的 item 加载动画我们一般不使用它，我们会使用 列表 专门的 Item 加载动画， 比如 recyclerView.setItemAnimator() 等。

<h2 id="2">帧动画</h2>  

![动画书](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/frame_animation_0.gif)  

帧动画这个很好理解，其实就和看的动画片一样，每一帧代表一个画面动作，当快速逐帧显示时，速度到达人眼无法分辨每一帧时，就达到了动画的效果。

在使用中，先要准备好每一帧的素材图片：  
![帧动画素材图](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/frame_animation_1.png)  

然后播放出来就成了动画的效果：  

<img src="https://github.com/OCNYang/Android-Animation-Set/raw/master/README_Res/demo2.gif?imageView2/2/w/400"  width="400px" alt="帧动画播放效果"/>  

要说起帧动画的使用场景，在开发中使用的真是少之又少，一般会有两种：  

* 设备的开关机动画
*  “复杂” 的动画效果，看似不可能完成的动画

> 之所以说开机动画是帧动画。是因为一般开机动画是通过 system/media/bootanimation.zip 这个压缩包，bootanimation 里面主要包含一个 desc.txt 以及 N 个文件夹。而文件夹里面放着的就是开机动画的图片资源。decs.txt 的作用就是指导系统如何去执行开机动画。  
desc.txt 编写规范，例如开机动画需要用到 2 个文件夹，分别是 folder1 和 folder2，开机的时候，先把 folder1 里面的图片都播放一遍，然后再循环播放 folder2 里面的文件，直到进入系统。

而在开发中，开机动画我们一般涉及不到的。而常常使用到的是，当我们需要一些比较复杂的图片动画显示效果时，其他动画又实现不了，这时我们可以考虑帧动画，但要注意防止 OOM。  
其实真正用到帧动画时，更多的时候我们还不如使用 GIF 图片代替，现在几个主流图片加载框架都支持 GIF 图片，同时也能控制 GIF 的播放时机。

<h2 id="3">属性动画</h2>  

属性动画所提供的功能和 View 动画十分相似。但两者在实现原理上完全不同，而相对 View 动画来说，属性动画要强大的许多。这里我们先对两者做个对比：   

**View 动画/视图动画：**  

1. View 动画只能为 View 添加动画效果，且不能监听 View 相关属性的变化过程。
2. View 动画提供的动画能力较为单一，目前只支持帧动画、缩放动画、位移动画、旋转动画、透明度动画以及这些动画的集合动画。
3. View动画改变的是 View 的绘制效果，View 的真正位置和相关属性并不会改变，这也就造成了点击事件的触发区域是动画前的位置而不是动画后的位置的原因。

**属性动画**  

1. 属性动画作用对象不局限在 View 上，而是任何提供了 Getter 和 Setter 方法的对象的属性上。
2. 属性动画没有直接改变 View 状态的能力，而是通过动态改变 View 相关属性的方式来改变 View 的显示效果。
3. 属性动画使用更方便，可以用更简洁的代码实现相关的动画效果。
4. 属性动画上手难度较高，对于 propertyName 需要自己去挖掘，或者自己通过 Wrapper 的方式去自定义 propertyName。
5. 属性动画是 Android3.0 以上系统提供的能力，在 3.0 以下需导入 nineoldandroids 三方库解决兼容性问题。

那属性动画的使用场景有哪些呢？  

* 基本上视图动画作用在 View 上的动画效果，属性动画都可以实现；
* 在自定义 View 时，需要实现一些复杂的动画效果，或对 View 的一些特殊属性值进行动画变更时，视图动画无法实现时；
* 另外，属性动画你也可以用在非动画场景，比如，你在自定义 View 需要一个有一定规律（根据特定差值器变化）且可监听的数值变化器，这个时候借助属性动画是再合适不过了。


属性动画是功能更强大、实现方式更优雅的动画解决方案，在为自定义 View 设置动效上有着非常强大的表现能力，可以实现 View 动画实现不了的更加炫酷的动画效果。详细的属性动画介绍可以去查看 《Android 动画详尽教程》系列。

这里盗一张前段时间有位网友实现的灵动的红鲤鱼效果，具体的实现也用到了不少属性动画的原理。  

![灵动的红鲤鱼](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/fish_animation.gif)  

<h2 id="4">触摸反馈动画（Ripple Effect）</h2>  

所谓触摸反馈动画就是一种点击效果，作用在可点击的 View 上时，当有点击事件时会有涟漪般的反馈效果，使用在 按钮 上是再好不过了。  

Ripple 波纹效果有两种：  

	//有边界
	?android:attr/selectableItemBackground
	//无边界 （要求API21以上）
	?android:attr/selectableItemBackgroundBorderless 

效果分别为：

<img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/ripple_effect1.gif"  width="400px" alt="有边界效果"/>  
<img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/ripple_effect2.gif.gif"  width="400px" alt="无边界效果"/>  

使用也非常简单，只要将上面两种效果设置为控件的背景或者前景就好了，同时需要给控件设置点击事件、或把控件设置为可点击 `android:clickable="true"`  

<h2 id="5">揭露动画（Reveal Effect）</h2>  

揭露动画在系统中很常见，就是类似波纹的效果， 从某一个点向四周展开或者从四周向某一点聚合起来。  

* 可以用在 Activity 里面的 View 动画效果，用来揭露某个隐藏 View 的显示；* 
* 也可以使用在 Activity 跳转过渡动画中。

如下图使用时的一些效果：

![显示隐藏View揭露动画](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/reveal_animation.gif?imageView2/2/w/600)  

如果加上些 View 动画效果，结合后成这样：  

![与基础动画效果结合](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/reveal_animation2.gif?imageView2/2/w/400)  

同时它还可以和下面要说的转场动画结合成下面更酷炫的效果：  

![与转场动画结合](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/reveal_animation3.gif?imageView2/2/w/400)  

上面动画效果是：先使用转场动画的共享元素转场，然后再使用揭露动画显示 View。


<h2 id="6">转场动画 & 共享元素（Activity 切换动画）</h2>  

转场动画听名字就知道它的使用场景了，转场、转场自然是用在场景转换的时候：

* 转场效果我们一般用在 Activity 切换时的动画效果上；
* 共享元素一般我们使用在转换的前后两个页面有共同元素<sub>[注1]</sub>时；
* 同时也可以在 Activity 布局发生场景变化时，让其中的 View 产生相应的过渡动画。

> 共同元素：并非限制指作用的两个共享元素的状态、大小、显示位置完全相同。而是指两者在页面中要传递的内容相同，比如是从文章列表转到文章详情页面时的相同标题、主图等。如果共享元素的两者是不同的元素，一方面在显示时共享元素在将结束转场完成转换时会有显示的闪动，另一方面，如果两者表达的是不同的元素，用户也会感到很莫名。

话不多说，放上效果图：  

<img src="http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/t1.gif"  width="400px" alt="转场效果"/>  
<img src="https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/transition_animation.gif?token=AQ83MnpsS0QFIfOphNBZ--ahkoxDvan1ks5axHvFwA%3D%3D"  width="400px" alt="共享元素效果"/>

<h2 id="7">视图状态动画（Animate View State Changes）</h2>  

所谓视图状态动画，就是 View 在状态改变时执行的动画效果。和之前我们通过 selector 选择器给 Button 设置不同状态下的背景效果是一样一样的。  

当然，它的使用场景也是特定的：  

* 当 View 的状态改变时，希望此时显示的效果和静态效果有所区分，即显示效果也做出相应的改变，比如 Z 轴抬高，大小改变、或其他动画效果等。

放上一个按钮被点击后设置的视图状态动画：  

<img src="https://github.com/OCNYang/Android-Animation-Set/raw/master/README_Res/demo7.gif"  width="400px" alt="视图状态动画"/>

<h2 id="8">矢量图动画（Vector 动画）</h2>  

不知道大家现在在开发中，在图标显示上是不是还在切各种尺寸的 .png 图片适配。现在我可是一直在使用 svg 图标（在开发中是通过[转换成 Vector ](http://inloop.github.io/svg2android/)再使用，现在 AS 中导入，可以自动完成转换，转换不成功的再用上面网址转换），svg 图标的好处自不用说了。那矢量图动画有是怎么回事呢？  

VectorDrawable 一般是以 `<vector>` 为根标签定义的 XML 文件，`<vector>、<group>、<clip-path>、<path>` 元素都有各自可以播放动画的属性。具体怎么生成具有动画效果的图标，可以在[系列教程](https://github.com/OCNYang/Android-Animation-Set)中查看。  

我们可以在以下场景使用：  
* 具有动态变换效果的图标；
* 也可以用在需要特定动画效果的 VectorDrawable 图片上。


<img src="https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/md_toolbar_icon.gif?token=AQ83MjZ3ICbs7datlN4_w46xsoJkFJj-ks5axebywA%3D%3D"  width="400px" alt="动态图标"/>  
<img src="https://raw.githubusercontent.com/OCNYang/Android-Animation-Set/master/README_Res/vector_anim.gif?token=AQ83MkaFkHlYPvrYr6vNPoJBfJAJjuspks5axebVwA%3D%3D"  width="400px" alt="奔跑的图钉"/>


<h2 id="9">约束布局实现的关键帧动画（ConstraintSet 动画）</h2>  

这个动画就比较新了，甚至连官方都没有提供完整的文档。这是通过 ConstraintLayout 实现的一种关键帧动画。

> **关键帧动画**：(百度百科)任何动画要表现运动或变化，至少前后要给出两个不同的关键状态，而中间状态的变化和衔接电脑可以自动完成，在 Flash 中，表示关键状态的帧动画叫做关键帧动画  
所谓关键帧动画，就是给需要动画效果的属性，准备一组与时间相关的值，这些值都是在动画序列中比较关键的帧中提取出来的，而其他时间帧中的值，可以用这些关键值，采用特定的插值方法计算得到，从而达到比较流畅的动画效果。  

而 ConstraintSet 动画既然实现的是关键帧动画，那至少需要两个关键帧，而对于 ConstraintSet 来说每次需要的两个关键帧就是两种布局状态，而两种布局状态的转变过程 ConstraintSet 会生成一定的动画过渡。  

那使用场景根据约束动画的说明也比较明显了，就是同一个布局需要重新调整布局内部 View 位置时使用。  

![约束布局动画](http://obbu6r1mi.bkt.clouddn.com/github/androidanimationset/constraintset.gif)  

## 更详细的动画介绍  

这里介绍的只是对 Android 各个种类的动画进行了一个简单的介绍，如果你要更加详细更加全面的查看 Android 各个动画的系列教程，可以到本文章同系列文章教程进行查看：  

**[Android 动画详尽教程：https://github.com/OCNYang/Android-Animation-Set](https://github.com/OCNYang/Android-Animation-Set)**  


本篇教程终于完了，这时我又想起了文章开头那个大胸弟，我想他此刻肯定在家正洋洋得意的啃着大西瓜。不多说了，如此炎热的天气，我也要去啃个西瓜抚慰一下狂躁的心。

