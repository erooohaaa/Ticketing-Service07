import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class AdminManager {
    public static void registerAdmin(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.nextLine();

        if (isAdminUsernameExists(username)) {
            System.out.println("Admin username already exists. Try again.");
            return;
        }

        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        System.out.println("Select permissions level:");
        System.out.println("1. Full Access");
        System.out.println("2. Edit");
        System.out.println("3. Read Only");
        int permissionChoice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String permissions = getPermissionsFromChoice(permissionChoice);
        if (permissions == null) {
            System.out.println("Invalid permissions choice. Registration failed.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO admins (username, password, permissions) VALUES (?, ?, ?)";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, permissions);
            pstmt.executeUpdate();
            System.out.println("Admin registered successfully with permissions: " + permissions);
        } catch (Exception e) {
            System.out.println("Error occurred during admin registration!");
            e.printStackTrace();
        }
    }

    public static void loginAdmin(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM admins WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                System.out.println("Admin login successful! Welcome, " + username);
                AdminDashboard.displayDashboard(scanner);
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during admin login!");
            e.printStackTrace();
        }
    }

    private static boolean isAdminUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM admins WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            System.out.println("Error occurred while checking admin username!");
            e.printStackTrace();
        }
        return false;
    }

    private static String getPermissionsFromChoice(int choice) {
        return switch (choice) {
            case 1 -> "full_access";
            case 2 -> "edit";
            case 3 -> "read_only";
            default -> null;
        };
    }
}
