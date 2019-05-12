package com.example.mybatis;

import java.sql.*;

public class JdbcConfig {
    private static String userName = "root";
    private static String password = "root";
    private static String url = "jdbc:mysql://localhost:3306/mybatis";
    private static String driver = "com.mysql.jdbc.Driver";

    public void init() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        // 注意：最后打开的最先关闭
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
                ps = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询方法
     * <p>
     * sql: 要执行的sql语句 <br/>
     * handler：自定义接口 obj：可变参数列表 <br/>
     */
    public <T> T excuteQuery(String sql, ResultSetHandler<T> handler, Object... obj) {
        Connection conn = getConnection(); // 获得连接
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 创建PreparedStatement对象
            ps = conn.prepareStatement(sql);
            // 为查询语句设置参数
            setParameter(ps, obj);
            // 获得ResultSet结果集
            rs = ps.executeQuery();
            // 返回对象
            return handler.callback(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            close(rs, ps, conn);
        }
        return null;
    }


    /**
     * 查询方法 (支持jdbc事务)
     * <p>
     * sql: 要执行的sql语句 <br/>
     * handler：自定义接口 obj：可变参数列表 <br/>
     */
    public <T> T excuteQuery(Connection connection, String sql, ResultSetHandler<T> handler, Object... obj) {
        Connection conn = connection; // 获得连接
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 创建PreparedStatement对象
            ps = conn.prepareStatement(sql);
            // 为查询语句设置参数
            setParameter(ps, obj);
            // 获得ResultSet结果集
            rs = ps.executeQuery();
            // 返回对象
            return handler.callback(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            close(rs, ps, conn);
        }
        return null;
    }


    /**
     * 增加、修改、删除,的方法
     * <p>
     * obj: 可变参数列表
     */
    public int excuteUpdate(String sql, Object... obj) {
        Connection conn = getConnection(); // 获得连接
        PreparedStatement ps = null;
        int rows = 0;
        try {
            // 创建PreparedStatement对象
            ps = conn.prepareStatement(sql);
            // 为查询语句设置参数
            setParameter(ps, obj);
            // 获得受影响的行数
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            close(null, ps, conn);
        }
        return rows;
    }

    private void setParameter(PreparedStatement ps, Object... obj) throws SQLException {
        if (obj != null && obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
        }
    }
}
