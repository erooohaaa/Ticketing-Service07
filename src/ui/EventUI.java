package ui;

import services.EventService;
import java.util.Scanner;

public class EventUI {
    public static void viewEvents() {
        System.out.println("📅 Viewing all available events...");
        EventService.viewEvents();
    }

    public static void bookTicket(Scanner scanner) {
        System.out.println("🎟 Booking a ticket...");
        System.out.print("Enter event ID: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        EventService.bookTicket(eventId, username);
    }

    public static void viewUserTickets(Scanner scanner) {
        System.out.println("🎫 Viewing your booked tickets...");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        EventService.viewUserTickets(username);
    }

    public static void manageEvents(Scanner scanner) {
        while (true) {
            System.out.println("\n🎭 Event Management:");
            System.out.println("1. View Events");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Tickets");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> viewEvents();
                case 2 -> bookTicket(scanner);
                case 3 -> viewUserTickets(scanner);
                case 4 -> {
                    System.out.println("Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
