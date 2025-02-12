package ui;

import services.TicketService;
import java.util.Scanner;

public class TicketUI {
    private TicketService ticketService;
    private Scanner scanner = new Scanner(System.in);

    public TicketUI(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void refundTicketUI() {
        System.out.print("Enter ticket ID to refund: ");
        int ticketId = scanner.nextInt();
        ticketService.refundTicket(ticketId);
        System.out.println("✅ Ticket refunded successfully!");
    }
}
