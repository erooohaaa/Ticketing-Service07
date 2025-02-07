package models;

import java.sql.Date;

public class Event {
    private int id;
    private String name;
    private String location;
    private Date date;
    private String description;
    private String category;
    private double price;
    private int availableTickets;
    private String soldTickets;

    public Event(int id, String name, String location, Date date, String description, String category, double price, int availableTickets) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availableTickets = availableTickets;
    }

    public Event(int eventId, String eventName, String location, Date eventDate, String description, String category, double price, int availableTickets, Object o, int soldTickets) {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public String getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(String soldTickets) {
        this.soldTickets = soldTickets;
    }
}
