# readme
## 活动（activity）
快递组一共使用两个活动，一个是PackageActivity，另一个是setLocation。
### packageActivity
该活动是快递任务的整体预览，可以添加查看快递任务信息，以及添加快递任务。当需要整合项目时，只需设置跳转到本活动即可
### setLocation
本活动是快递任务设置活动，设置好快递任务的领取地点，领取单号，快递公司以及提醒时间等，并将设置好的值返回PackageActivity，且在该页面上显示。本活动暂未实现时间提醒设置
## 其他类
Package.class 是为了将快递任务显示在PackageActivity上而设计的类

PackageAdapter.class 是为了将快递任务显示在PackageActivity上而必须的类，和Package.class 搭配使用

Location.class，Company.class 是为了设置快递任务而设计的类

Locate.class 是定位接口
