package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "0000";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
    }
}