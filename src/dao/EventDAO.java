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

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id, event_name, location, event_date, description, category, price, available_tickets FROM events ORDER BY event_id";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("location"),
                        rs.getDate("event_date"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("available_tickets"),
                        null,
                        0
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getFullEventDetails(int eventId) {
        String sql = """
            SELECT e.event_id, e.event_name, e.location, e.event_date, e.description, 
                   COALESCE(c.category_name, 'Unknown') AS category, 
                   e.price, e.available_tickets,
                   COALESCE(COUNT(t.ticket_id), 0) AS sold_tickets
            FROM events e
            LEFT JOIN event_categories c ON e.category = c.category_name
            LEFT JOIN tickets t ON e.event_id = t.event_id
            WHERE e.event_id = ?
            GROUP BY e.event_id, e.event_name, e.location, e.event_date, e.description, 
                     c.category_name, e.price, e.available_tickets;
        """;

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("location"),
                        rs.getDate("event_date"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("available_tickets"),
                        null,
                        rs.getInt("sold_tickets")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
