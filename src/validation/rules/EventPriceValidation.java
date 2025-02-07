package validation.rules;

import validation.ValidationRule;

/**

 Проверяет, что цена события больше 0*/
public class EventPriceValidation implements ValidationRule<Double> {
    @Override
    public boolean validate(Double price) {
        return price != null && price > 0;
    }

    @Override
    public String getErrorMessage() {
        return "Event price must be greater than 0!";
    }
}