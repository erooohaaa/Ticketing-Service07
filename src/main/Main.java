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
            System.out.println("2. Register Admin");
            System.out.println("3. Login as User");
            System.out.println("4. Login as Admin");
            System.out.println("5. Manage Events");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerUser(scanner);
                case 2 -> registerAdmin(scanner);
                case 3 -> loginUser(scanner);
                case 4 -> loginAdmin(scanner);
                case 5 -> EventManagementMain.main(args);
                case 6 -> {
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
                // Можно добавить функционал для работы с админ панелью
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during admin login!");
            e.printStackTrace();
        }
    }
}
