package services;

import ui.AdminActions;
import eventmanagement.EventManagementMain;
import java.util.Scanner;

public class AdminService {
    private AdminActions adminActions;  // ✅ Добавлено

    public AdminService(AdminActions adminActions) {
        this.adminActions = adminActions;
    }

    public static void viewAllUsers() {
        AdminActions.viewAllUsers();
    }

    public static void viewAllAdmins() {
        AdminActions.viewAllAdmins();
    }

    public static void deleteUser(Scanner scanner) {
        AdminActions.deleteUser(scanner);
    }

    public static void deleteAdmin(Scanner scanner) {
        AdminActions.deleteAdmin(scanner);
    }

    public static void manageEvents() {
        EventManagementMain.manageEvents();
    }
}
