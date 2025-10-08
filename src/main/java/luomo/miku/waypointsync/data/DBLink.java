package luomo.miku.waypointsync.data;

import java.sql.*;

import luomo.miku.waypointsync.MyMod;

public class DBLink {

    public static void connectDB() {
        String dbURL = "jdbc:sqlite:waypointsync.db";

        try (Connection connection = DriverManager.getConnection(dbURL)) {
            MyMod.LOG.info("Connected to database");

            createTable(connection);
            insertData(connection, "test", "test@email.com");
            queryData(connection);

        } catch (SQLException e) {
            MyMod.LOG.error("连接数据库或执行操作时发生严重错误，MOD的部分功能可能不可用", e);
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT NOT NULL, "
            + "email TEXT NOT NULL UNIQUE)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            MyMod.LOG.info("表创建成功（或已存在）。");
        }
    }

    private static void insertData(Connection conn, String name, String email) throws SQLException {
        // 使用PreparedStatement防止SQL注入，并安全地插入参数
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            MyMod.LOG.info("数据插入成功。");
        }
    }

    private static void queryData(Connection conn) throws SQLException {
        String sql = "SELECT id, name, email FROM users";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            MyMod.LOG.info("查询结果：");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                MyMod.LOG.info("ID: {}, Name: {}, Email: {}", id, name, email);
            }
        }
    }
}
