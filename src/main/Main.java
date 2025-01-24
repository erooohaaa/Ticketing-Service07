package main;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.Scanner;
import eventmanagement.EventManagementMain;

public class Main {
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "0000";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register User");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");

            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerUser(scanner);
                case 2 -> loginUser(scanner);
                case 3 -> loginAdmin(scanner);
                case 4 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username or email already exists. Try again.");
        } catch (Exception e) {
            System.out.println("Error occurred during user registration!");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                System.out.println("User login successful! Welcome, " + username);
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during user login!");
        }
    }

    private static void loginAdmin(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM admins WHERE username = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                System.out.println("Admin login successful! Welcome, " + username);
                adminDashboard(scanner);
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during admin login!");
        }
    }

    private static void adminDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. View All Users");
            System.out.println("2. View All Admins");
            System.out.println("3. Delete a User");
            System.out.println("4. Delete an Admin");
            System.out.println("5. Manage Events");
            System.out.println("6. Logout");

            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> viewAllAdmins();
                case 3 -> deleteUser(scanner);
                case 4 -> deleteAdmin(scanner);
                case 5 -> EventManagementMain.main(new String[]{});
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewAllUsers() {
        String sql = "SELECT * FROM users";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("All Registered Users:");
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("user_id") +
                        ", Username: " + rs.getString("username") +
                        ", Email: " + rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching users!");
        }
    }

    private static void viewAllAdmins() {
        String sql = "SELECT * FROM admins";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("All Registered Admins:");
            while (rs.next()) {
                System.out.println("Admin ID: " + rs.getInt("admin_id") +
                        ", Username: " + rs.getString("username") +
                        ", Permissions: " + rs.getString("permissions"));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching admins!");
        }
    }

    private static void deleteUser(Scanner scanner) {
        System.out.println("Enter User ID to delete:");
        int userId = scanner.nextInt();

        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting user!");
        }
    }

    private static void deleteAdmin(Scanner scanner) {
        System.out.println("Enter Admin ID to delete:");
        int adminIdToDelete = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter your Admin ID:");
        int currentAdminId = scanner.nextInt();
        scanner.nextLine();

        String sqlCheck = "SELECT permissions FROM admins WHERE admin_id = ?";
        String sqlDelete = "DELETE FROM admins WHERE admin_id = ?";

        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck);
             PreparedStatement pstmtDelete = con.prepareStatement(sqlDelete)) {

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
