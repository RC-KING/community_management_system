package com.jdd.community_management_system;




class CodeGeneratorApplicationTests {
//
//
//    // 将代码生成的版本回到 3.3.1,会导致MybatisPlusConfig失效
//    public static void main(String[] args) {
//        // ******************* //
//        // 建议一次一个实体类的生成 //
//        // ******************* //
//        String author = "金大大";
//        String database = "jdd_college_club_system";
//        String tableName = "t_sys_permission"; // 需要每次输入正确的表名,这个是关键
//        String outputDir= "com.jdd.community_management_system.pojo";// 所有实体生成的地方
//        // ==============================================================
//        String tableNameWithoutPrefix = tableName;
//
//        // 1. 构建代码自动生成器对象
//        AutoGenerator autoGenerator = new AutoGenerator();
//        // 配置策略
//        // 1. 全局配置
//        GlobalConfig globalConfig = new GlobalConfig();
//        // 设置代码生成后存放的文件夹
//        // 固定写法(web项目不适用,这样访问是Tomcat的bin目录)
//        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
//        globalConfig.setAuthor(author);
//        globalConfig.setOpen(false);//打开代码所在的文件夹
//        globalConfig.setFileOverride(true);//是否覆盖生成
//        globalConfig.setServiceName("%sService");// 生成的Service去除I前缀(使用的正则表达式)
//        globalConfig.setIdType(IdType.ID_WORKER);// 主键策略
//        globalConfig.setDateType(DateType.ONLY_DATE);//日期类型
//        globalConfig.setSwagger2(true); //注意得引入对应的依赖
//        autoGenerator.setGlobalConfig(globalConfig);
//
//        // 2. 设置数据源
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
//        dataSourceConfig.setUrl("jdbc:mysql:///"+database+"?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
//        dataSourceConfig.setUsername("root");
//        dataSourceConfig.setPassword("root");
//        dataSourceConfig.setDbType(DbType.MYSQL);
//        autoGenerator.setDataSource(dataSourceConfig);
//
//        // 3. 包的配置
//        PackageConfig packageConfig = new PackageConfig();
//        // 获取字符串第一个位置
//        char c1 = tableName.charAt(0);
//        char c2 = tableName.charAt(1);
//        tableNameWithoutPrefix = tableName.substring(2, tableName.length());
//        packageConfig.setModuleName(tableNameWithoutPrefix); // 设置模块的名称,用名为user的包将controller,service...包裹,可以分模块填写,建议和表名相同
//        packageConfig.setParent(outputDir); // 自己的包名(确保正确)
//        packageConfig.setEntity("entity");
//        packageConfig.setMapper("mapper");
//        packageConfig.setController("controller");
//        packageConfig.setService("service");
//        autoGenerator.setPackageInfo(packageConfig);
//
//        // 4. 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);//包命名规则 下划线 转 驼峰命名
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 列命名规则 下划线 转 驼峰命名
//        strategy.setVersionFieldName("version");// 乐观锁
//        strategy.setLogicDeleteFieldName("deleted"); // 逻辑删除(这个可以不用配置,用上面的配置)
//        // 自动填充策略-开始
//        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
//        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
//        ArrayList<TableFill> TableFill= new ArrayList<>();
//        TableFill.add(gmtModified);
//        TableFill.add(gmtCreate);
//        strategy.setTableFillList(TableFill);
//        // 自动填充策略-结束
//
//        strategy.setEntityLombokModel(true); //是否使用Lombok,注意引入依赖
//        strategy.setRestControllerStyle(true); // 是否使用RestFul风格
//        // strategy.setControllerMappingHyphenStyle(true); // 使用下划线命名 localhost:8080/hello_id_2
//        strategy.setTablePrefix("t_"); // 去除表前缀
//
//
//        strategy.setInclude(tableName);// 这里要填写正确的表名
//        autoGenerator.setStrategy(strategy);
//
//        // 执行自动生成
//        autoGenerator.execute();
//    }
}