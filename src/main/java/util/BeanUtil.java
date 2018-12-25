package util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供对Bean的操作
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/24 20:15
 */
@Slf4j
public class BeanUtil {

    private BeanUtil(){}

    /**
     * 获取List对象中指定属性名的属性值
     * @param objs
     * @param name 属性名
     * @param propertyType 属性类型
     * @param <T>
     * @return
     */
    public static final <T extends Object> List<T> getPropertys(List<? extends Object> objs,String name,Class<T> propertyType) {
        List<T> list = new ArrayList<>();
        for(Object obj : objs) {
            try {
                Class cls = obj.getClass();
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                Object o = field.get(obj);
                if(o != null) {
                    T propertyValue = propertyType.cast(o);
                    list.add(propertyValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.debug("反射失败:{}",e.getMessage());
            }
        }
        return list;
    }

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
     * @param propertyNames
     * @return
     */
    public static final List<JSONObject> beanToJSON(List<? extends Object> objs,String... propertyNames) {

        List<JSONObject> beanJSON = new ArrayList<>();
        if(propertyNames.length == 0) {
            for(Object obj : objs) {
                JSONObject fieldJSON = new JSONObject();
                Class<?> cls = obj.getClass();
                Field[] fields = cls.getDeclaredFields();
                if(fields.length != 0) {
                    for(Field field : fields) {
                        try {
                            String name = field.getName();
                            field.setAccessible(true);
                            Object o = field.get(obj);
                            if(o != null) {
                                fieldJSON.put(name,o);
                            }
                        } catch (IllegalAccessException e) {
                            log.debug("反射失败");
                        }
                    }
                    beanJSON.add(fieldJSON);
                }
            }
        }else {
            for(Object obj : objs) {
                JSONObject fieldJSON = new JSONObject();
                Class<?> cls = obj.getClass();
                for(String propertyName :propertyNames) {
                    try {
                        Field field = cls.getDeclaredField(propertyName);
                        field.setAccessible(true);
                            Object o = field.get(obj);
                            if(o != null) {
                                fieldJSON.put(propertyName,o);
                            }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.debug("反射失败");
                    }
                }
                beanJSON.add(fieldJSON);

            }
        }
        return beanJSON;

    }
}
