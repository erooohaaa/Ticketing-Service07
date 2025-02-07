package services;

import dao.EventDAO;
import models.Event;
import validation.ValidationRule;
import validation.rules.*;

import java.sql.Date;
import java.util.List;

public class EventService implements IEventService {
    private final EventDAO eventDAO;
    private final CategoryService categoryService;
    private final List<ValidationRule<String>> stringValidations;
    private final List<ValidationRule<Double>> priceValidations;
    private final List<ValidationRule<Integer>> ticketValidations;

    public EventService(EventDAO eventDAO, CategoryService categoryService) {
        this.eventDAO = eventDAO;
        this.categoryService = categoryService;

        // Инициализация правил валидации
        this.stringValidations = List.of(
                new EventNameValidation(),
                new EventDescriptionValidation(),
                new LocationValidation(),
                new CategoryValidation() // Убедитесь, что CategoryValidation реализует ValidationRule<String>
        );
        this.priceValidations = List.of(new EventPriceValidation());
        this.ticketValidations = List.of(new TicketCountValidation());
    }

    @Override
    public boolean addEvent(String name, String location, String description, String category, double price, int tickets, Date date) {
        if (!validateInputs(name, location, description, category, price, tickets)) {
            return false;
        }

        int availableSeats = categoryService.getSeatsForCategory(category);
        if (tickets > availableSeats) {
            System.out.println("❌ Not enough seats available for this category.");
            return false;
        }

        return eventDAO.createEvent(name, location, description, category, price, tickets, date);
    }

    @Override
    public boolean editEvent(int id, String name, String location, String description, String category, double price, int tickets, Date date) {
        if (!validateInputs(name, location, description, category, price, tickets)) {
            return false;
        }

        int availableSeats = categoryService.getSeatsForCategory(category);
        if (tickets > availableSeats) {
            System.out.println("❌ Not enough seats available for this category.");
            return false;
        }

        return eventDAO.updateEvent(id, name, location, description, category, price, tickets, date);
    }

    @Override
    public boolean removeEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }

    @Override
    public List<Event> listEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public Event getEventDetails(int eventId) {
        return eventDAO.getFullEventDetails(eventId);
    }

    private boolean validateInputs(String name, String location, String description, String category, double price, int tickets) {
        // Валидация строковых полей
        for (ValidationRule<String> rule : stringValidations) {
            if (!rule.validate(name)) {
                System.out.println("❌ Event name: " + rule.getErrorMessage());
                return false;
            }
            if (!rule.validate(location)) {
                System.out.println("❌ Location: " + rule.getErrorMessage());
                return false;
            }
            if (!rule.validate(description)) {
                System.out.println("❌ Description: " + rule.getErrorMessage());
                return false;
            }
            if (!rule.validate(category)) {
                System.out.println("❌ Category: " + rule.getErrorMessage());
                return false;
            }
        }

        // Валидация цены
        for (ValidationRule<Double> rule : priceValidations) {
            if (!rule.validate(price)) {
                System.out.println("❌ Price: " + rule.getErrorMessage());
                return false;
            }
        }

        // Валидация количества билетов
        for (ValidationRule<Integer> rule : ticketValidations) {
            if (!rule.validate(tickets)) {
                System.out.println("❌ Tickets: " + rule.getErrorMessage());
                return false;
            }
        }

        return true;
    }
}