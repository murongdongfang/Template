1. 只需要引入mybatis-plus的依赖即可，无需引入mybatis和mybatis-spring依赖

   ```xml
   <dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-boot-starter</artifactId>
       <version>3.0.5</version>
   </dependency>
   ```

   

2. 实现基础的增删改查只需要`interface` 继承`BaseMapper<>` 

3. 要想看到mp底层操作的sql语句只需要在全局配置文件中配置

   ```yaml
   #mybatis-plus打印sql语句日志
   mybatis-plus:
     configuration:
       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
   ```

4. 调用mp更新方法的时候，实体类中属性字段为null的数据表中相应的字段不会更新

5. 以下两项配置不能共存

   ```yaml
   mybatis-plus:
     configuration:
     config-location:  
   ```

   

1. 生成器使用之前引入相关模板引擎依赖

   ```xml
   <!-- Apache velocity -->
   <dependency>
       <groupId>org.apache.velocity</groupId>
       <artifactId>velocity-engine-core</artifactId>
       <version>2.0</version>
   </dependency>
   ```

7. MP中最常用的条件构造器的实现实现类是`QueryWrapper`

8. Mybatis-plus中条件构造器查询结果只返回指定字段

   ```java
   //先根据课程id查出这个课程下面所有的视频id
   QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
   videoWrapper.eq("course_id",courseId);
   //select：只查询数据表中的video_source_id字段，最终封装的EduVideo中只有videoSourceId有值
   //也可以传入多个值
   videoWrapper.select("video_source_id");
   ```

9. Mybatis-plus条件构造器拼接SQL语句

   ```java
   QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
   wrapper.orderByDesc("id");
   //采用last方法，拼接sql语句。
   wrapper.last("limit 2");
   List<CrmBanner> list = baseMapper.selectList(wrapper);
   
   ```

   

10. 生成器的配置

```java
@Test
  public void  testGenerator() {
    //1. 全局配置
    GlobalConfig config = new GlobalConfig();
    config.setActiveRecord(true) // 是否支持AR模式
      .setAuthor("xxh") // 作者
      .setOutputDir("E:\\workspace\\ideaSrc\\mybatis_plus\\src\\main\\java") // 生成路径
      .setFileOverride(true)  // 后生成的文件把先生成的文件覆盖
      .setIdType(IdType.AUTO) // 主键策略
      .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I， 默认设置，如果不想设置成这样写成%sService即可
      .setBaseResultMap(true)    //生成基本的resultMap
      .setBaseColumnList(true)    //mapper配置文件中是否抽取公共列名
      //.setEnableCache(true)     //是否开启二级缓存


    ;
    //2. 数据源配置
    DataSourceConfig dsConfig  = new DataSourceConfig();
    dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
      .setDriverName("com.mysql.cj.jdbc.Driver")
      .setUrl("jdbc:mysql://localhost:3306/blog?serverTimezone=GMT%2B8")
      .setUsername("root")
      .setPassword("1234");

    //3. 策略配置
    StrategyConfig stConfig = new StrategyConfig();
    stConfig.setCapitalMode(true) //生成类的时候类名首字母大写
      .setNaming(NamingStrategy.underline_to_camel) // 将数据库中下划线命名字段，生成实体类的时候转为驼峰命名
      .setTablePrefix("t_")   //根据表生成实体类的时候去掉前面的前缀tbl_
      //.setInclude("tbl_employee")  // 生成实体类所根据的数据表

    ;

    //4. 包名策略配置
    PackageConfig pkConfig = new PackageConfig();
    pkConfig.setParent("com.whpu.mybatis_plus")
      .setMapper("mapper")
      .setService("service")
      .setController("controller")
      .setEntity("entity")
      .setXml("mappers")

    ;

    //5. 整合配置
    AutoGenerator ag = new AutoGenerator();

    ag.setGlobalConfig(config)
      .setDataSource(dsConfig)
      .setStrategy(stConfig)
      .setPackageInfo(pkConfig)
    ;

    //6. 执行
    ag.execute();
  }
```




```java
 @Test
  public void generateCode() {

    // 1、创建代码生成器
    AutoGenerator mpg = new AutoGenerator();

    // 2、全局配置
    GlobalConfig gc = new GlobalConfig();

    String projectPath = System.getProperty("user.dir");
    System.out.println(projectPath);
    gc.setOutputDir(projectPath + "/src/main/java");
    gc.setAuthor("xxh");
    gc.setOpen(false); //生成后是否打开资源管理器
    gc.setFileOverride(false); //重新生成时文件是否覆盖
    /*
     * mp生成service层代码，默认接口名称第一个字母有 I
     * UcenterService
     * */
    gc.setServiceName("%sService");	//去掉Service接口的首字母I
    gc.setIdType(IdType.ID_WORKER); //主键策略
    gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
    gc.setSwagger2(true);//开启Swagger2模式

    mpg.setGlobalConfig(gc);

    // 3、数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("1234");
    dsc.setDbType(DbType.MYSQL);
    mpg.setDataSource(dsc);

    // 4、包配置
    PackageConfig pc = new PackageConfig();
    pc.setModuleName("serviceedu"); //具体模块名
    pc.setParent("com.whpu");//公司域名
    pc.setController("controller");
    pc.setEntity("entity");
    pc.setService("service");
    pc.setMapper("mapper");
    mpg.setPackageInfo(pc);

    // 5、策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setInclude("edu_teacher");  //只生成这个数据库中的一张表，如果没有这个配置，每张表都生成代码
    strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
    strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

    strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
    strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

    strategy.setRestControllerStyle(true); //restful api风格控制器
    strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

    mpg.setStrategy(strategy);

    // 6、执行
    mpg.execute();
  }
```

