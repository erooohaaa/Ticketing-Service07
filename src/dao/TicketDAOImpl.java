package dao;

import models.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDAOImpl implements TicketDAO {
    private static TicketDAOImpl instance;
    private List<Ticket> tickets;
    private int nextTicketId = 1;

    private TicketDAOImpl() {
        tickets = new ArrayList<>();
    }

    public static TicketDAOImpl getInstance() {
        if (instance == null) {
            instance = new TicketDAOImpl();
        }
        return instance;
    }

    public synchronized int generateNextTicketId() {
        return nextTicketId++;
    }

    @Override
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public Ticket getTicketById(int id) {
        Optional<Ticket> foundTicket = tickets.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
        return foundTicket.orElse(null);
    }

    @Override
    public List<Ticket> getTicketsByEvent(int eventId) {
        List<Ticket> eventTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getEventId() == eventId) {
                eventTickets.add(ticket);
            }
        }
        return eventTickets;
    }

    @Override
    public List<Ticket> getTicketsByUser(String username) {
        List<Ticket> userTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUsername().equalsIgnoreCase(username)) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }

    @Override
    public void updateTicketStatus(int id, String status) {
        Ticket ticket = getTicketById(id);
        if (ticket != null) {
            ticket.setStatus(status);
        }
    }
}
