package validation.rules;

import validation.ValidationRule;

public class EventNameValidation implements ValidationRule<String> {
    @Override
    public boolean validate(String value) {
        return value != null && !value.trim().isEmpty() && !value.equalsIgnoreCase("N/A");
    }

    @Override
    public String getErrorMessage() {
        return "Event line cannot be empty or 'N/A'. Please enter a valid name.";
    }
}