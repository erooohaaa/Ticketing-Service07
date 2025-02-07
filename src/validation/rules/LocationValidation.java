package validation.rules;

import validation.ValidationRule;

public class LocationValidation implements ValidationRule<String> {
    @Override
    public boolean validate(String value) {
        return value != null && !value.trim().isEmpty() && !value.equalsIgnoreCase("N/A");
    }

    @Override
    public String getErrorMessage() {
        return "Location cannot be empty or 'N/A'. Please enter a valid location.";
    }
}