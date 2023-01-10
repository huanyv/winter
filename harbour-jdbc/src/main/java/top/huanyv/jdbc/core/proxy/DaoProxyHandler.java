package top.huanyv.jdbc.core.proxy;

import top.huanyv.jdbc.annotation.Delete;
import top.huanyv.jdbc.annotation.Insert;
import top.huanyv.jdbc.annotation.Select;
import top.huanyv.jdbc.annotation.Update;
import top.huanyv.jdbc.core.SqlContext;
import top.huanyv.jdbc.core.SqlContextFactory;
import top.huanyv.jdbc.handler.type.SqlTypeHandler;
import top.huanyv.jdbc.handler.type.SqlTypeHandlerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * @author admin
 * @date 2022/7/23 15:05
 */
public class DaoProxyHandler implements InvocationHandler {
    /*
        TODO
        抽象此类
        两个实现类，一个为实现DAO接口，一个代理DAO类
            代理DAO：当方法上有CRUD注解时，使用注解，不执行方法体：反之，执行方法体
        BaseDao<?>的接口实现在抽象的类中，方便两个实现类公共调用
     */

    public DaoProxyHandler() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        if (method.isAnnotationPresent(Select.class)) {
            return doSelect(method, args);
        }
        Update update = method.getAnnotation(Update.class);
        if (update != null) {
            return doUpdate(update.value(), method, args);
        }
        Insert insert = method.getAnnotation(Insert.class);
        if (insert != null) {
            return doUpdate(insert.value(), method, args);
        }
        Delete delete = method.getAnnotation(Delete.class);
        if (delete != null) {
            return doUpdate(delete.value(), method, args);
        }
        return null;
    }

    public Object doSelect(Method method, Object[] args) throws SQLException {
        String sql = method.getAnnotation(Select.class).value();
        // TODO 改成组合模式
        SqlTypeHandler sqlTypeHandler = SqlTypeHandlerFactory.getSqlTypeHandler(method);
        return sqlTypeHandler.handle(sql, args, method);
    }

    public int doUpdate(String sql, Method method, Object[] args) throws SQLException {
        SqlContext sqlContext = SqlContextFactory.getSqlContext();
        return sqlContext.update(sql, args);
    }

}