package tech.titans.hotel.Model;


public class Hotel {
    
    String name;
    String location;
    Room availablerooms; 
    Room bookedrooms;
    double rating;
    int[] allratings; //type muss geändert werden zu rating
    

    public Hotel(String name, String location, double rating, Room availablerooms, Room bookedrooms) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.availablerooms = availablerooms;
        this.bookedrooms = bookedrooms;
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


    public Room getAvailablerooms() {
        return this.availablerooms;
    }

    public void setAvailablerooms(Room availablerooms) {
        this.availablerooms = availablerooms;
    }

    public Room getBookedrooms() {
        return this.bookedrooms;
    }

    public void setBookedrooms(Room bookedrooms) {
        this.bookedrooms = bookedrooms;
    }

    public int[] getAllratings() {
        return this.allratings;
    }

    public void setAllratings(int[] allratings) {
        this.allratings = allratings;
    }


    @Override
    public String toString() {
        return "{name= " + getName()+ ", location= " + getLocation() +  ", availablerooms= " + getAvailablerooms() + ", bookedrooms= " + getBookedrooms() +
            ", rating= " + getRating() + ", allratings= " + getAllratings();
    }


}
