@Test
  public void  testGenerator() {
    //1. ȫ������
    GlobalConfig config = new GlobalConfig();
    config.setActiveRecord(true) // �Ƿ�֧��ARģʽ
      .setAuthor("xxh") // ����
      .setOutputDir("E:\\workspace\\ideaSrc\\mybatis_plus\\src\\main\\java") // ����·��
      .setFileOverride(true)  // �����ɵ��ļ��������ɵ��ļ�����
      .setIdType(IdType.AUTO) // ��������
      .setServiceName("%sService")  // �������ɵ�service�ӿڵ����ֵ�����ĸ�Ƿ�ΪI�� Ĭ�����ã�����������ó�����д��%sService����
      .setBaseResultMap(true)    //���ɻ�����resultMap
      .setBaseColumnList(true)    //mapper�����ļ����Ƿ��ȡ��������
      //.setEnableCache(true)     //�Ƿ�����������


    ;
    //2. ����Դ����
    DataSourceConfig dsConfig  = new DataSourceConfig();
    dsConfig.setDbType(DbType.MYSQL)  // �������ݿ�����
      .setDriverName("com.mysql.cj.jdbc.Driver")
      .setUrl("jdbc:mysql://localhost:3306/blog?serverTimezone=GMT%2B8")
      .setUsername("root")
      .setPassword("1234");

    //3. ��������
    StrategyConfig stConfig = new StrategyConfig();
    stConfig.setCapitalMode(true) //�������ʱ����������ĸ��д
      .setNaming(NamingStrategy.underline_to_camel) // �����ݿ����»��������ֶΣ�����ʵ�����ʱ��תΪ�շ�����
      .setTablePrefix("t_")   //���ݱ�����ʵ�����ʱ��ȥ��ǰ���ǰ׺tbl_
      //.setInclude("tbl_employee")  // ����ʵ���������ݵ����ݱ�

    ;

    //4. ������������
    PackageConfig pkConfig = new PackageConfig();
    pkConfig.setParent("com.whpu.mybatis_plus")
      .setMapper("mapper")
      .setService("service")
      .setController("controller")
      .setEntity("entity")
      .setXml("mappers")

    ;

    //5. ��������
    AutoGenerator ag = new AutoGenerator();

    ag.setGlobalConfig(config)
      .setDataSource(dsConfig)
      .setStrategy(stConfig)
      .setPackageInfo(pkConfig)
    ;

    //6. ִ��
    ag.execute();
  }