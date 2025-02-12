package services;

import models.Ticket;
import dao.TicketDAO;
import dao.TicketDAOImpl;
import validation.rules.TicketValidator;  // <-- добавляем импорт

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void purchaseTicket(int eventId, String category, double price, String username) {
        if (!TicketValidator.isValidTicketData(eventId, category, price, username)) {
            System.out.println("Invalid ticket data provided. Ticket purchase aborted.");
            return;
        }
        int newTicketId = ((TicketDAOImpl) ticketDAO).generateNextTicketId();
        Ticket ticket = new Ticket(newTicketId, eventId, category, price, "Sold", username);
        ticketDAO.addTicket(ticket);
    }

    public void refundTicket(int ticketId) {
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        if (ticket != null && !"Refunded".equalsIgnoreCase(ticket.getStatus())) {
            ticketDAO.updateTicketStatus(ticketId, "Refunded");
        }
    }
}
