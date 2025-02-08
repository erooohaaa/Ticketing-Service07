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
    private String organizer;
    private int soldTickets;

    public Event(int id, String name, String location, Date date, String description, String category, double price, int availableTickets, String organizer, int soldTickets) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.category = category;
        this.price = price;
        this.availableTickets = availableTickets;
        this.organizer = organizer;
        this.soldTickets = soldTickets;
    }

    public Event(int id, String name, String location, Date date, String description, String category, double price, int tickets, int i) {
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public Date getDate() { return date; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getAvailableTickets() { return availableTickets; }
    public String getOrganizer() { return organizer; }
    public int getSoldTickets() { return soldTickets; }
}
