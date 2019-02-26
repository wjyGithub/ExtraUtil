package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/2/26 14:04
 */
public class ObjectConvert {


    /**
     * 批量转换
     * @param toClazz
     * @param fromObjList
     * @param <T>
     * @return
     */
    public static <T> List<T> listConvert(Class<T> toClazz, List<?> fromObjList) {
        List<T> listT = new ArrayList<>();
        for(Object obj : fromObjList) {
            T objT = convert(toClazz, obj);
            listT.add(objT);
        }
        return listT;
    }

    /**
     * 对象之间转换
     * 转换规则:将两个对象字段中，字段类型和字段名相同的进行数据赋值
     * @param toClazz 目标对象
     * @param fromObj 待转换的对象
     * @param <T>
     * @return
     */
    public static <T> T convert(Class<T> toClazz, Object fromObj) {
        /**
         * key: 类型+属性名(java.lang.String_id)
         * value: 数据
         */
        Map<String,Object> fromObjBufferMap = initToMap(fromObj);

        try {
            Object targetObj = toClazz.newInstance();
            Field[] fields = toClazz.getDeclaredFields();

            for (Field field : fields) {
                System.out.println(field);
                field.setAccessible(true);
                String key = getKey(field);
                Object obj = fromObjBufferMap.get(key);
                field.set(targetObj,obj);
            }
            return (T) targetObj;
        } catch (InstantiationException | IllegalAccessException e) {
        }
        return null;

    }

    /**
     * 将对象初始化为Map
     * @param obj
     * @return
     */
    private static Map<String,Object> initToMap(Object obj) {
        Map<String,Object> bufferMap = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {}
            bufferMap.put(getKey(field),fieldValue);
        }
        return bufferMap;
    }

    /**
     * 获取buffer的Key
     * @param field
     * @return
     */
    private static String getKey(Field field) {
        return field.getType().getTypeName() + "_" + field.getName();
    }

}

