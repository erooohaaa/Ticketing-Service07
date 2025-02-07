package validation.rules;

import validation.ValidationRule;

/**

 Проверяет, что название события не пустое*/
public class EventNameValidation implements ValidationRule<String> {
    @Override
    public boolean validate(String eventName) {
        return eventName != null && !eventName.trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Event name cannot be empty!";
    }
}