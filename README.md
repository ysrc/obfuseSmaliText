# obfuseSmaliText
smali字符串混淆

相关文章 http://mp.weixin.qq.com/s/SRv1Oar87w1iKuDXS4oaew

注意：配置中设置了仅会混淆包名目录下的文件

##### 2017-3-2
目前使用异或+十六进制的方式对字符串进行混淆，支持中文字符，测试未发现问题，有问题欢迎反馈

##### 2017-3-14
参考StringFog，增加对jar包的字符串混淆（使用asm）,支持自定义key

##### 2017-3-21
修复了因ide字符串替换导致的错误，编译了两个可执行jar包，并写了两个bat文件，双击即可执行

##### 2017-5-5
更新ObfuseJarString方法，替换其中class字节码为java7，防止dx无法正确编译dex

##### 2017-5-10
更新jar包混淆string方式，改为每个字符串解密对应一个解密key，字符串以byte数组呈现（未更新jar包，需自行编译），一定要注意编码问题，不然会导致乱码

##### 2017-07-17
修改smali混淆的目录为手工输入，部分apk包名对应的目录下可能会没有文件，先暂时修改为这样，后期修改为按照配置文件对目录进行混淆

##### 2017-07-19
添加支持gradle的字符串混淆，在打包时进行自动混淆

* 将obfuseStringGradle.jar文件放在工程根目录，在项目build.gradle下添加如下
```
android.libraryVariants.all{ variant ->  //module工程则为libraryVariants，主项目则是applicationVariants
    variant.javaCompile.doLast{
        println  ("start classes obfuscation "+"${variant.javaCompile.destinationDir}")
        javaexec {
            main = "-jar";
            args = [
                    "../obfuseStringGradle.jar",
                    project.name,
                    variant.javaCompile.destinationDir
            ]
        }
    }
}
```



##### 使用方法
* 先使用apktool.jar将apk进行反编译
* java -Dfile.encoding=utf-8 -jar 执行jar
* 输入当前已经反编译apk的路径（复制粘贴即可）
* 等待任务完成，重新打包回去即可

注：`jar包执行时需要指定运行编码，不然会导致混淆后乱码 ，使用命令如：java -Dfile.encoding=utf-8 -jar obfuseJarString.jar`
 
##### 混淆版酷安网
[下载](https://qtfreet.cn/com.coolapk.market_7_Mod.apk)

#### smali混淆效果图
![](http://p1.bpimg.com/567571/90927a8fd19786b1.png)

#### jar包混淆效果图
![](http://i4.buimg.com/588926/d9b230241ef448ea.png)
