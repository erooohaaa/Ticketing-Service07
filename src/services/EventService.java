package services;

import dao.EventDAO;
import models.Event;
import validation.ValidationRule;
import validation.rules.EventNameValidation;
import validation.rules.EventPriceValidation;
import validation.rules.TicketCountValidation;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис для работы с событиями, теперь с разделёнными правилами валидации
 */
public class EventService {
    private final EventDAO eventDAO;

    public EventService() {
        this.eventDAO = new EventDAO();
    }

    // 🔹 Исправлен список валидации: теперь правила разделены по типу
    private final List<ValidationRule<String>> stringValidations = Arrays.asList(
            new EventNameValidation()  // Проверяет название события
    );

    private final List<ValidationRule<Double>> doubleValidations = Arrays.asList(
            new EventPriceValidation()  // Проверяет цену события
    );

    private final List<ValidationRule<Integer>> intValidations = Arrays.asList(
            new TicketCountValidation()  // Проверяет количество билетов
    );

    /**
     * 🔹 Теперь перед созданием события проводится проверка данных
     */
    public boolean addEvent(String name, String location, String description, String category, double price, int tickets, Date date) {
        for (ValidationRule<String> rule : stringValidations) {  // Проверяем строковые данные
            if (!rule.validate(name)) {
                System.out.println("Validation Error: " + rule.getErrorMessage());
                return false;
            }
        }

        for (ValidationRule<Double> rule : doubleValidations) {  // Проверяем цену
            if (!rule.validate(price)) {
                System.out.println("Validation Error: " + rule.getErrorMessage());
                return false;
            }
        }

        for (ValidationRule<Integer> rule : intValidations) {  // Проверяем количество билетов
            if (!rule.validate(tickets)) {
                System.out.println("Validation Error: " + rule.getErrorMessage());
                return false;
            }
        }

        return eventDAO.createEvent(name, location, description, category, price, tickets, date);
    }

    /**
     * 🔹 Теперь при редактировании тоже используется валидация
     */
    public boolean editEvent(int id, String name, String location, String description, String category, double price, int tickets, Date date) {
        for (ValidationRule<String> rule : stringValidations) {
            if (!rule.validate(name)) {
                System.out.println("Validation Error: " + rule.getErrorMessage());
                return false;
            }
        }

        for (ValidationRule<Double> rule : doubleValidations) {
            if (!rule.validate(price)) {
                System.out.println("Validation Error: " + rule.getErrorMessage());
                return false;
            }
        }

        for (ValidationRule<Integer> rule : intValidations) {
            if (!rule.validate(tickets)) {
                System.out.println("Validation Error: " + rule.getErrorMessage());
                return false;
            }
        }

        return eventDAO.updateEvent(id, name, location, description, category, price, tickets, date);
    }

    /**
     * Метод удаления события
     */
    public boolean removeEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }

    /**
     * Метод получения списка всех событий
     */
    public List<Event> listEvents() {
        return eventDAO.getAllEvents();
    }

    /**
     * Метод получения полной информации о событии
     */
    public Event getEventDetails(int eventId) {
        return eventDAO.getFullEventDetails(eventId);
    }
}