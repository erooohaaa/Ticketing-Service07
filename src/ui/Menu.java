package ui;

import java.util.Scanner;

public class Menu {
    public static void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register User");
            System.out.println("2. Register Admin");
            System.out.println("3. Login as User");
            System.out.println("4. Login as Admin");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> UserManager.registerUser(scanner);
                case 2 -> AdminManager.registerAdmin(scanner);
                case 3 -> UserManager.loginUser(scanner);
                case 4 -> AdminManager.loginAdmin(scanner);
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
