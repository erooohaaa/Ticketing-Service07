package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    private static Connection connection;

    private DatabaseConfig() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("❌ Failed to connect to the database.");
            }
        }
        return connection;
    }

    private static boolean isClosed(Connection conn) {
        try {
            return conn == null || conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}