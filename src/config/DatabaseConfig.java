package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    private static Connection connection;

    private DatabaseConfig() {}  // Закрываем возможность создания экземпляра класса

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConfig.class) {  // Блокировка для многопоточной безопасности
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("❌ Failed to connect to the database.");
                    }
                }
            }
        }
        return connection;
    }
}
