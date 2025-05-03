package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URL;

public class Database {
    private static final String DB_NAME = "todo.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Загружаем базу данных из ресурсов
            URL dbUrl = Database.class.getClassLoader().getResource(DB_NAME);
            if (dbUrl == null) {
                throw new RuntimeException("База данных не найдена в ресурсах");
            }
            String url = "jdbc:sqlite:" + dbUrl.getPath();
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            throw new SQLException("Ошибка подключения к БД: " + e.getMessage(), e);
        }
    }
}
