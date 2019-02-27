# ExtraUtil

注意:github上编辑文件需要在该行末尾添加两个空格

总体文件说明
```$xslt
ListWrap:带有默认初始化的List工具类
BeanUtil:提供了对实体类的相关操作方法
Table:提供一个表结构的数据格式类
ListSplit:提供一个将大List分隔成指定长度的小List的工具类
ObjectConvert:进不同对象间的数据拷贝工具类
ClassLoaderUtil:提供了classLoader获取方法,以及从jar包中获取某一包下所有文件的全限定名的测试demo
```

ListWrap类提供了一个List的包装工具类，能够弥补List不能够默认初始化的不足
```
用法：  
  ListWrap<Integer> listWrap = new ListWrap<>(0);  
  listWrap.add(4,4);

其中：add用于向List中指定的位置添加元素，如上,向List的索引为4的位置添加了一个元素4,  
其未为指定元素的索引位置,使用默认值0,进行初始化

该List包装类用途之一：主要是在对前端返回图表类数据结构时，有些时候需要对查询数据时，没有数据的位置补0,  
就可以通过该工具类方便实现
```

BeanUtil类提供了对实体类的相关操作方法，目前暂时提供的方法有如下：
```
//获取List对象中指定属性名的属性值
List<T> getPropertys（List<? extends Object> objs,String name,Class<T> propertyType）
    
//将实体类对象转成JSONObject,key为属性名,value为对象属性的值
List<JSONObject> beanToJSON(List<? extends Object> objs,String... propertyNames)

用法：
public class User {
    private String name;
    private Integer age;
    ...
}
public static final void main(String[] args) {
  List<User> users = new ArrayList<>();
  for(int i=0;i<20;i++) {
    User user = new User();
    user.setName("wjy" + i);
    user.setAge(i);
    users.add(user);
  }
  
  List<String> names = BeanUtil.getPropertys(users,"name",String.class);
  System.out.println(names);  // 输出结果:["wjy0","wjy1","wjy2","wjy3",......]
  
  List<JSONObject> jsonList = BeanUtil.beanToJSON(users,"name","age"); //属性名没有，则默认为该实体类全部属性
  System.out.println(jsonList); //输出结果:[{"name":"wjy0","age":0},{"name":"wjy1","age":1},......]
  
}
```

Table类提供了一个表结构(三元组)的数据结构
```
用法:
Table<String,Integer,String> table = new Table<>();
table.put("wjy1",21,"物联网工程师");
table.put("wjy2",21,"物联网工程师");
table.put("wjy3",22,"物联网工程师");
table.put("wjy4",23,"物联网工程师");
table.put("wjy5",24,"物联网工程师");
System.out.println(table);//运行结果:Table{{key=wjy3, mid=22, value=物联网工程师},{key=wjy4, mid=23, value=物联网工程师},...}

//记录存在
System.out.println("--------记录存在------------");
Table.TableEntry wjy1 = table.get("wjy1");
System.out.println(wjy1);//运行结果:TableEntry{key=21, mid=null, value=物联网工程师}

//记录不存在
System.out.println("--------记录不存在------------");
Table.TableEntry wjy = table.get("wjy");
System.out.println("记录不存在的返回值:" + wjy); //运行结果:记录不存在的返回值:null

//遍历表中全部记录
Set<Table.TableEntry<String,Integer,String>> entries = table.entrySet();
System.out.println("---------遍历表中的全部记录-----------------");
for(Table.TableEntry<String,Integer,String> tableEntry : entries) {
    System.out.println(tableEntry);//运行结果:TableEntry{key=wjy5, mid=24, value=物联网工程师}
                                          
}
```
ObjectConvert类提供了不同对象之间的数据拷贝(拷贝规则: 类中成员属性的类型和成员属性名相同的之间相互拷贝)
```$xslt
//将fromObj对象的数据拷贝到toClazz里面
public static <T> T convert(Class<T> toClazz, Object fromObj)

//将fromObjList对象的数据拷贝到另一个List中
public static <T> List<T> listConvert(Class<T> toClazz, List<?> fromObjList)

demo类:
class FromObj {

    private String attr1;
    
    private Long attrId;
    
    ....
}

class ToClazz {

    private String attr1;
    
    private Long attrId;
    
    private String attr2;
    
    ...
}

使用方法
FromObj fromObj = new FromObj();
fromObj.setAttr1("魏剑源");
fromObj.setAttrId(1L);

ToClazz toObj = convert(ToClazz.class,fromObj);
System.out.println(toObj);  //ToClazz:{attr1:"魏剑源",attrId:1,attr2:null}

```
  
