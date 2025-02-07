package validation;

public interface ValidationRule<T> {
    boolean validate(T data);  // Проверяет данные
    String getErrorMessage();  // Возвращает сообщение об ошибке
}