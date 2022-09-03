package top.huanyv.jdbc.core;

import top.huanyv.jdbc.handler.ResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * jdbc模板
 *
 * @author huanyv
 * @date 2022/9/3 20:25
 */
public class JdbcTemplate {

    public JdbcTemplate() {
    }

    public <T> T query(Connection connection, String sql, ResultSetHandler<T> resultSetHandler, Object... args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) {
            ps.setString(i, args[i - 1].toString());
        }
        ResultSet resultSet = ps.executeQuery();
        return resultSetHandler.handle(resultSet);
    }

    public int update(Connection connection, String sql, Object... args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) {
            ps.setString(i, args[i - 1].toString());
        }
        return ps.executeUpdate();
    }

}
