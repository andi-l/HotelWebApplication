package fra.uas.model;

import java.util.ArrayList;
import java.util.List;


public class Hotel {

    private String name;
    private String location;
    private ArrayList<Booking> bookings;
    private ArrayList<Room> rooms;
    private double rating;
    private ArrayList<Rating> allratings;

    public Hotel(String name, String location, double rating, ArrayList<Booking> booking, ArrayList<Rating> allRatings) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.bookings = new ArrayList<Booking>();
        this. rooms = new ArrayList<Room>();
        this.allratings = new ArrayList<Rating>();
    }


    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation() {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating() {
        this.rating = rating;
    }

    public ArrayList<Rating> getAllratings() {
        return this.allratings;
    }

    public void setAllratings(ArrayList<Rating> allratings) {
        this.allratings = allratings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addRoom(Room room) {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        if (rooms != null) {
            rooms.remove(room);
        }
    }

    public List<Room> getRooms() {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        return rooms;
    }

    public void addBooking(Booking booking) {
        if (this.bookings == null) {
            this.bookings = new ArrayList<>();
        }
        this.bookings.add(booking);
    }


    @Override
    public String toString() {
        return "Hotel [name=" + name + ", location=" + location + ", bookings=" + bookings + ", rooms=" + rooms
                + ", rating=" + rating + ", allratings=" + allratings + "]";
    }


}