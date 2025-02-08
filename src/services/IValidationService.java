package services;

public interface IValidationService<T> {
    boolean validate(T value);
    String getErrorMessage();
}
