
  @Test
  public void generateCode() {

    // 1����������������
    AutoGenerator mpg = new AutoGenerator();

    // 2��ȫ������
    GlobalConfig gc = new GlobalConfig();

    String projectPath = System.getProperty("user.dir");
    System.out.println(projectPath);
    gc.setOutputDir(projectPath + "/src/main/java");
    gc.setAuthor("xxh");
    gc.setOpen(false); //���ɺ��Ƿ����Դ������
    gc.setFileOverride(false); //��������ʱ�ļ��Ƿ񸲸�
    /*
     * mp����service����룬Ĭ�Ͻӿ����Ƶ�һ����ĸ�� I
     * UcenterService
     * */
    gc.setServiceName("%sService");	//ȥ��Service�ӿڵ�����ĸI
    gc.setIdType(IdType.ID_WORKER); //��������
    gc.setDateType(DateType.ONLY_DATE);//�������ɵ�ʵ��������������
    gc.setSwagger2(true);//����Swagger2ģʽ

    mpg.setGlobalConfig(gc);

    // 3������Դ����
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("1234");
    dsc.setDbType(DbType.MYSQL);
    mpg.setDataSource(dsc);

    // 4��������
    PackageConfig pc = new PackageConfig();
    pc.setModuleName("serviceedu"); //����ģ����
    pc.setParent("com.whpu");//��˾����
    pc.setController("controller");
    pc.setEntity("entity");
    pc.setService("service");
    pc.setMapper("mapper");
    mpg.setPackageInfo(pc);

    // 5����������
    StrategyConfig strategy = new StrategyConfig();
    strategy.setInclude("edu_teacher");  //ֻ����������ݿ��е�һ�ű����û��������ã�ÿ�ű����ɴ���
    strategy.setNaming(NamingStrategy.underline_to_camel);//���ݿ��ӳ�䵽ʵ�����������
    strategy.setTablePrefix(pc.getModuleName() + "_"); //����ʵ��ʱȥ����ǰ׺

    strategy.setColumnNaming(NamingStrategy.underline_to_camel);//���ݿ���ֶ�ӳ�䵽ʵ�����������
    strategy.setEntityLombokModel(true); // lombok ģ�� @Accessors(chain = true) setter��ʽ����

    strategy.setRestControllerStyle(true); //restful api��������
    strategy.setControllerMappingHyphenStyle(true); //url���շ�ת���ַ�

    mpg.setStrategy(strategy);

    // 6��ִ��
    mpg.execute();
  }