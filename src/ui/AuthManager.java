package ui;

import config.DatabaseConfig;
import dao.TicketDAOImpl;
import org.mindrot.jbcrypt.BCrypt;
import services.TicketService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AuthManager {

    public static void registerUser(Scanner scanner) {
        System.out.print("Enter new username:");
        String username = scanner.nextLine().trim();

        System.out.print("Enter email:");
        String email = scanner.nextLine().trim();

        System.out.print("Enter new password:");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Registration failed. Fields cannot be empty.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ User registered successfully.");
            } else {
                System.out.println("❌ Registration failed.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error occurred during user registration.");
            e.printStackTrace();
        }
    }

    public static void login(Scanner scanner) {
        System.out.print("Enter username:");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password:");
        String password = scanner.nextLine().trim();

        String sqlAdmin = "SELECT password FROM admins WHERE username = ?";
        String sqlUser = "SELECT password FROM users WHERE username = ?";

        try (Connection con = DatabaseConfig.getConnection()) {
            // Проверяем среди админов
            try (PreparedStatement pstmt = con.prepareStatement(sqlAdmin)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        System.out.println("✅ Login successful! Welcome, Admin " + username);
                        AdminDashboard.displayAdminMenu(scanner);  // Запуск панели администратора
                        return;
                    } else {
                        System.out.println("❌ Incorrect password.");
                        return;
                    }
                }
            }

            // Проверяем среди пользователей
            try (PreparedStatement pstmt = con.prepareStatement(sqlUser)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        System.out.println("✅ Login successful! Welcome, User " + username);
                        // Используем TicketDAOImpl.getInstance() вместо new TicketDAOImpl()
                        UserDashboard.displayUserMenu(scanner, new TicketService(TicketDAOImpl.getInstance()));
                        return;
                    } else {
                        System.out.println("❌ Incorrect password.");
                        return;
                    }
                }
            }

            // Если не нашли ни там, ни там
            System.out.println("❌ User not found.");

        } catch (Exception e) {
            System.out.println("❌ Error occurred during login.");
            e.printStackTrace();
        }
    }
}
