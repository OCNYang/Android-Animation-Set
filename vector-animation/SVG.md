# SVG 讲解

## 一、概述

SVG 即可缩放矢量图形 (Scalable Vector Graphics) ，是使用 XML 来描述二维图形和绘图程序的语言，其定义遵循 W3C 标准。

关于SVG主要内容有： 
* [SVG W3C标准](https://www.w3.org/TR/SVG11/)
* [W3School SVG](http://www.w3school.com.cn/svg/) 
* [MDN SVG Attribute reference](https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute) 
* [SVG参考手册(SVG元素)](http://www.w3school.com.cn/svg/svg_reference.asp) 
* [SVG实例](http://www.w3school.com.cn/svg/svg_examples.asp) 
* [SVG属性](http://www.runoob.com/svg/svg-stroke.html) 
* [Adobe SVG查看器](http://www.adobe.com/devnet/svg.html)

在 2003 年 1 月，SVG 1.1 被确立为 W3C 标准。使用 SVG 的优势在于：

* SVG 可被非常多的工具读取和修改(比如记事本)
* SVG 与 JPEG 和 GIF 图像比起来，尺寸更小，且可压缩性更强
* SVG 是可伸缩的
* SVG 图像可在任何的分辨率下被高质量地打印
* SVG 可在图像质量不下降的情况下被放大
* SVG 图像中的文本是可选的，同时也是可搜索的(很适合制作地图)
* SVG 可以与 Java 技术一起运行
* SVG 是开放的标准
* SVG 文件是纯粹的 XML
  
下面的例子是一个简单的 SVG 文件的例子。SVG 文件必须使用 .svg 后缀来保存：

    <?xml version="1.0" standalone="no"?>
    <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" 
        "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
    
    <svg width="100%" height="100%" version="1.1"
        xmlns="http://www.w3.org/2000/svg">
        <circle cx="100" cy="50" r="40" stroke="black"
        stroke-width="2" fill="red"/>
    </svg>

## 二、HTML页面中的SVG

SVG 文件可通过以下标签嵌入 HTML 文档：`<embed>、<object> 或者 <iframe>`。

**使用 `<embed>` 标签**  

`<embed>` 标签被所有主流的浏览器支持，并允许使用脚本。当在 HTML 页面中嵌入 SVG 时使用 `<embed>` 标签是 `Adobe SVG Viewer` 推荐的方法！
然而，如果需要创建合法的 XHTML，就不能使用 `<embed>`。任何 HTML 规范中都没有 `<embed>` 标签。

    <embed src="rect.svg" width="300" height="100" 
        type="image/svg+xml"
        pluginspage="http://www.adobe.com/svg/viewer/install/"/>

**使用 `<object>` 标签**  

`<object>` 标签是 HTML 4 的标准标签，被所有较新的浏览器支持。它的缺点是不允许使用脚本。假如您安装了最新版本的 `Adobe SVG Viewer`，
那么当使用 `<object>` 标签时 SVG 文件无法工作（至少不能在 IE 中工作）！

    <object data="rect.svg" width="300" height="100" 
        type="image/svg+xml"
        codebase="http://www.adobe.com/svg/viewer/install/"/>

**使用 `<iframe>` 标签**  

`<iframe>` 标签可工作在大部分的浏览器中。

    <iframe src="rect.svg" width="300" height="100">
    </iframe>

## 三、SVG &lt;path&gt;

SVG 的 `<path>` 元素用于定义一些复杂的图形，其定义在 W3 SVG Path。

`<path>` 可用的命令如下：

|   名称   |    解释   |
| :------ | :------- |
| M = moveto(M X,Y) ：                                                    | 将画笔移动到指定的坐标位置 |
| L = lineto(L X,Y) ：                                                    | 画直线到指定的坐标位置 |
| H = horizontal lineto(H X)：                                            | 画水平线到指定的X坐标位置 |
| V = vertical lineto(V Y)：                                              | 画垂直线到指定的Y坐标位置 |
| C = curveto(C X1,Y1,X2,Y2,ENDX,ENDY)：                                  | 三次贝赛曲线 |
| S = smooth curveto(S X2,Y2,ENDX,ENDY)                                   | 同样三次贝塞尔曲线，更平滑 |
| Q = quadratic Belzier curve(Q X,Y,ENDX,ENDY)：                          | 二次贝赛曲线 |
| T = smooth quadratic Belzier curveto(T ENDX,ENDY)：                     | 同样二次贝塞尔曲线，更平滑 |
| A = elliptical Arc(A RX,RY,XROTATION,large-arc-flag,sweep-flag,X,Y)：   | 弧线 |
| Z = closepath()：                                                       | 关闭路径 |

> 以上所有命令均允许小写字母。大写的字母是基于原点的坐标系(偏移量)，即绝对位置；小写字母是基于当前点坐标系(偏移量)，即相对位置。

所有绘制工作都是在 `<path>` 元素中通过 d 属性来完成的。

    <?xml version="1.0" standalone="no"?>
    <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" 
        "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
    
    <svg width="100%" height="100%" version="1.1"
         xmlns="http://www.w3.org/2000/svg">
        <path d="M250 150 L150 350 L350 350 Z"/>
    </svg>

* (1) 坐标轴为以(0,0)为中心，X轴水平向右，Y轴水平向下
* (2) 命令与参数间的空格可以省略  

        M 250 150 M250 150
        
* (3) 参数间可以使用空格或逗号隔开  

        M 250,150 M250,150
        
* (4) 同一指令出现多次可以只用一个  

        M300,70 l 0,-70 70,70 0,0 -70,70z
        
* (5) 命令大写字母基于坐标系原点，小写字母基于当前位置

    > 假如虚拟画笔开始绘制的位置是(50,50)，如果使用的是 L100,100 指令，那么就是从 (50,50) 位置开始绘制直线，绘制到 (100,100) 的位置。
    如果是 l100,100 指令，那么直线将从 (50,50) 位置开始绘制，绘制到 (50+100,50+100) 的位置。

* (6) 闭合路径是指从最后一个绘制点连线到开始点，这个命令是Z(或z，这里大小写没有区别) 
 

### 1. SVG Stroke属性

SVG 提供了一个范围广泛 stroke 属性。在本章中，我们将看看下面： 

* stroke 定义一条线，文本或元素轮廓颜色 
* stroke-width 定义了一条线，文本或元素轮廓厚度 
* stroke-linecap 定义不同类型的开放路径的终结，对接(butt)、圆形(round)和方形(square)； 
* stroke-linejoin 控制两条线段之间的衔接，三个值：尖角(miter)、圆角(round)和斜角(bevel)； 
* stroke-miterlimit miter-length和 stroke-width 之间的比率做了限制，它的比值范围应大于或等于1。当比值不在这个范围的时候，
 stroke 就会被转换成斜角（bevel）。当strokeLineJoin设置为 “miter” 的时候，绘制两条线段以锐角相交的时候，所得的斜面可能相当长。
 当斜面太长，就会变得不协调。strokeMiterLimit 属性为斜面的长度设置一个上限。这个属性表示斜面长度和线条长度的比值。默认是10，
 意味着一个斜面的长度不应该超过线条宽度的10倍。如果斜面达到这个长度，它就变成斜角了。当 strokeLineJoin 为 “round” 或 “bevel” 的时候，这个属性无效。 
* stroke-dasharray 创建虚线 
* stroke-dashoffset 设置需要图案延迟绘制的距离 
* stroke-opacity 线条，文本或元素轮廓透明度；

  
可能的取值如下：

    stroke="blue"
    stroke="#347559
    
    stroke-width="3px"
    stroke-width="1em"
    stroke-width="2%"
    
    stroke-dashoffset="3px"
    stroke-dashoffset="1em"
    stroke-dashoffset="2%"


以下为 stroke 属性的范例 stroke.avg ，可以较好地理解各项含义：

    <?xml version="1.0" standalone="no"?>
    <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" 
    "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
    
    <svg xmlns="http://www.w3.org/2000/svg" version="1.1">
      <g fill="none">
        <path stroke="red" stroke-width="10" stroke-linecap="butt" d="M50,50 L50,150" />
        <path stroke="green" stroke-width="20" stroke-linecap="round" d="M100,50 L100,150" />
        <path stroke="blue" stroke-width="30" stroke-linecap="square" d="M150,50 L150,150" />
    
        <path stroke="black" stroke-width="4" d="M50,50 L50,150" />
        <path stroke="black" stroke-width="4" d="M100,50 L100,150" />
        <path stroke="black" stroke-width="4" d="M150,50 L150,150" />
    
        <path stroke="white" d="M160,50 L160,150" />
      </g>
    
      <g fill="none" stroke="black" stroke-width="4">
        <path stroke-dasharray="5,5" d="M50 200 l215 0" />
        <path stroke-dasharray="10,10" d="M50 220 l215 0" />
        <path stroke-dasharray="20,10,5,5,5,10" stroke-dashoffset="100" d="M50 240 l215 0" />
      </g>
    
      <g fill="none" stroke="black" stroke-width="50">
        <path stroke-linejoin="miter" stroke-miterlimit="30" d="M100 400 L200 300 L300 400" />
        <path stroke-linejoin="round" stroke-miterlimit="20" d="M100 500 L200 400 L300 500" />
        <path stroke-linejoin="bevel" stroke-miterlimit="10" d="M100 600 L200 500 L300 600" />
    
    
        <path stroke="white" stroke-width="5" d="M100 400 L200 300 L300 400" />
        <path stroke="white" stroke-width="5" d="M100 500 L200 400 L300 500" />
        <path stroke="white" stroke-width="5" d="M100 600 L200 500 L300 600" />
      </g>
    
    </svg>

### 2. `<path>` 标签 A 指令

`<path>` 标签 d 属性中，A指令可用于画一段椭圆孤。 
  
**A 指令格式**

elliptical Arc (A RX,RY,XROTATION,large-arc-flag,sweep-flag,X,Y)  

    RX,RY  所在椭圆水平方向上的半径，垂直方向上的半径；RX和RY设置为相同的值，将得到一个圆形的弧线  
    XROTATION  椭圆的X轴与水平方向顺时针方向夹角，可以想像成一个水平的椭圆绕中心点顺时针旋转XROTATION的角度。
    large-arc-flag，1表示大角度弧线，0为小角度弧线。
    sweep-flag，确定从起点至终点的方向，1为顺时针，0为逆时针
    X,Y为终点坐标  

A 指令图形详解，可以把 arc.svg 保存到本地，在浏览器中查看：

    <?xml version="1.0" standalone="no"?>
    <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" 
      "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
    <svg width="12cm" height="5.25cm" viewBox="0 0 1200 525" version="1.1"
         xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
      <title>Example arcs02 - arc options in paths</title>
      <desc>Pictures showing the result of setting
            large-arc-flag and sweep-flag to the four
            possible combinations of 0 and 1.</desc>
      <g font-family="Verdana" >
        <defs>
          <g id="baseEllipses" font-size="20" >
            <ellipse cx="125" cy="125" rx="100" ry="50"
                     fill="none" stroke="#888888" stroke-width="2" />
            <ellipse cx="225" cy="75" rx="100" ry="50"
                     fill="none" stroke="#888888" stroke-width="2" />
            <text x="35" y="70">Arc start</text>
            <text x="225" y="145">Arc end</text>
          </g>
        </defs>
        <rect x="1" y="1" width="1198" height="523"
              fill="none" stroke="blue" stroke-width="1" />
    
        <g font-size="30" >
          <g transform="translate(0,0)">
            <use xlink:href="#baseEllipses"/>
          </g>
          <g transform="translate(400,0)">
            <text x="50" y="210">large-arc-flag=0</text>
            <text x="50" y="250">sweep-flag=0</text>
            <use xlink:href="#baseEllipses"/>
            <path d="M 125,75 a100,50 0 0,0 100,50"
                  fill="none" stroke="red" stroke-width="6" />
          </g>
          <g transform="translate(800,0)">
            <text x="50" y="210">large-arc-flag=0</text>
            <text x="50" y="250">sweep-flag=1</text>
            <use xlink:href="#baseEllipses"/>
            <path d="M 125,75 a100,50 0 0,1 100,50"
                  fill="none" stroke="red" stroke-width="6" />
          </g>
          <g transform="translate(400,250)">
            <text x="50" y="210">large-arc-flag=1</text>
            <text x="50" y="250">sweep-flag=0</text>
            <use xlink:href="#baseEllipses"/>
            <path d="M 125,75 a100,50 0 1,0 100,50"
                  fill="none" stroke="red" stroke-width="6" />
          </g>
          <g transform="translate(800,250)">
            <text x="50" y="210">large-arc-flag=1</text>
            <text x="50" y="250">sweep-flag=1</text>
            <use xlink:href="#baseEllipses"/>
            <path d="M 125,75 a100,50 0 1,1 100,50"
                  fill="none" stroke="red" stroke-width="6" />
          </g>
        </g>
      </g>
    </svg>

### 3. `<path>` 标签画贝塞尔曲线

`<path>` 标签 d 属性中，绘制贝塞尔曲线的命令包括：

    Q 二次贝赛尔曲线 x1,y1 x,y
    T 平滑二次贝塞尔曲线 x,y
    C 曲线(curveto) x1,y1 x2,y2 x,y
    S 平滑曲线 x2,y2 x,y 

**(1) Q 二次贝塞尔曲线**

**Q指令格式：** M x0,y0 Q x1,y1 x2,y2

通过M定义（x0,y0）为起点，用Q定义（x1,y1）为控制点，（x2,y2）为终点，构成一条二次贝塞尔曲线。Q方法非常简单直观。

    <?xml version="1.0" standalone="no"?>
    <svg width="400px" height="200px" version="1.1" xmlns="http://www.w3.org/2000/svg">
    
        <path d="M 50,50 Q 100,150, 150,50" stroke="black" fill="transparent"/>
    
        <text x="50" y="30" style="fill: red;">Q单独</text>
        <!-- Control line -->
        <path d="M 50,50 L 100,150 L 150,50" stroke="red" fill="transparent"/>
        <!-- Points -->
        <circle cx="50" cy="50" r="3" fill="red"/>
        <circle cx="100" cy="150" r="3" fill="red"/>
        <circle cx="150" cy="50" r="3" fill="red"/>
    </svg> 

**(2) T 二次贝塞尔曲线平滑延伸**

**T指令格式：** M x0,y0 Q x1,y1 x2,y2 T x4,y4

T命令是二次被赛尔曲线的平滑延伸命令，用Q定义了一段二次贝塞尔曲线后面，紧接着一个T命令，只需定义终点，就可以自动延伸出一条平滑的曲线。
在这段曲线中，(x3,y3)没有手工定义，这里使用的T方法，只定义一个终点，起点是上一段曲线的终点，控制点则是上一段曲线的控制点的对称点（相对于上一段曲线的终点）。

T命令前面必须是一个Q命令，或者是另一个T命令，才能达到这种效果。如果T单独使用，那么控制点就会被认为和终点是同一个点，所以画出来的将是一条直线。 
 
**(3) C 三次贝塞尔曲线**

**C指令格式：** M x0,y0 C x1,y1 x2,y2 x3,y3

C方法定义的是三次贝塞尔曲线，只是多了一个控制点。

    <?xml version="1.0" standalone="no"?>
    <svg width="400px" height="200px" version="1.1" xmlns="http://www.w3.org/2000/svg">
    
        <path d="M 250,50 C 280,130, 320,160 370,80" stroke="black" fill="transparent"/>
    
        <text x="250" y="30" style="fill: #ff0000;">C单独</text>
        <!-- Control line -->
        <path d="M 250,50 L 280,130 M 320,160 L 370,80" stroke="red" fill="transparent"/>
        <!-- Points -->
        <circle cx="250" cy="50" r="3" fill="red"/>
        <circle cx="280" cy="130" r="3" fill="red"/>
        <circle cx="320" cy="160" r="3" fill="red"/>
        <circle cx="370" cy="80" r="3" fill="red"/>
    
    </svg>

**(4) S 三次贝塞尔曲线平滑延伸**

**S指令格式：** M x0,y0 C x1,y1 x2,y2 x3,y3 S x5,y5 x6,y6

三次贝塞尔也有一个平滑延伸的命令，需要定义一个控制点和一个终点，命令会再自动生成一个控制点x4,y4，从而形成一条延伸出来的三次贝赛尔曲线。

S命令前面必须是一个C命令，或者是另一个S命令，才能达到这种效果。如果单独使用S命令，则会变成画二次贝塞尔曲线效果。 
  
[SVG 贝塞尔曲线参考一](http://justcoding.iteye.com/blog/2226354)  
[SVG 贝塞尔曲线参考二](http://www.zhangxinxu.com/wordpress/2014/06/deep-understand-svg-path-bezier-curves-command/)


**附录**  
本文摘录自：[原文地址](https://blog.csdn.net/nemo__/article/details/71099839)