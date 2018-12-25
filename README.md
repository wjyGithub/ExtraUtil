# ExtraUtil

注意:github上编辑文件需要在该行末尾添加两个空格

本工程主要提供了一个List的包装工具类，能够弥补List不能够默认初始化的不足

用法：  
  ListWrap<Integer> listWrap = new ListWrap<>(0);  
  listWrap.add(4,4);

其中：add用于向List中指定的位置添加元素，如上,向List的索引为4的位置添加了一个元素4,  
其未为指定元素的索引位置,使用默认值0,进行初始化

该List包装类用途之一：主要是在对前端返回图表类数据结构时，有些时候需要对查询数据时，没有数据的位置补0,  
就可以通过该工具类方便实现



BeanUtil类提供了对实体类的相关操作方法，目前暂时提供的方法有如下：
```
   /**
     * 获取List对象中指定属性名的属性值
     * @param objs 
     * @param name 属性名
     * @param propertyType 属性类型
     * @param <T>
     * @return
     */
List<T> BeanUtil.getPropertys（List<? extends Object> objs,String name,Class<T> propertyType）
    /**
     * 将实体类对象转成JSONObject,key为属性名,value为对象属性的值
     * class User {
     *
     *    private String name;
     *
     *    private Integer age;
     * }
     * {
     *     "name":"wjy",
     *     "age":2
     * }
     * @param objs
     * @param propertyNames 属性名
     * @return
     */
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
  
