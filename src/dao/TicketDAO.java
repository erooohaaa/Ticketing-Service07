package dao;

import models.Ticket;
import java.util.List;

public interface TicketDAO {
    void addTicket(Ticket ticket);
    Ticket getTicketById(int id);
    List<Ticket> getTicketsByEvent(int eventId);
    List<Ticket> getTicketsByUser(String username); // Новый метод
    void updateTicketStatus(int id, String status);
}
