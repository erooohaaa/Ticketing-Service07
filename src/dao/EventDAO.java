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
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, tickets);
            pstmt.setDate(7, date);
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

    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void renumberEvents() {
        String updateSql = "WITH updated_events AS ( " +
                "  SELECT event_id, ROW_NUMBER() OVER (ORDER BY event_id) AS new_id " +
                "  FROM events " +
                ") " +
                "UPDATE events e " +
                "SET event_id = u.new_id " +
                "FROM updated_events u " +
                "WHERE e.event_id = u.event_id";
        try (Connection con = DatabaseConfig.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetAutoIncrement() {
        String resetSql = "ALTER SEQUENCE events_event_id_seq RESTART WITH 1";
        try (Connection con = DatabaseConfig.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(resetSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
