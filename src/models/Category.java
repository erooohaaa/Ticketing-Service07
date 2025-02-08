package models;

public class Category {
    private String name;
    private int seats;

    public Category(String name, int seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public int getSeats() {
        return seats;
    }
}
