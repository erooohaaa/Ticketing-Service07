import eventmanagement.EventManagementMain;
import java.util.Scanner;

public class AdminDashboard {
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
                case 1 -> AdminActions.viewAllUsers();
                case 2 -> AdminActions.viewAllAdmins();
                case 3 -> AdminActions.deleteUser(scanner);
                case 4 -> AdminActions.deleteAdmin(scanner);
                case 5 -> EventManagementMain.main(new String[]{});
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
