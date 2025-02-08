package services;

import models.Event;
import java.sql.Date;
import java.util.List;

public interface IEventService {
    boolean addEvent(String name, String location, String description, String category, double price, int tickets, Date date);
    boolean editEvent(int id, String name, String location, String description, String category, double price, int tickets, Date date);
    boolean removeEvent(int eventId);
    List<Event> listEvents();
    Event getEventDetails(int eventId);
}
