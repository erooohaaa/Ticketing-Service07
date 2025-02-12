package dao;

import models.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {
    private static TicketDAOImpl instance;
    private List<Ticket> tickets;
    private int nextTicketId = 1; // Счётчик для уникальных ID билетов

    // Приватный конструктор для синглтона
    private TicketDAOImpl() {
        tickets = new ArrayList<>();
    }

    // Получение единственного экземпляра
    public static TicketDAOImpl getInstance() {
        if (instance == null) {
            instance = new TicketDAOImpl();
        }
        return instance;
    }

    // Метод генерации нового уникального ID (потокобезопасно)
    public synchronized int generateNextTicketId() {
        return nextTicketId++;
    }

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
