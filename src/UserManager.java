import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserManager {
    public static void registerUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (Exception e) {
            System.out.println("Error occurred during user registration!");
            e.printStackTrace();
        }
    }

    public static void loginUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                System.out.println("User login successful! Welcome, " + username);
                displayUserMenu(scanner, username);
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during user login!");
            e.printStackTrace();
        }
    }

    private static void displayUserMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    viewProfile(username);
                    break;
                case "2":
                    changePassword(scanner, username);
                    break;
                case "3":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void viewProfile(String username) {
        String sql = "SELECT username, email FROM users WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nProfile Information:");
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Email: " + rs.getString("email"));
            } else {
                System.out.println("User not found.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving profile");
            e.printStackTrace();
        }
    }

    private static void changePassword(Scanner scanner, String username) {
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            System.out.println("Password changed successfully!");
        } catch (Exception e) {
            System.out.println("Error changing password.");
            e.printStackTrace();
        }
    }
}
