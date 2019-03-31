package util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 提供对Bean的操作
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/24 20:15
 */
public class BeanUtil {

    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);
    private BeanUtil(){}

    /**
     * 获取List对象中指定属性名的属性值
     * @param aimObjs
     * @param name 属性名
     * @param propertyType 属性类型
     * @param <T>
     * @return
     */
    public static final <T> List<T> getPropertys(List<?> aimObjs,String name,Class<T> propertyType) {
        List<T> list = new ArrayList<>();
        for(Object aimObj : aimObjs) {
            try {
                list.add(getValueFrom(aimObj,name,propertyType));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("BeanUtil->",e.getStackTrace());
            }
        }
        return list;
    }

    /**
     * 获取指定对象的属性值
     * @param aimObj
     * @param <T>
     * @return
     */
    private static final <T> T getValueFrom(Object aimObj,String name,Class<T> propertyType) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = aimObj.getClass();
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        Object value = field.get(aimObj);
        return value != null ? propertyType.cast(value) : null;
    }

    /**
     * 将实体类对象转成JSONObject,key为属性名,value为对象属性的值
     * 如果List中某对象的所有值全部为空，则该对象存放一个空json
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
    public static final List<JSONObject> beanToJson(List<?> objs, String... propertyNames) {

        List<JSONObject> jsonList = new ArrayList<>();
        if(propertyNames.length == 0) {
            for(Object obj : objs) {
                JSONObject fieldJson = new JSONObject();
                try {
                    fieldJson = fieldToJson(obj);
                }catch (IllegalAccessException e) {
                    log.error("BeanUtil->",e.getStackTrace());
                }
                jsonList.add(fieldJson);
            }
        }else {
            for(Object obj : objs) {
                JSONObject fieldJson = new JSONObject();
                try {
                    fieldJson = jsonFromAware(obj, Arrays.asList(propertyNames));
                }catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("BeanUtil->",e.getStackTrace());
                }
                jsonList.add(fieldJson);
            }
        }
        return jsonList;
    }

    /**
     * 将已知的属性值转成json
     * @param aimObj
     * @param propertyNames
     * @return
     */
    private static final JSONObject jsonFromAware(Object aimObj, List<String> propertyNames) throws NoSuchFieldException, IllegalAccessException {
        JSONObject aimJson = new JSONObject();
        if(aimObj != null) {
            Class<?> clazz = aimObj.getClass();
            for(String propertyName: propertyNames) {
                Field field = clazz.getDeclaredField(propertyName);
                setValue(field,aimObj,aimJson);
            }
        }
        return aimJson;
    }

    /**
     * 将对象中的属性转成json格式,key:属性名,value: 属性值
     * 如果该字段的属性值为空，则返回null
     * @return
     */
    private static final JSONObject fieldToJson(Object aimObj) throws IllegalAccessException {
        JSONObject aimJson = new JSONObject();
        List<Field> fields = getFields(aimObj);
        for(Field field : fields) {
            setValue(field,aimObj,aimJson);
        }
        return aimJson;
    }

    /**
     * 将field已key,value的形式put到json中
     * @param field
     * @param aimObj
     * @param aimJson
     */
    private static final void setValue(Field field,Object aimObj,JSONObject aimJson) throws IllegalAccessException {
        String name = field.getName();
        field.setAccessible(true);
        Object value = field.get(aimObj);
        if(value != null) {
            aimJson.put(name,value);
        }
    }

    /**
     * 获取一个对象的全部属性列表
     * @param aimObj
     * @return
     */
    private static final List<Field> getFields(Object aimObj) {
        if(aimObj != null) {
            Class<?> clazz = aimObj.getClass();
            return Arrays.asList(clazz.getDeclaredFields());
        }
        return new ArrayList<>();
    }

}
