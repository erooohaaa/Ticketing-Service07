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

    public static synchronized TicketDAOImpl getInstance() {
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
                .findAny();
        return foundTicket.orElse(null);
    }

    @Override
    public List<Ticket> getTicketsByEvent(int eventId) {
        return tickets.stream()
                .filter(t -> t.getEventId() == eventId)
                .toList();
    }

    @Override
    public List<Ticket> getTicketsByUser(String username) {
        return tickets.stream()
                .filter(t -> t.getUsername().equalsIgnoreCase(username))
                .toList();
    }

    @Override
    public void updateTicketStatus(int id, String status) {
        Ticket ticket = getTicketById(id);
        if (ticket != null) {
            ticket.setStatus(status);
        }
    }
}
