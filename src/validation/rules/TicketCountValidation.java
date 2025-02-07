package validation.rules;

import validation.ValidationRule;

/**

 Проверяет, что количество билетов не отрицательное*/
public class TicketCountValidation implements ValidationRule<Integer> {
    @Override
    public boolean validate(Integer tickets) {
        return tickets != null && tickets >= 0;
    }

    @Override
    public String getErrorMessage() {
        return "Available tickets cannot be negative!";
    }
}
