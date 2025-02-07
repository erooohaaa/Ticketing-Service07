package dao;

import config.DatabaseConfig;
import models.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public boolean addCategory(String categoryName, int seats) {
        String sql = "INSERT INTO event_categories (category_name, seats) VALUES (?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            pstmt.setInt(2, seats);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(String categoryName) {
        String sql = "DELETE FROM event_categories WHERE category_name = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean categoryExists(String categoryName) {
        String sql = "SELECT COUNT(*) FROM event_categories WHERE category_name = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_name, seats FROM event_categories ORDER BY category_name";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("category_name");
                int seats = rs.getInt("seats");
                categories.add(new Category(name, seats));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public int getSeatsForCategory(String categoryName) {
        String sql = "SELECT seats FROM event_categories WHERE category_name = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("seats");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}