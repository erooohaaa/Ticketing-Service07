package ui;

import config.DatabaseConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AdminActions {
    public static void viewAllUsers() {
        String sql = "SELECT * FROM users";
        try (Connection con = DatabaseConfig.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            System.out.println("All Registered Users:");
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("user_id") + ", Username: " + rs.getString("username") + ", Email: " + rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching users!");
            e.printStackTrace();
        }
    }

    public static void viewAllAdmins() {
        String sql = "SELECT * FROM admins";
        try (Connection con = DatabaseConfig.getConnection();PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            System.out.println("All Registered Admins:");
            while (rs.next()) {
                System.out.println("Admin ID: " + rs.getInt("admin_id") + ", Username: " + rs.getString("username") + ", Permissions: " + rs.getString("permissions"));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching admins!");
            e.printStackTrace();
        }
    }

    public static void deleteUser(Scanner scanner) {
        System.out.println("Enter User ID to delete:");
        int userId = scanner.nextInt();

        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection con = DatabaseConfig.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting user!");
            e.printStackTrace();
        }
    }

    public static void deleteAdmin(Scanner scanner) {
        System.out.println("Enter Admin ID to delete:");
        int adminIdToDelete = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter your Admin ID:");
        int currentAdminId = scanner.nextInt();
        scanner.nextLine();

        String sqlCheck = "SELECT permissions FROM admins WHERE admin_id = ?";
        String sqlDelete = "DELETE FROM admins WHERE admin_id = ?";

        try (Connection con = DatabaseConfig.getConnection();PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck); PreparedStatement pstmtDelete = con.prepareStatement(sqlDelete)) {
            pstmtCheck.setInt(1, adminIdToDelete);
            ResultSet rsToDelete = pstmtCheck.executeQuery();

            if (rsToDelete.next()) {
                String permissionsToDelete = rsToDelete.getString("permissions");

                pstmtCheck.setInt(1, currentAdminId);
                ResultSet rsCurrent = pstmtCheck.executeQuery();

                if (rsCurrent.next()) {
                    String currentPermissions = rsCurrent.getString("permissions");

                    if (comparePermissions(currentPermissions, permissionsToDelete) >= 0) {
                        pstmtDelete.setInt(1, adminIdToDelete);
                        int rowsAffected = pstmtDelete.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Admin deleted successfully!");
                        } else {
                            System.out.println("No admin found with the given ID.");
                        }
                    } else {
                        System.out.println("You cannot delete an admin with higher permissions.");
                    }
                } else {
                    System.out.println("Your admin ID is invalid.");
                }
            } else {
                System.out.println("Admin ID to delete is invalid.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting admin!");
            e.printStackTrace();
        }
    }

    private static int comparePermissions(String current, String target) {
        String[] levels = {"read_only", "edit", "full_access"};

        int currentLevel = -1, targetLevel = -1;

        for (int i = 0; i < levels.length; i++) {
            if (levels[i].equals(current)) {
                currentLevel = i;
            }
            if (levels[i].equals(target)) {
                targetLevel = i;
            }
        }

        return Integer.compare(currentLevel, targetLevel);
    }

}