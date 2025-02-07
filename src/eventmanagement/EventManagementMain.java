package eventmanagement;

import services.EventService;
import utils.InputUtils;
import models.Event;
import java.sql.Date;
import java.util.List;

public class EventManagementMain {
    private static final EventService eventService = new EventService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Event Management ---");
            System.out.println("1. Add Event");
            System.out.println("2. List Events");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. View Event Details");
            System.out.println("6. Exit");

            int choice = InputUtils.getInt("Choose an option: ");

            switch (choice) {
                case 1 -> addEvent();
                case 2 -> listEvents();
                case 3 -> editEvent();
                case 4 -> deleteEvent();
                case 5 -> viewEventDetails();
                case 6 -> {
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
        String category = InputUtils.getString("Enter event category: ");
        double price = InputUtils.getDouble("Enter event price: ");
        int tickets = InputUtils.getInt("Enter available tickets: ");
        Date date = InputUtils.getDate("Enter event date");

        if (eventService.addEvent(name, location, description, category, price, tickets, date)) {
            System.out.println("✅ Event added successfully.");
        } else {
            System.out.println("❌ Failed to add event.");
        }
    }

    private static void listEvents() {
        System.out.println("\n--- Events ---");
        List<Event> events = eventService.listEvents();
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        for (Event event : events) {
            System.out.println(event.getId() + ". " + event.getName() + " - " + event.getLocation() +
                    " (" + event.getDate() + "), " + event.getCategory() + ", " + event.getPrice() + "$" +
                    ", Tickets: " + event.getAvailableTickets());
        }
    }

    private static void editEvent() {
        int id = InputUtils.getInt("Enter event ID to edit: ");
        String name = InputUtils.getString("Enter new event name: ");
        String location = InputUtils.getString("Enter new event location: ");
        String description = InputUtils.getString("Enter new event description: ");
        String category = InputUtils.getString("Enter new event category: ");
        double price = InputUtils.getDouble("Enter new event price: ");
        int tickets = InputUtils.getInt("Enter new available tickets: ");
        Date date = InputUtils.getDate("Enter new event date");

        if (eventService.editEvent(id, name, location, description, category, price, tickets, date)) {
            System.out.println("✅ Event updated successfully.");
        } else {
            System.out.println("❌ Failed to update event.");
        }
    }

    private static void deleteEvent() {
        int id = InputUtils.getInt("Enter event ID to delete: ");
        if (eventService.removeEvent(id)) {
            System.out.println("✅ Event deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete event.");
        }
    }

    private static void viewEventDetails() {
        int id = InputUtils.getInt("Enter event ID: ");
        Event event = eventService.getEventDetails(id);
        if (event == null) {
            System.out.println("❌ Event not found.");
            return;
        }

        System.out.println("\n--- Event Details ---");
        System.out.println("ID: " + event.getId());
        System.out.println("Name: " + event.getName());
        System.out.println("Location: " + event.getLocation());
        System.out.println("Date: " + event.getDate());
        System.out.println("Description: " + event.getDescription());
        System.out.println("Category: " + event.getCategory());
        System.out.println("Price: " + event.getPrice() + "$");
        System.out.println("Available Tickets: " + event.getAvailableTickets());
        System.out.println("Tickets Sold: " + event.getSoldTickets());
    }
}