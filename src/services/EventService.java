package services;

import dao.EventDAO;
import models.Event;
import validation.ValidationRule;
import validation.rules.EventNameValidation;
import validation.rules.EventDescriptionValidation;
import validation.rules.LocationValidation;
import validation.rules.EventPriceValidation;
import validation.rules.TicketCountValidation;

import java.sql.Date;
import java.util.List;

public class EventService {
    private final EventDAO eventDAO;

    public EventService() {
        this.eventDAO = new EventDAO();
    }

    public boolean addEvent(String name, String location, String description, String category, double price, int tickets, Date date) {
        List<ValidationRule<String>> stringValidations = List.of(
                new EventNameValidation(),
                new EventDescriptionValidation(),
                new LocationValidation()

        );

        List<ValidationRule<Double>> priceValidations = List.of(new EventPriceValidation());
        List<ValidationRule<Integer>> ticketValidations = List.of(new TicketCountValidation());

        for (ValidationRule<String> rule : stringValidations) {
            if (!rule.validate(name)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
            if (!rule.validate(location)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
            if (!rule.validate(description)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
            if (!rule.validate(category)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
        }

        for (ValidationRule<Double> rule : priceValidations) {
            if (!rule.validate(price)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
        }

        for (ValidationRule<Integer> rule : ticketValidations) {
            if (!rule.validate(tickets)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
        }

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