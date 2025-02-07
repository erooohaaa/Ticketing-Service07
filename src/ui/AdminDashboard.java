package ui;

import java.util.Scanner;
import services.AdminService;

public class AdminDashboard {
    private final AdminService adminService;
    public AdminDashboard(AdminService adminService) {  // 🆕 Добавлен конструктор
        this.adminService = adminService;
    }


    public static void displayDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. View All Users");
            System.out.println("2. View All Admins");
            System.out.println("3. Delete a User");
            System.out.println("4. Delete an Admin");
            System.out.println("5. Manage Events");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> AdminService.viewAllUsers();  // 🔄 Заменено: теперь вызываем AdminService
                case 2 -> AdminService.viewAllAdmins();
                case 3 -> AdminService.deleteUser(scanner);
                case 4 -> AdminService.deleteAdmin(scanner);
                case 5 -> AdminService.manageEvents();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
