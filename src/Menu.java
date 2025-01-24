import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class Menu {
    public static void displayMainMenu() {
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
                case 1 -> UserManager.registerUser(scanner);
                case 2 -> AdminManager.registerAdmin(scanner);
                case 3 -> UserManager.loginUser(scanner);
                case 4 -> AdminManager.loginAdmin(scanner);
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

// User management class
class UserManager {
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
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during user login!");
            e.printStackTrace();
        }
    }
}
