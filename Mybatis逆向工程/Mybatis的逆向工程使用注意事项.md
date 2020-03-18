1. 使用maven命令生成的时候配置文件必须要写到maven工程的resources文件下，而且必须命名为generatorConfig.xml
2. maven命令参数在Eclipse右键->maven->maven build配置参数mybatis-generator:generate
   idea下和配置tomcat一样配置maven参数mybatis-generator:generate
3. maven还有一种使用代码生成相应的实体类和dao的方法，此时不想要求配置文件的名字和位置，只需要在代码中把配置文件传入即可
4. Mbg自动生成的实体类没有无参构造器，toString方法和有参构造器
5. Mbg带有复杂条件的SQL用法

```java
//创建roleExample对象
RoleExample roleExample = new RoleExample();
//创建Criceria对象选择相应的方法
RoleExample.Criteria criteria = roleExample.createCriteria().andIdIn(roleIdList);
//把Example对象传入Mapper里边执行复杂条件的SQL
int res = roleMapper.deleteByExample(roleExample);
```



