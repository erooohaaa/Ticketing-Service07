package validation.rules;

import validation.ValidationRule;

public class TicketCountValidation implements ValidationRule<Integer> {
    @Override
    public boolean validate(Integer count) {
        return count != null && count >= 0;
    }

    @Override
    public String getErrorMessage() {
        return "Available tickets cannot be negative.";
    }
}
