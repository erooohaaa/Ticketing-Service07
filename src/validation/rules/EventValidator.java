package validation.rules;

import validation.ValidationRule;
import java.sql.Date;

public class EventValidator {
    private static final EventNameValidation nameValidator = new EventNameValidation();
    private static final LocationValidation locationValidator = new LocationValidation();
    private static final EventDescriptionValidation descriptionValidator = new EventDescriptionValidation();
    private static final EventPriceValidation priceValidator = new EventPriceValidation();
    private static final TicketCountValidation ticketCountValidator = new TicketCountValidation();

    public static String validateEvent(String name, String location, String description, String category,
                                       double price, int tickets, String dateStr) {
        if (!nameValidator.validate(name)) {
            return nameValidator.getErrorMessage();
        }
        if (!locationValidator.validate(location)) {
            return locationValidator.getErrorMessage();
        }
        if (!descriptionValidator.validate(description)) {
            return descriptionValidator.getErrorMessage();
        }
        if (!priceValidator.validate(price)) {
            return priceValidator.getErrorMessage();
        }
        if (!ticketCountValidator.validate(tickets)) {
            return ticketCountValidator.getErrorMessage();
        }
        if (category == null || category.trim().isEmpty()) {
            return "Category cannot be empty.";
        }
        try {
            Date.valueOf(dateStr);
        } catch (IllegalArgumentException e) {
            return "Invalid date format. Please enter date in format YYYY-MM-DD.";
        }
        return null;
    }
}
