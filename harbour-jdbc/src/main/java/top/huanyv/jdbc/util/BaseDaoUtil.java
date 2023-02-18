package top.huanyv.jdbc.util;

import top.huanyv.jdbc.annotation.Column;
import top.huanyv.jdbc.annotation.TableId;
import top.huanyv.jdbc.annotation.TableName;
import top.huanyv.jdbc.builder.BaseDao;
import top.huanyv.tools.utils.Assert;
import top.huanyv.tools.utils.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author huanyv
 * @date 2022/9/1 10:21
 */
public class BaseDaoUtil {

    /**
     * 获取 baseDao 的泛型类型 Class
     *
     * @param type 接口或类
     * @return 泛型 Class
     */
    public static Class<?> getType(Type type) {
        Assert.notNull(type, "'type' must not be null.");
        if (type instanceof Class) {
            Class<?> cls = (Class<?>) type;
            if (!cls.isInterface()) {
                Class<?> superclass = cls.getSuperclass();
                if (BaseDao.class.isAssignableFrom(superclass)) {
                    return getType(superclass);
                }
            }
            for (Type genericInterface : cls.getGenericInterfaces()) {
                if (genericInterface instanceof Class) {
                    if (BaseDao.class.isAssignableFrom((Class<?>) genericInterface)) {
                        return getType(genericInterface);
                    }
                }
                if (genericInterface instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) genericInterface).getRawType();
                    if (rawType instanceof Class && BaseDao.class.isAssignableFrom((Class<?>) rawType)) {
                        return getType(genericInterface);
                    }
                }
            }
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (BaseDao.class.equals(parameterizedType.getRawType())) {
                return (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } else {
                return getType(parameterizedType);
            }
        }
        return null;
    }

    /**
     * 获取表id名
     *
     * @param clazz clazz对象
     * @return table id 的字段名
     */
    public static String getTableId(Class<?> clazz) {
        Assert.notNull(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(TableId.class)) {
                Column column = field.getAnnotation(Column.class);
                return column != null ? column.value() : field.getName();
            }
        }
        return "id";
    }

    /**
     * 获取表名
     *
     * @param clazz 类型class
     * @return 表名称
     */
    public static String getTableName(Class<?> clazz) {
        Assert.notNull(clazz);
        TableName tableName = clazz.getAnnotation(TableName.class);
        if (tableName != null) {
            return tableName.value();
        }
        return StringUtil.firstLetterLower(clazz.getSimpleName());
    }

    public static Field getIdField(Class<?> cls) throws NoSuchFieldException {
        Assert.notNull(cls);
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(TableId.class)) {
                return field;
            }
        }
        return cls.getDeclaredField("id");
    }
}
