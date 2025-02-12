package models;

public class Ticket {
    private int id;
    private int eventId;
    private String category;
    private double price;
    private String status;
    private String username;

    public Ticket(int id, int eventId, String category, double price, String status, String username) {
        this.id = id;
        this.eventId = eventId;
        this.category = category;
        this.price = price;
        this.status = status;
        this.username = username;
    }

    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public String getUsername() { return username; }

    public void setStatus(String status) { this.status = status; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", eventId=" + eventId + ", category=" + category +
                ", price=" + price + ", status=" + status + ", username=" + username + "]";
    }
}
