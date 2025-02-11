package ui;

import services.TicketService;
import java.util.Scanner;

public class TicketUI {
    private TicketService ticketService;
    private Scanner scanner = new Scanner(System.in);

    public TicketUI(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void showTicketMenu() {
        System.out.println("1. Buy ticket\n2. Refund ticket\n3. Exit");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter event ID:");
                int eventId = scanner.nextInt();
                System.out.println("Enter ticket category:");
                String category = scanner.next();
                System.out.println("Enter ticket price:");
                double price = scanner.nextDouble();
                ticketService.purchaseTicket(eventId, category, price);
                System.out.println("Ticket purchased successfully!");
                break;
            case 2:
                System.out.println("Enter ticket ID:");
                int ticketId = scanner.nextInt();
                ticketService.refundTicket(ticketId);
                System.out.println("ticket refunded successfully!");
                break;
            case 3:
                return;
            default:
                System.out.println("invalid input!");
        }
    }
}
