package ui;

import services.AdminService;
import eventmanagement.EventManagementMain;
import java.util.Scanner;

public class AdminDashboard {
    public static void displayAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. View All Users");
            System.out.println("2. View All Admins");
            System.out.println("3. Add New Admin");
            System.out.println("4. Manage Events");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> AdminService.viewAllUsers();
                case 2 -> AdminService.viewAllAdmins();
                case 3 -> AdminManager.registerAdmin(scanner);
                case 4 -> {
                    EventManagementMain.manageEvents(scanner);
                    System.out.println("\nReturning to Admin Dashboard...");
                }
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
