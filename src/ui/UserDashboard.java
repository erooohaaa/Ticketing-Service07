package ui;

import java.util.Scanner;

public class UserDashboard {
    public static void displayUserMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n👤 User Dashboard:");
            System.out.println("1. View Events");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Tickets");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> EventUI.viewEvents();
                case 2 -> EventUI.bookTicket(scanner);
                case 3 -> EventUI.viewUserTickets(scanner);
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
