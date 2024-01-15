package tech.titans.hotel.Model;
import java.util.ArrayList;


public class Hotel {
    
    String name;
    String location;
    ArrayList<Room> availablerooms; 
    ArrayList<Room> bookedrooms;
    double rating;
    ArrayList<Review> allratings;

    public Hotel(String name, String location, double rating, ArrayList<Room> availablerooms, ArrayList<Room> bookedrooms, ArrayList<Review> allReviews) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.availablerooms  = new ArrayList<Room>();
        this.bookedrooms = new ArrayList<Room>(); 
        this.allratings = new ArrayList<Review>();
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



    public ArrayList<Room> getAvailablerooms() {
        return this.availablerooms;
    }

    public void setAvailablerooms(ArrayList<Room> availablerooms) {
        this.availablerooms = availablerooms;
    }

    public ArrayList<Room> getBookedrooms() {
        return this.bookedrooms;
    }

    public void setBookedrooms(ArrayList<Room> bookedrooms) {
        this.bookedrooms = bookedrooms;
    }


    public ArrayList<Review> getAllratings() {
        return this.allratings;
    }

    public void setAllratings(ArrayList<Review> allratings) {
        this.allratings = allratings;
    }



    @Override
    public String toString() {
        return "{name= " + getName()+ ", location= " + getLocation() +  ", availablerooms= " + getAvailablerooms() + ", bookedrooms= " + getBookedrooms() +
            ", rating= " + getRating() + ", allratings= " + getAllratings();
    }


}
