package world.xuewei.fast.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean 工具类
 *
 * @author XUEW
 * @since 2023/11/7 18:37
 */
public class BeanUtils {

    /**
     * 将对象转为 Map
     *
     * @param bean 对象
     * @return Map 映射
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(bean);
                map.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
