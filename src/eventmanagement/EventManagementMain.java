package eventmanagement;

import services.EventService;
import java.sql.Date;
import java.util.Scanner;

public class EventManagementMain {
    public static void manageEvents(Scanner scanner) {  // ✅ Передаем scanner из AdminDashboard
        while (true) {
            System.out.println("\nEvent Management:");
            System.out.println("1. View Events");
            System.out.println("2. Add Event");
            System.out.println("3. Edit Event");
            System.out.println("4. Remove Event");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> listEvents();
                case 2 -> addEvent(scanner);
                case 3 -> editEvent(scanner);
                case 4 -> removeEvent(scanner);
                case 5 -> {
                    System.out.println("Returning to Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void addEvent(Scanner scanner) {
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        System.out.print("Enter event location: ");
        String location = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();
        System.out.print("Enter event category: ");
        String category = scanner.nextLine();
        System.out.print("Enter event price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter available tickets: ");
        int tickets = scanner.nextInt();
        System.out.print("Enter event date (YYYY-MM-DD): ");
        Date date = Date.valueOf(scanner.next());

        if (EventService.addEvent(name, location, description, category, price, tickets, date)) {
            System.out.println("✅ Event added successfully.");
        } else {
            System.out.println("❌ Failed to add event.");
        }
    }

    public static void listEvents() {
        System.out.println("\n--- Events ---");
        EventService.listEvents();
    }

    public static void editEvent(Scanner scanner) {
        System.out.print("Enter event ID to edit: ");
        int eventId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new event name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new location: ");
        String newLocation = scanner.nextLine();
        System.out.print("Enter new description: ");
        String newDescription = scanner.nextLine();
        System.out.print("Enter new category: ");
        String newCategory = scanner.nextLine();
        System.out.print("Enter new price: ");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter new available tickets: ");
        int newTickets = scanner.nextInt();
        System.out.print("Enter new event date (YYYY-MM-DD): ");
        Date newDate = Date.valueOf(scanner.next());

        if (EventService.editEvent(eventId, newName, newLocation, newDescription, newCategory, newPrice, newTickets, newDate)) {
            System.out.println("✅ Event updated successfully.");
        } else {
            System.out.println("❌ Failed to update event.");
        }
    }

    public static void removeEvent(Scanner scanner) {
        System.out.print("Enter event ID to remove: ");
        int eventId = scanner.nextInt();
        scanner.nextLine();

        if (EventService.removeEvent(eventId)) {
            System.out.println("✅ Event removed successfully.");
        } else {
            System.out.println("❌ Failed to remove event.");
        }
    }
}
