package validation.rules;

import validation.ValidationRule;

public class EventNameValidation implements ValidationRule<String> {
    @Override
    public boolean validate(String name) {
        return name != null && !name.trim().isEmpty() && !name.equalsIgnoreCase("N/A");
    }

    @Override
    public String getErrorMessage() {
        return "Event name cannot be empty or 'N/A'. Please enter a valid name.";
    }
}
