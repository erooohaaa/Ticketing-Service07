package validation.rules;

import validation.ValidationRule;

public class CategoryValidation implements ValidationRule<String> {
    @Override
    public boolean validate(String category) {
        // Пример проверки: категория не должна быть пустой или null
        return category != null && !category.trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Category cannot be empty or null!";
    }
}