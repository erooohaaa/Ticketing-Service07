package dao;

import config.DatabaseConfig;
import models.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public boolean createEvent(String name, String location, String description, String category, double price, int tickets, Date date) {
        String sql = "INSERT INTO events (event_name, location, description, category, price, available_tickets, event_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, tickets);
            pstmt.setDate(7, date);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("SELECT setval('events_event_id_seq', (SELECT MAX(event_id) FROM events) + 1)");
                }
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEvent(int id, String name, String location, String description, String category, double price, int tickets, Date date) {
        String sql = "UPDATE events SET event_name = ?, location = ?, description = ?, category = ?, price = ?, available_tickets = ?, event_date = ? WHERE event_id = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, tickets);
            pstmt.setDate(7, date);
            pstmt.setInt(8, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCategoryNameById(int categoryId) {
        String sql = "SELECT category_name FROM event_categories WHERE category_id = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("category_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY event_id";
        try (Connection con = DatabaseConfig.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("location"),
                        rs.getDate("event_date"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("available_tickets")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            boolean deleted = pstmt.executeUpdate() > 0;

            if (deleted) {
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("SELECT setval('events_event_id_seq', (SELECT MAX(event_id) FROM events) + 1)");
                }
            }

            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
