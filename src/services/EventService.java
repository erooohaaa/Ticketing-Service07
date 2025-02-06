package services;

import dao.EventDAO;
import models.Event;
import java.sql.Date;
import java.util.List;

public class EventService {
    private final EventDAO eventDAO;

    public EventService() {
        this.eventDAO = new EventDAO();
    }

    public boolean addEvent(String name, String location, String description, String category, double price, int tickets, Date date) {
        return eventDAO.createEvent(name, location, description, category, price, tickets, date);
    }

    public boolean editEvent(int id, String name, String location, String description, String category, double price, int tickets, Date date) {
        return eventDAO.updateEvent(id, name, location, description, category, price, tickets, date);
    }

    public boolean removeEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }

    public List<Event> listEvents() {
        return eventDAO.getAllEvents();
    }

    public Event getEventDetails(int eventId) {
        return eventDAO.getFullEventDetails(eventId);
    }
}
