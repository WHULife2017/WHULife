# 课程提醒模块第三次迭代说明

## 概述

第三次迭代主要是整理了课表的数据，增加了手动添加课表以及撤课的操作，调用地点接口和时间接口生成提醒。修改了一些小bug。

## 工作情况

###### 伍晶晶

完善了课程提醒部分的功能，调用地点接口、时间接口对于当前时间当前地点的上课教室提醒；增加手动添加课表功能，需要输入课程号、课程名、课程时间以及上课地点；增加手动撤课功能，需要输入课程号和课程名。操作成功会提醒操作成功。

###### 姜星宇

修改`clockmanager.java`

增加`setAlarm2()`函数，利用周、时、分、秒设置闹钟

在`setAlarm()`函数中比较设置时间和当前时间，当设置时间早于当前时间，不设置闹钟

修改`EditTextActivity.java`

`Id`初始值设为0，利用id的值判断闹钟数量，当没有闹钟时不用取消闹钟并设定提醒

## 测试情况

### 拉取课表

输入账号密码验证码，抓取到的数据如下：

![1576769367417](C:\Users\wujj\AppData\Roaming\Typora\typora-user-images\1576769367417.png)

### 定位功能

当前测试时间是上午近八点，修改地点定位的时间限制，运行后点击地点定位，可以看到如下提醒页面

![1576799960036](C:\Users\wujj\AppData\Roaming\Typora\typora-user-images\1576799960036.png)



### 手动加课

如添加高数课程后，显示的课程信息如下：

![1576770846698](C:\Users\wujj\AppData\Roaming\Typora\typora-user-images\1576770846698.png)

### 手动撤课

手动撤除高数后，显示的课程信息如下：

![1576770879647](C:\Users\wujj\AppData\Roaming\Typora\typora-user-images\1576770879647.png)

### 闹钟功能

代码中闹钟部分用于提醒上课，如果打开时时间已经超过上课时间，会提醒设置时间已过。

![1576800397429](C:\Users\wujj\AppData\Roaming\Typora\typora-user-images\1576800397429.png)

如果到设置时间，会生成提醒页面：

![1576809152364](C:\Users\wujj\AppData\Roaming\Typora\typora-user-images\1576809152364.png)

