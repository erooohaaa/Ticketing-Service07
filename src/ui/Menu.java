package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static void displayMainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n📌 Main Menu:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Чистим буфер после nextInt()
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number (1, 2, or 3).");
                scanner.nextLine(); // Очищаем некорректный ввод
                continue; // Возвращаемся в начало цикла
            }

            switch (choice) {
                case 1 -> AuthManager.login(scanner);
                case 2 -> AuthManager.registerUser(scanner);
                case 3 -> {
                    System.out.println("🚪 Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
}
