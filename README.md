# DEVELOPING

## 介绍

`Leafer`是基于Java实现的一个笔记管理项目，现在还是处于beta版本，如果有任何的bug或者功能改进方面上的建议，欢迎大家提issues帮我改进，thanks :-)

## 部署

```bash
$ git clone https://github.com/ziewnxie/leafer
$ cd leafer
```

然后修改`/src/main/resources/application.properties`中本地MySQL数据库的配置：

```xml
spring.datasource.url=jdbc:mysql://localhost:3306/数据库名
spring.datasource.username=用户名
spring.datasource.password=密码
```

最后将`leafer.sql`下面的`sql`语句复制到上面指定的数据库中，就可以执行下面maven命令运行了：

```bash
$ mvn spring-boot:run
```

在浏览器中打开`localhost:8080`使用默认的用户名`admin`以及登录密码`123456`登录即可。

p.s: 如果上面的部署过程中有任何的疑问欢迎在GitHub上反馈 ^_^。

## 功能特性

**首页显示**

![index](http://upload-images.jianshu.io/upload_images/4003106-f60661d213fa8372.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**单篇文章显示**

![article](http://upload-images.jianshu.io/upload_images/4003106-4d9e05e5c6401d97.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**简洁的markdown编辑器**

![markdown eidtor](http://upload-images.jianshu.io/upload_images/4003106-b01cddae7f904191.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**标签云**

![tag cloud](http://upload-images.jianshu.io/upload_images/4003106-e74d44b35880a883.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Contact

GitHub: https://github.com/ziwenxie
Blog: https://www.ziwenxie.site
Email: ziwenxiecat@gmail.com

  [1]: https://github.com/ziwenxie/leafer
