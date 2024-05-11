# District-SQL
通过高德数据获取国内的地区信息


通过高德数据获取国内最新的省、市、区县和街道乡镇四级数据。


## 登录高德开发者平台官网 创建一个web应用的key
https://console.amap.com/dev/key/app
![img.png](./img.png)

## 调用高德接口
https://lbs.amap.com/api/webservice/guide/api/district

## 处理数据，插入数据库表
具体代码看：[DistrictTestController.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fdistrictsql%2FDistrictTestController.java)

 - 已经处理好的数据看（深度优先遍历结果）：[sys_district.sql](sys_district.sql)
 - 已经处理好的数据看（广度优先遍历结果）：[sys_district2.sql](sys_district2.sql)

