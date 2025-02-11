package dao;

import models.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public Ticket getTicketById(int id) {
        return tickets.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
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
    public void updateTicketStatus(int id, String status) {
        Ticket ticket = getTicketById(id);
        if (ticket != null) {
            ticket.setStatus(status);
        }
    }
}
