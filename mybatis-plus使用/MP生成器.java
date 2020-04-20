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