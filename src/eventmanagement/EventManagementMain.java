package eventmanagement;

import models.Category;
import services.EventService;
import services.CategoryService;
import utils.InputUtils;
import models.Event;
import java.sql.Date;
import java.util.List;

public class EventManagementMain {
    private static final CategoryService categoryService = new CategoryService();
    private static EventService eventService = new EventService();

    public EventManagementMain() {
        this.eventService = new EventService();
    }

    public static void start() {
        while (true) {
            System.out.println("\n--- Event Management ---");
            System.out.println("1. Add Event");
            System.out.println("2. List Events");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. View Event Details");
            System.out.println("6. Manage Categories");
            System.out.println("7. Exit");

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

        System.out.printf("%-5s | %-20s | %-15s | %-10s | %-10s | %-7s | %-10s%n",
                "ID", "Name", "Location", "Date", "Category", "Price", "Tickets");
        System.out.println("--------------------------------------------------------------------------------");


        for (Event event : events) {
            System.out.printf("%-5d | %-20s | %-15s | %-10s | %-10s | %-7.2f | %-10d%n",
                    event.getId(), event.getName(), event.getLocation(),
                    event.getDate(), event.getCategory(), event.getPrice(), event.getAvailableTickets());
        }
    }

    private static void editEvent() {
        int id = InputUtils.getInt("Enter event ID to edit: ");
        Event updatedEvent = getEventDetailsFromUser(true);
        if (eventService.editEvent(id, updatedEvent.getName(), updatedEvent.getLocation(), updatedEvent.getDescription(),
                updatedEvent.getCategory(), updatedEvent.getPrice(), updatedEvent.getAvailableTickets(), updatedEvent.getDate())) {
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

    private static Event getEventDetailsFromUser(boolean isEdit) {
        String name = InputUtils.getString("Enter " + (isEdit ? "new " : "") + "event name: ");
        String location = InputUtils.getString("Enter " + (isEdit ? "new " : "") + "event location: ");
        String description = InputUtils.getString("Enter " + (isEdit ? "new " : "") + "event description: ");
        String category = InputUtils.getString("Enter " + (isEdit ? "new " : "") + "event category: ");
        double price = InputUtils.getDouble("Enter " + (isEdit ? "new " : "") + "event price: ");
        int tickets = InputUtils.getInt("Enter " + (isEdit ? "new " : "") + "available tickets: ");
        Date date = InputUtils.getDate("Enter " + (isEdit ? "new " : "") + "event date");

        return new Event(0, name, location, date, description, category, price, tickets, 0); // ID & soldTickets default
    }


    public static void main(String[] args) {
        EventManagementMain app = new EventManagementMain();
        app.start();
        CategoryService categoryService = new CategoryService();
        if (categoryService.addCategory("VIP")) {
            System.out.println("✅ Category added successfully.");
        } else {
            System.out.println("❌ Failed to add category.");
        }

        if (categoryService.removeCategory("VIP")) {
            System.out.println("✅ Category deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete category.");
        }
    }

}
