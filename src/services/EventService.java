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
        boolean result = eventDAO.createEvent(name, location, description, categoryName, price, tickets, date);
        if (result) {
            eventDAO.renumberEvents();
            eventDAO.resetAutoIncrement();
        }
        return result;
    }

    public List<Event> listEvents() {
        return eventDAO.getAllEvents();
    }

    public boolean editEvent(int id, String name, String location, String description, int categoryId, double price, int tickets, Date date) {
        String categoryName = eventDAO.getCategoryNameById(categoryId);
        if (categoryName == null) {
            throw new IllegalArgumentException("Invalid category ID.");
        }
        boolean result = eventDAO.updateEvent(id, name, location, description, categoryName, price, tickets, date);
        if (result) {
            eventDAO.renumberEvents();
            eventDAO.resetAutoIncrement();
        }
        return result;
    }

    public boolean removeEvent(int id) {
        boolean result = eventDAO.deleteEvent(id);
        if (result) {
            eventDAO.renumberEvents();
            eventDAO.resetAutoIncrement();
        }
        return result;
    }
}
