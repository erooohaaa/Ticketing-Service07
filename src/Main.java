import java.sql.*;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class Main {
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "0000";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register User");
            System.out.println("2. Register Admin");
            System.out.println("3. Login as User");
            System.out.println("4. Login as Admin");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerUser(scanner);
                case 2 -> registerAdmin(scanner);
                case 3 -> loginUser(scanner);
                case 4 -> loginAdmin(scanner);
                case 5 -> {
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
            e.printStackTrace();
        }
    }

    private static void registerAdmin(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            System.out.println("Admin registered successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Admin username already exists. Try again.");
        } catch (Exception e) {
            System.out.println("Error occurred during admin registration!");
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private static void adminDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. View All Users");
            System.out.println("2. Delete a User");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> deleteUser(scanner);
                case 3 -> {
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
                System.out.println("User ID: " + rs.getInt("user_id") + ", Username: " + rs.getString("username") + ", Email: " + rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching users!");
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}