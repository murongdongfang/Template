

1. 前后端分离开发模式中，api文档是最好的沟通方式。

   Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。

   及时性 (接口变更后，能够及时准确地通知相关前后端开发人员)

   规范性 (并且保证接口的规范性，如接口的地址，请求方式，参数及响应格式和错误信息)

   一致性 (接口信息一致，不会出现因开发人员拿到的文档版本不一致，而出现分歧)

   可测性 (直接在接口文档上进行测试，以方便理解业务)

2. 引入依赖

   ```xml
     <!--swagger-->
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger2</artifactId>
       <scope>provided </scope>
   </dependency>
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger-ui</artifactId>
       <scope>provided </scope>
   </dependency>
   ```

3. 配置类配置swagger

   ```java
   /**
    *@author xxh
    *@date 2020/4/20
    *@discription: SpringBoot整合Swagger
    */
   @Configuration
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
   
     private ApiInfo webApiInfo(){
   
       return new ApiInfoBuilder()
         .title("网站-课程中心API文档")
         .description("本文档描述了课程中心微服务接口定义")
         .version("1.0")
         .contact(new Contact("xxh", "http://baidu.com", "2261276148@qq.com"))
         .build();
     }
   }
   ```

3. 一定要在启动类上标注`@EnableSwagger2`

4. swagger三个常用的注解

   定义在类上：@Api

   定义在方法上：@ApiOperation

   定义在参数上：@ApiParam

   ```java
   @Api(description="讲师管理")
   @RestController
   @RequestMapping("/admin/edu/teacher")
   public class TeacherAdminController {
   
   	@Autowired
   	private TeacherService teacherService;
   
   	@ApiOperation(value = "所有讲师列表")
   	@GetMapping
   	public List<Teacher> list(){
   		return teacherService.list(null);
   	}
   
   	@ApiOperation(value = "根据ID删除讲师")
   	@DeleteMapping("{id}")
   	public boolean removeById(
   			@ApiParam(name = "id", value = "讲师ID", required = true)
   			@PathVariable String id){
   		return teacherService.removeById(id);
   	}
   }
   ```

   