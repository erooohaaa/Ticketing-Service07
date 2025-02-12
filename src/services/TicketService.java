import models.Ticket;
import dao.TicketDAO;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void purchaseTicket(int eventId, String category, double price, String username) {
        int newTicketId = ((dao.TicketDAOImpl) ticketDAO).generateNextTicketId();
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
