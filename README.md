<p align="center">
	<img src="https://xuewei-blog.oss-cn-beijing.aliyuncs.com/202311081738321.png" style="zoom:50%;" />
</p>

<p align="center">
	<strong>🚀 A Cutting-edge Tool For Rapid Java Web Development.</strong>
</p>
<p align="center">
	👉 <a href="http://fast.xuewei.world/" target="_blank">http://xuewei.world </a>👈
</p>

<p align="center">
	<a target="_blank" href="https://central.sonatype.com/artifact/world.xuewei/xw-fast-all">
		<img src="https://img.shields.io/maven-metadata/v.svg?label=Maven%20Central&metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fworld%2Fxuewei%2Fxw-fast-all%2Fmaven-metadata.xml" />
	</a>
	<a target="_blank" href="https://license.coscl.org.cn/MulanPSL2">
		<img src="https://img.shields.io/:license-MulanPSL2-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg" />
	</a>
	<a target="_blank" href="https://docs.spring.io/spring-boot/docs/2.7.17/reference/html/">
		<img src="https://img.shields.io/badge/Spring%20Boot-v2.7.17+-green" />
	</a>
		<a target="_blank" href="https://docs.spring.io/spring-boot/docs/2.7.17/reference/html/">
		<img src="https://img.shields.io/badge/MyBatisPlus-v3.2.0+-blue" />
	</a>
	<a target="_blank" href='https://github.com/373675032/xw-fast'>
		<img src="https://img.shields.io/github/stars/373675032/xw-fast.svg?style=social" alt="github star"/>
	</a>
</p>

---

## 🚀简介

XwFast 是专为 Java Web 开发的基于 Spring
系列框架封装的黑科技脚手架，通过诸多实用工具类/组件的使用，旨在帮助开发者快速、便捷地完成各类开发任务。这些封装的工具组件涵盖了基础增删查改接口、邮件客户端、短信客户端、等一系列操作，可以满足各种不同的开发需求。

## ❤️理念

XwFast 旨在解放程序员生产力，用最少的代码做最多的事，将您的宝贵时间放在复杂业务和提升自我上，简单的事就交给 XwFast 吧！

## 🛠️组件

XwFast
采用了组件式的架构设计，您可按需引入所需要的模块，当然如果您是成年人，“成年人不做选择，我全都要！”，您可直接引入 `xw-fast-all`。

| 模块           | 介绍                    |
|--------------|-----------------------|
| xw-fast-all  | 关于 XwFast 的全部内容       |
| xw-fast-core | 核心，包括的各种工具类、异常定义等     |
| xw-fast-crud | 封装基础增删查改的基础接口、基础实体的封装 |
| xw-fast-web  | 封装一些开箱即用的 Spring 组件   |
| ...          | ...                   |

## 📦安装

### 🍊Maven

在项目的 `pom.xml` 的 `dependencies` 中加入以下内容：

```xml
<!-- 引入 Xw-Fast -->
<dependency>
    <groupId>world.xuewei</groupId>
    <artifactId>xw-fast-all</artifactId>
    <version>1.0.3</version>
</dependency>
```

### 📥下载jar

点击链接，下载 `xw-fast-all-X.X.X.jar`
即可，[Maven中央库](https://repo1.maven.org/maven2/world/xuewei/xw-fast-all/1.0.1/)。

## 📝文档

### 快速开始

创建一个基础的 SpringBoot 项目，引入下面依赖：

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <version>2.7.17</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.7.17</version>
    </dependency>

    <!-- MyBatis Plus -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.2.0</version>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
        <version>5.1.48</version>
    </dependency>

    <!-- Druid -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.2.8</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.2.8</version>
    </dependency>

    <!-- 引入 Xw-Fast -->
    <dependency>
        <groupId>world.xuewei</groupId>
        <artifactId>xw-fast-all</artifactId>
        <version>1.0.3</version>
    </dependency>

</dependencies>
```

将 XwFast 的组件配置给 SpringBoot 扫描：

```java
@ComponentScan(value = {"world.xuewei.fast", "你自己的项目包路径"})
@SpringBootApplication
public class XwFastDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(XwFastDemoApplication.class, args);
    }
}
```

在 SpringBoot 配置文件中正常配置数据源等信息，之后创建基础目录结构，如下（也可按照自己的想法自行设计）：

#### controller

控制器包，编写控制器时继承 XwFast 封装的 BaseController，传入绑定的实体泛型。也可以在当前控制器类上声明注解
@FastController，XwFast 会自动注册这些增删查改接口。

点击链接查看接口示例
👉 [基础增删查改接口示例 - XwFast](https://apifox.com/apidoc/shared-c29aa917-b35f-4886-9a32-370a72de349d)

```java

