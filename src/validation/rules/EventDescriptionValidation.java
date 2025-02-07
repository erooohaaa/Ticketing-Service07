package validation.rules;

import validation.ValidationRule;

public class EventDescriptionValidation implements ValidationRule<String> {
    @Override
    public boolean validate(String description) {
        return description != null && !description.trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Event description cannot be empty!";
    }
}