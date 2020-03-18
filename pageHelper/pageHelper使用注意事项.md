# Spring整合Mybatis以及pageHelper

1. 使用之前需要引入依赖

   ```xml
   <dependency>
           <groupId>com.github.pagehelper</groupId>
           <artifactId>pagehelper</artifactId>
           <version>4.0.0</version>
   </dependency>
   ```

   

2. 需要再spring整合mybatis的sqlSessionFactory的时候配置pageHelper插件，需要配置的有数据库以及合理化修正

   ```xml
   <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
   		<!-- 指定MyBatis全局配置文件位置 -->
   		<property name="configLocation" value="classpath:mybatis-config.xml"/>
   		
   		<!-- 指定Mapper.xml配置文件位置 -->
   		<property name="mapperLocations" value="classpath:mybatis/mapper/*Mapper.xml"/>
   		
   		<!-- 装配数据源 -->
   		<property name="dataSource" ref="dataSource"/>
   		
   		<!-- 配置插件 -->
   		<property name="plugins">
   			<array>
   				<!-- 配置PageHelper插件 -->
   				<bean class="com.github.pagehelper.PageHelper">
   					<property name="properties">
   						<props>
   							<!-- 配置数据库方言，告诉PageHelper当前使用的数据库 -->
   							<prop key="dialect">mysql</prop>
   							
   							<!-- 配置页码的合理化修正，在1~总页数之间修正页码 -->
   							<prop key="reasonable">true</prop>
   						</props>
   					</property>
   				</bean>
   			</array>
   		</property>
   	</bean>
   ```

3. 使用方法

```java
@Override
  public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
    //使用PageHelper启动分页查询
    PageHelper.startPage(pageSize,pageNum);
    List<Admin> admins = adminMapper.selectAdminListByKeyword(keyword);
    //将查询的数据包装成为PageInfo对象，里边包含了每一页的详细信息
    PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
    return adminPageInfo;
  }
```

