package services;

import config.DatabaseConfig;
import models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    // Add a category to the database
    public boolean addCategory(String categoryName) {
        String sql = "INSERT INTO event_categories (category_name) VALUES (?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Remove a category from the database
    public boolean removeCategory(String categoryName) {
        String sql = "DELETE FROM event_categories WHERE category_name = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            return pstmt.executeUpdate() > 0;  // Fixed: Remove from DB, not just a list

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all categories from the database
    public static List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_name FROM event_categories ORDER BY category_name";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getString("category_name"), rs.getInt("seats")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
