# **WHULife 后端设计**

## 概述

为了方便的用户的使用，为用户提供多样的生活和娱乐方式，我们添加了订阅模块。本着保护用户隐私的理念，客户端不会上传用户的个人隐私信息，服务端也不会存储用户信息。我们选择了 BS 模式，服务端提供内容，客户端拉取服务端内容，供用户参阅。

## 信息

信息包括两大类，新闻通知和活动信息。

### 新闻通知

服务端所包含的新闻通知涉及学术信息、体育信息、图书馆信息、自强超市信息、假期信息、通知公告等诸多内容。用户在客户端设置完喜好后，客户端会根据这些喜好自动拉取相应的内容。

### 活动信息

与新闻通知里面的信息不同，活动信息把重点放到了用户所关注的主要信息上，能够简洁的表示出活动的内容、时间和地点方便用户取舍。同时，活动信息中显示的内容可以一键添加到待办事项，方便用户定制自己的活动规划。

## 数据更新

原始数据主要来自武汉大学官网、武汉大学本科生院官网、武汉大学体育部官网、武汉大学图书馆官网、掌上武大微信公众号。

考虑到数据的多样性和复杂性，目前只能通过修改服务端数据文件的形式手动更新。

## 稳定性

为了保证服务端运行稳定，同时减少编码难度，我们选择了使用 nodejs 作为服务端，用著名的 pm2 模块管理和监控运行情况。做到崩溃自动重启，服务端数据变化后自动更新，保证用户得以及时获取最新信息。

## 接口使用

### 新闻获取接口

获取所有类型新闻:

~~~
GET http://111.230.233.136:8888/api/news/?all
~~~

获取指定类型新闻:

~~~
GET http://111.230.233.136:8888/api/news/?lecture&whunews&movie&supermarket
~~~

查询信息类别包括

- lecture：学术信息类新闻
- whunews：武汉大学新闻
- holiday：假日相关新闻
- bulletin：公告板消息
- sports：运动类新闻
- movie：影视资讯
- supermarket：超市活动类
- library：图书馆新闻资讯

信息返回

~~~javascript
[
    {
     	"url":"http://cs.whu.edu.cn/news_show.aspx?id=1197",
     	"title":"2019年11月7日学术报告（龙成江 研究员，Kitware Inc.）"
    },
    {
    	"url":"http://cs.whu.edu.cn/news_show.aspx?id=1187",
        "title":"2019年10月18日学术报告（徐敏 副教授 澳大利亚悉尼科技大学）"
    }
]
~~~

JSON格式，由数组对象构成，结构如上所示。

### 订阅获取接口

查询所有可用订阅

~~~
GET http://111.230.233.136:8888/api/msg/
~~~

查询指定 ID 对应的订阅内容

~~~
POST http://111.230.233.136:8888/api/msg/

{"Num":"[1003, 1002]"}
~~~

信息返回

~~~javascript
[
  {
    mid: 1002,
    time: '2019-12-17 10:00',
    location: '信息管理学院205会议室',
    longitude: 0,
    latitude: 0,
    shortInfo: '利用大规模数字踪迹增强智能互联社区的决策',
    info: '利用大规模数字踪迹增强智能互联社区的决策（朱晓龙，香港科技大学教授）'
  },
  {
    mid: 1003,
    time: '2019-12-17 09:00',
    location: '电子信息学院303',
    longitude: 0,
    latitude: 0,
    shortInfo: '磁重联扩散区和偶极化锋面处的哨声波观测研究',
    info: '磁重联扩散区和偶极化锋面处的哨声波观测研究（符慧山，北京航天航空大学教授）'
  }
]
~~~

JSON 格式返回，如未指定消息ID，则返回所有消息。信息 MID 具有全局唯一性，可用于标识信息。

