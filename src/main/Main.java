package main;

import services.EventService;
import utils.InputUtils;
import models.Event;

import java.sql.Date;

public class Main {
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
        while (true) {
            String name = InputUtils.getString("Enter event name: ");
            String location = InputUtils.getString("Enter event location: ");
            String description = InputUtils.getString("Enter event description: ");
            int categoryId = InputUtils.getInt("Enter event category ID (1-6): ");
            double price = InputUtils.getDouble("Enter event price: ");
            int tickets = InputUtils.getInt("Enter available tickets: ");
            Date date = InputUtils.getDate("Enter event date");

            try {
                if (eventService.addEvent(name, location, description, categoryId, price, tickets, date)) {
                    System.out.println("Event added successfully.");
                } else {
                    System.out.println("Failed to add event.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (!askToContinue("Do you want to add another event? (yes/no): ")) {
                break;
            }
        }
    }

    private static void listEvents() {
        System.out.println("\n--- Events ---");
        int index = 1;
        for (Event event : eventService.listEvents()) {
            System.out.println(index + ". " + event.getName() + " - " + event.getLocation() +
                    " (" + event.getDate() + "), " + event.getCategory() + "," + event.getPrice() + "$" +
                    ", Tickets: " + event.getAvailableTickets());
            index++;
        }
    }

    private static void editEvent() {
        while (true) {
            int id = InputUtils.getInt("Enter event ID to edit (or 0 to return to main menu): ");
            if (id == 0) {
                return;
            }

            String name = InputUtils.getString("Enter new event name: ");
            String location = InputUtils.getString("Enter new event location: ");
            String description = InputUtils.getString("Enter new event description: ");
            int categoryId = InputUtils.getInt("Enter new event category ID (1-6): ");
            double price = InputUtils.getDouble("Enter new event price: ");
            int tickets = InputUtils.getInt("Enter new available tickets: ");
            Date date = InputUtils.getDate("Enter new event date");

            try {
                if (eventService.editEvent(id, name, location, description, categoryId, price, tickets, date)) {
                    System.out.println("Event updated successfully.");
                } else {
                    System.out.println("Failed to update event.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (!askToContinue("Do you want to edit another event? (yes/no): ")) {
                break;
            }
        }
    }

    private static void deleteEvent() {
        while (true) {
            int id = InputUtils.getInt("Enter the event sequence number in the list to delete (or 0 to return to main menu): ");
            if (id == 0) {
                return;
            }

            if (eventService.removeEvent(id)) {
                System.out.println("Event deleted successfully.");
            } else {
                System.out.println("Failed to delete event.");
            }

            if (!askToContinue("Do you want to delete another event? (yes/no): ")) {
                break;
            }
        }
    }

    private static boolean askToContinue(String message) {
        String response = InputUtils.getString(message).toLowerCase();
        return response.equals("yes");
    }
}
