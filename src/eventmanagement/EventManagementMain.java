package eventmanagement;

import services.EventService;
import utils.InputUtils;
import models.Event;
import java.sql.Date;

public class EventManagementMain {
    private static final EventService eventService = new EventService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Event Management ---");
            System.out.println("1. Add Event");
            System.out.println("2. List Events");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Exit");

            int choice = InputUtils.getInt("Choose an option: ");

            switch (choice) {
                case 1 -> addEvent();
                case 2 -> listEvents();
                case 3 -> editEvent();
                case 4 -> deleteEvent();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addEvent() {
        String name = InputUtils.getString("Enter event name: ");
        String location = InputUtils.getString("Enter event location: ");
        String description = InputUtils.getString("Enter event description: ");
        int categoryId = InputUtils.getInt("Enter event category ID (1-6): ");
        double price = InputUtils.getDouble("Enter event price: ");
        int tickets = InputUtils.getInt("Enter available tickets: ");
        Date date = InputUtils.getDate("Enter event date");

        if (eventService.addEvent(name, location, description, categoryId, price, tickets, date)) {
            System.out.println("Event added successfully.");
        } else {
            System.out.println("Failed to add event.");
        }
    }

    private static void listEvents() {
        System.out.println("\n--- Events ---");
        for (Event event : eventService.listEvents()) {
            System.out.println(event.getId() + ". " + event.getName() + " - " + event.getLocation() +
                    " (" + event.getDate() + "), " + event.getCategory() + "," + event.getPrice() + "$" +
                    ", Tickets: " + event.getAvailableTickets());
        }
    }

    private static void editEvent() {
        int id = InputUtils.getInt("Enter event ID to edit: ");
        String name = InputUtils.getString("Enter new event name: ");
        String location = InputUtils.getString("Enter new event location: ");
        String description = InputUtils.getString("Enter new event description: ");
        int categoryId = InputUtils.getInt("Enter new event category ID (1-6): ");
        double price = InputUtils.getDouble("Enter new event price: ");
        int tickets = InputUtils.getInt("Enter new available tickets: ");
        Date date = InputUtils.getDate("Enter new event date");

        if (eventService.editEvent(id, name, location, description, categoryId, price, tickets, date)) {
            System.out.println("Event updated successfully.");
        } else {
            System.out.println("Failed to update event.");
        }
    }

    private static void deleteEvent() {
        int id = InputUtils.getInt("Enter event ID to delete: ");
        if (eventService.removeEvent(id)) {
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Failed to delete event.");
        }
    }
}
