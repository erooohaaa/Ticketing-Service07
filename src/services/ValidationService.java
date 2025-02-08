package services;

import validation.ValidationRule;
import java.util.List;

public class ValidationService<T> implements IValidationService<T> {
    private final List<ValidationRule<T>> rules;

    public ValidationService(List<ValidationRule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public boolean validate(T value) {
        for (ValidationRule<T> rule : rules) {
            if (!rule.validate(value)) {
                System.out.println("❌ " + rule.getErrorMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Validation failed";
    }
}