@RestController
@RequestMapping("/Example")
public class ExampleController extends BaseController<Example> {

    public ExampleController(ExampleService service) {
        super(service);
    }

    // Write Your Code Here ...
}
```

```java

@RestController
@FastController
@RequestMapping("/Example")
public class ExampleController {

    @Autowired
    private ExampleService service;

    // Write Your Code Here ...
}
```

#### service

业务实现层，编写服务时继承 XwFast 封装的 BaseDBService，里面像 MyBatisPlus 一样封装了增删查改的接口，如果想问区别的话，那么我只能说
XwFast 更全面！

```java

@Service
public class ExampleService extends BaseDBService<Example> {

    public ExampleService(ExampleMapper mapper) {
        super(mapper);
    }

    // Write Your Code Here ...
}
```

#### mapper

数据库访问层，和 Dao 是同一个意思，这里你可以直接继承自 MyBatisPlus 的 BaseMapper 类，传入实泛型。

```java

@Mapper
public interface ExampleMapper extends BaseMapper<Example> {

    // Write Your Code Here ...
}
```

#### entity

数据库实体包，编写实体时继承 XwFast 封装的 BaseEntity，涉及到一些数据库的基础字段。

```java

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("example")
@EqualsAndHashCode(callSuper = true)
public class Example extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 541179640568881082L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

}
```

> 以上步骤昨晚之后那么恭喜你，运行服务就可以打开 Postman、Apifox 等接口测试工具测试了！
>
> 接口文档可查看示例：[基础增删查改接口示例 - XwFast](https://apifox.com/apidoc/shared-c29aa917-b35f-4886-9a32-370a72de349d)

> 如果你觉得创建、编写上面的几个类文件麻烦的话，XwFast 也帮你想了办法，你可以下载一个名为 EasyCode 的 IDEA 插件，这个插件可以帮你一键生成对应的实体、服务、DAO、Controller 等。但是你需要将 XwFast 提供的代码模板导入到你的 IDEA 配置。
>
> ![](https://xuewei-blog.oss-cn-beijing.aliyuncs.com/202311091230970.png)
>
> **可关注我的公众号：【薛伟同学】，后台回复【XwFast模板】获取代码模板。**
>
> [【B站 - EasyCode使用教学视频 - 薛伟同学】](https://www.bilibili.com/video/BV1f7411R7se/)

## 🏗️添砖加瓦

### 🌵分支说明

XwFast 的源码分为两个分支，功能如下：

| 分支   | 说明                                              |
|------|-------------------------------------------------|
| main | 主分支，release 版本使用的分支，与中央库提交的 jar 一致，不接收任何 pr 或修改 |
| dev  | 开发分支，默认为下个版本的 SNAPSHOT 版本，接受修改或 pr              |

### 🐞提供bug反馈或建议

提交问题反馈请说明正在使用的 JDK 版本、XwFast 版本和相关依赖库版本。

[Issues · XwFast](https://github.com/373675032/xw-fast/issues)

### 💡贡献代码的步骤

1. 在 Github 上 fork 项目到自己的 repo
2. 把 fork 过去的项目也就是你的项目 clone 到你的本地
3. 修改代码（记得一定要修改 dev 分支）
4. commit 后 push 到自己的库（dev分支）
5. 登录 Github 在你首页可以看到一个 Pull Request 按钮，点击它，填写一些说明信息，然后提交即可
6. 等待维护者合并

### 📐PR遵照的原则

XwFast 欢迎任何人为 XwFast 添砖加瓦，贡献代码，不过维护者是一个强迫症患者，为了照顾病人，需要提交的 pr（pull
request）符合一些规范，规范如下：

1. 注释完备，尤其每个新增的方法应按照 Java 文档规范标明方法说明、参数说明、返回值说明等信息，必要时请添加单元测试，如果愿意，也可以加上你的大名。
2. XwFast 代码的缩进按照默认（tab）缩进，所以请遵守（不要和我争执空格与 tab 的问题，这是一个病人的习惯）。
3. 请 pull request 到 `dev` 分支。
4. 我们如果关闭了你的 issue 或 pr，请不要诧异，这是我们保持问题处理整洁的一种方式，你依旧可以继续讨论，当有讨论结果时我们会重新打开。
