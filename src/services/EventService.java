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

    public boolean addEvent(String name, String location, String description, int categoryId, double price, int tickets, Date date) {
        String categoryName = eventDAO.getCategoryNameById(categoryId);
        if (categoryName == null) {
            throw new IllegalArgumentException("Invalid category ID.");
        }
        return eventDAO.createEvent(name, location, description, categoryName, price, tickets, date);
    }

    public boolean editEvent(int id, String name, String location, String description, int categoryId, double price, int tickets, Date date) {
        String categoryName = eventDAO.getCategoryNameById(categoryId);
        if (categoryName == null) {
            throw new IllegalArgumentException("Invalid category ID.");
        }
        return eventDAO.updateEvent(id, name, location, description, categoryName, price, tickets, date);
    }

    public List<Event> listEvents() {
        return eventDAO.getAllEvents();
    }

    public boolean removeEvent(int id) {
        return eventDAO.deleteEvent(id);
    }

}
