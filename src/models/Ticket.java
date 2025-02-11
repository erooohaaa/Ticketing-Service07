package models;

public class Ticket {
    private int id;
    private int eventId;
    private String category;
    private double price;
    private String status; // Available, Sold, Refunded

    public Ticket(int id, int eventId, String category, double price, String status) {
        this.id = id;
        this.eventId = eventId;
        this.category = category;
        this.price = price;
        this.status = status;
    }

    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
