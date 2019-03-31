package util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jianyuan.wei@hand-china.com
 * @date 2018/11/5 16:23
 */
public class BeanUtilTest {

    @Test
    public void beanUtilT() {

        List<User> users = new ArrayList<>();
        for(int i=0;i<20;i++) {
            User user = new User();
            user.setName("wjy" + i);
            user.setAge(i);
            users.add(user);
        }

        List<String> names = BeanUtil.getPropertys(users,"name",String.class);
        System.out.println(names);
        // 输出结果:["wjy0","wjy1","wjy2","wjy3",......]

        //属性名没有，则默认为该实体类全部属性
        List<JSONObject> jsonList = BeanUtil.beanToJson(users,"name","age");
        System.out.println(jsonList);
        //输出结果:[{"name":"wjy0","age":0},{"name":"wjy1","age":1},......]
    }

    public static class User {

        private String name;

        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}