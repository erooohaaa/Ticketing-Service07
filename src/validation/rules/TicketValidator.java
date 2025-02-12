package validation.rules;

public class TicketValidator {
    public static boolean isValidTicketData(int eventId, String category, double price, String username) {
        if (eventId <= 0) return false;
        if (category == null || category.trim().isEmpty()) return false;
        if (price <= 0) return false;
        if (username == null || username.trim().isEmpty()) return false;
        return true;
    }
}
