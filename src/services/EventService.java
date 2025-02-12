package services;

import dao.EventDAO;
import dao.TicketDAO;
import dao.TicketDAOImpl;
import models.Event;
import models.Ticket;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import config.DatabaseConfig;

public class EventService {
    private static final EventDAO eventDAO = new EventDAO();
    private static final TicketDAO ticketDAO = TicketDAOImpl.getInstance();

    public static void listEvents() {
        List<Event> events = eventDAO.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("❌ No available events.");
        } else {
            System.out.println("📅 Available events:");
            for (Event event : events) {
                System.out.println(event.getId() + ": " + event.getName() + " at " + event.getLocation() + " on " + event.getDate()
                        + " | Tickets: " + event.getAvailableTickets());
            }
        }
    }

    public static void viewEvents() {
        listEvents();
    }

    // Уменьшает доступные билеты на 1 после бронирования
    public static void updateAvailableTickets(int eventId) {
        Event event = eventDAO.getFullEventDetails(eventId);
        if (event != null && event.getAvailableTickets() > 0) {
            int remainingTickets = event.getAvailableTickets() - 1;
            String sql = "UPDATE events SET available_tickets = ? WHERE event_id = ?";
            try (var conn = DatabaseConfig.getConnection();
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, remainingTickets);
                pstmt.setInt(2, eventId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Увеличивает доступные билеты на 1 (вызывается при возврате билета)
    public static void increaseAvailableTickets(int eventId) {
        Event event = eventDAO.getFullEventDetails(eventId);
        if (event != null) {
            int newTickets = event.getAvailableTickets() + 1;
            String sql = "UPDATE events SET available_tickets = ? WHERE event_id = ?";
            try (var conn = DatabaseConfig.getConnection();
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, newTickets);
                pstmt.setInt(2, eventId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // При бронировании билета генерируется уникальный ID
    public static void bookTicket(int eventId, String username) {
        Event event = eventDAO.getFullEventDetails(eventId);
        if (event == null) {
            System.out.println("❌ Event not found.");
            return;
        }

        if (event.getAvailableTickets() <= 0) {
            System.out.println("❌ No tickets available for this event.");
            return;
        }

        int newTicketId = ((TicketDAOImpl) ticketDAO).generateNextTicketId();
        Ticket ticket = new Ticket(newTicketId, eventId, event.getCategory(), event.getPrice(), "Sold", username);
        ticketDAO.addTicket(ticket);
        updateAvailableTickets(eventId);
        System.out.println("✅ Ticket booked successfully for event: " + event.getName());
    }

    public static void viewUserTickets(String username) {
        List<Ticket> userTickets = ticketDAO.getTicketsByUser(username);
        if (userTickets.isEmpty()) {
            System.out.println("❌ No tickets found for user: " + username);
        } else {
            System.out.println("🎫 Tickets for user " + username + ":");
            for (Ticket ticket : userTickets) {
                System.out.println("Ticket ID: " + ticket.getId() + " | Event ID: " + ticket.getEventId() +
                        " | Category: " + ticket.getCategory() + " | Price: " + ticket.getPrice() +
                        " | Status: " + ticket.getStatus());
            }
        }
    }

    public static boolean addEvent(String name, String location, String description, String category, double price, int tickets, Date date) {
        System.out.println("➕ Adding event: " + name);
        return eventDAO.createEvent(name, location, description, category, price, tickets, date);
    }

    public static boolean editEvent(int eventId, String newName, String newLocation, String newDescription, String newCategory, double newPrice, int newTickets, Date newDate) {
        System.out.println("✏ Editing event ID: " + eventId);
        return eventDAO.updateEvent(eventId, newName, newLocation, newDescription, newCategory, newPrice, newTickets, newDate);
    }

    public static boolean removeEvent(int eventId) {
        System.out.println("🗑 Removing event ID: " + eventId);
        return eventDAO.deleteEvent(eventId);
    }
}
