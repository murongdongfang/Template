https://juejin.im/post/5dc8e9ec5188254a5d3f02c1



# 注意事项：

1. 一定要在启动类上标注`@EnableSwagger2`

4. swagger三个常用的注解用于对代码的说明

   定义在类上：@Api

   定义在方法上：@ApiOperation

   定义在参数上：@ApiParam


## 一、SpringBoot引入Swagger的两种方式

目前SpringBoot有两种使用Swagger的方式：

> 1. 引入swagger原生依赖`springfox-swagger2`和`springfox-swagger2-ui`
> 2. 引入国内Spring4All社区开发的依赖`swagger-spring-boot-starter`

Spring4All出品的依赖采取配置文件的方式进行配置，而原生依赖是通过java config类来设置的。

### 1.1  原生配置Swagger

maven依赖：

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>

<dependency>
	<groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
 	<version>2.9.2</version>
</dependency>

```

swagger配置类：

```java
/**
 * swagger2配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket webApiConfig(){
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("webApi")
      .apiInfo(webApiInfo())
      .select()
      .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
      .paths(Predicates.not(PathSelectors.regex("/error.*")))
      .build();

  }
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
      			.paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基于Swagger构建的Rest API文档")
                .description("更多请咨询服务开发者eknown")
                .contact(new Contact("空夜", "http://www.eknown.cn", "eknown@163.com"))
                .termsOfServiceUrl("http://www.eknown.com")
                .version("1.0")
                .build();
    }
}
复制代码
```

从这里可以看出Swagger的一个缺点：无法通过SpringBoot的配置文件进行配置，所以配置并不能灵活转变。

spring4all社区出品的swagger-spring-boot-starter可以解决这个问题。

------

### 1.2  基于spring4all配置swagger

Spring4All社区的博主程序猿DD和小火两个人开发了Spring Boot Starter Swagger，目前已经在maven官方仓库上线了。



选择第一个，引入该依赖：

```xml
<dependency>
    <groupId>com.spring4all</groupId>
    <artifactId>swagger-spring-boot-starter</artifactId>
    <version>1.9.0.RELEASE</version>
</dependency>
复制代码
```

这种方式的Swagger配置是通过application配置文件进行的。下面给出一个示例：

```yml
server:
  port: 8106
swagger:
  base-path: /**
  base-package: 'com.example'
  title: 'spring-boot-swagger-demo'
  description: '基于Swagger构建的SpringBoot RESTApi 文档'
  version: '1.0'
  contact:
    name: '空夜'
    url: 'http://www.eknown.cn'
    email: 'eknown@163.com'
复制代码
```

------

## 二、应用Swagger构建接口可视化

### 2.1 Controller类添加Swagger注解

下面给一个简单的示例：

```java
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "user")
public class UserController {

    @ApiOperation("列表查询")
    @GetMapping(value = "")
    public List<User> list() {
        return new ArrayList<>(userMap.values());
    }


}

复制代码
```

### 2.2 参数实体类添加Swagger注解

实体类也需要添加一些注解，以便前端开发人员确定参数的意义、类型、示例等。

```java
@ApiModel(description = "用户类")
public class User {

    @ApiModelProperty(value = "ID", example = "100")
    private Integer id;


    @ApiModelProperty("更新时间")
    private Date updateTime;
}

```

### 2.3 测试

启动项目，访问http://localhost:port/swagger-ui.html

如果存在server.servlet.context-path配置，那么访问地址是http://localhost:port/context-path/swagger-ui.html



