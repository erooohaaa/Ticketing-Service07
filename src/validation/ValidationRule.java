package validation;

public interface ValidationRule<T> {
    boolean validate(T data);
    String getErrorMessage();
}
