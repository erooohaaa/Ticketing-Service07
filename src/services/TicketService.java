package services;

import models.Ticket;
import dao.TicketDAO;
import dao.TicketDAOImpl;
import java.util.List;

public class TicketService {
    private TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    // При покупке билета генерируется уникальный ID и билет создается с привязкой к пользователю
    public void purchaseTicket(int eventId, String category, double price, String username) {
        int newTicketId = ((TicketDAOImpl) ticketDAO).generateNextTicketId();
        Ticket ticket = new Ticket(newTicketId, eventId, category, price, "Sold", username);
        ticketDAO.addTicket(ticket);
    }

    // При возврате билета обновляется его статус и увеличивается количество билетов события
    public void refundTicket(int ticketId) {
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        if (ticket != null && !ticket.getStatus().equals("Refunded")) {
            ticketDAO.updateTicketStatus(ticketId, "Refunded");
            EventService.increaseAvailableTickets(ticket.getEventId());
        }
    }

    public List<Ticket> getTicketsByEvent(int eventId) {
        return ticketDAO.getTicketsByEvent(eventId);
    }
}
