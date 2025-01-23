package utils;

import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? "N/A" : input;
    }

    public static int getInt(String prompt) {
        System.out.print(prompt);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static double getDouble(String prompt) {
        System.out.print(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public static Date getDate(String prompt) {
        System.out.print(prompt + " (dd-MM-yyyy): ");
        String dateStr = scanner.next();
        scanner.nextLine();
        try {
            java.util.Date utilDate = new SimpleDateFormat("dd-mm-yyyy").parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format.");
        }
    }
}
