package services;

import models.Ticket;
import dao.TicketDAO;
import java.util.List;

public class TicketService {
    private TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void purchaseTicket(int eventId, String category, double price) {
        Ticket ticket = new Ticket(ticketDAO.getTicketsByEvent(eventId).size() + 1, eventId, category, price, "Sold");
        ticketDAO.addTicket(ticket);
    }

    public void refundTicket(int ticketId) {
        ticketDAO.updateTicketStatus(ticketId, "Refunded");
    }

    public List<Ticket> getTicketsByEvent(int eventId) {
        return ticketDAO.getTicketsByEvent(eventId);
    }
}
