# Ⅴ. 揭露动画

## 1. 概述

在 Android 5.0 及更高的版本中，加入了一种全新的视觉动画效果，就是揭露动画。揭露动画在系统中很常见，就是类似波纹的效果，
从某一个点向四周展开或者从四周向某一点聚合起来，本文实现的效果如下所示，可以用在 Activity 里面的 View 动画效果，
也可以使用在 Activity 跳转过渡动画中，如下图使用在 View 的显示隐藏的效果图：
 
![Reveal Effect]()  

## 2. 使用

使用揭露动画非常简单，Android Sdk 中已经帮我们提供了一个工具类 ViewAnimationUtils 来创建揭露动画。ViewAnimationUtils 
里面只有一个静态方法 `createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius)`，
返回一个 Animator 动画对象。

    public final class ViewAnimationUtils {
        private ViewAnimationUtils() {}
        /**
         * ......
         * @param view The View will be clipped to the animating circle.
         * @param centerX The x coordinate of the center of the animating circle, relative to
         *                <code>view</code>.
         * @param centerY The y coordinate of the center of the animating circle, relative to
         *                <code>view</code>.
         * @param startRadius The starting radius of the animating circle.
         * @param endRadius The ending radius of the animating circle.
         */
        public static Animator createCircularReveal(View view,
                int centerX,  int centerY, float startRadius, float endRadius) {
            return new RevealAnimator(view, centerX, centerY, startRadius, endRadius);
        }
    }

`ViewAnimationUtils.createCircularReveal()` 方法能够为裁剪区域添加动画以揭露或隐藏视图。我们主要使用 createCircularReveal 方法，
该方法有四个参数:  
* 第一个参数是执行揭露动画的 View 视图
* 第二个参数是相对于视图 View 的坐标系，动画圆的中心的x坐标
* 第三个参数是相对于视图 View 的坐标系，动画圆的中心的y坐标 
* 第四个参数是动画圆的起始半径，第五个参数动画圆的结束半径。

如下图所示： 

![]()  

揭露动画有两种效果，一种是显示一组UI元素，另一种是隐藏一组UI元素：   
* 以中心点为轴点，当开始半径小于结束半径时，从开始半径处向外扩大到结束半径处显示View 
* 以中心点为轴点，当开始半径大于结束半径时，从开始半径处向内缩小到结束半径处隐藏View

> 注意：揭露动画对象只能使用一次，不能被重新使用，也就是说每次使用揭露动画都要调用 ViewAnimationUtils.createCircularReveal() 
返回一个揭露动画对象使用，同时一旦开始了动画就不能暂停或重新开始。揭露动画是一种异步动画，可以自动运行在 UI 线程上。
当揭露动画结束后，如果设置了 Animator.AnimatorListener 监听器，那么监听器的 onAnimationEnd(Animator) 方法会被调用，
但可能会被延迟调用，这取决于线程的响应能力。