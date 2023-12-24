package tech.titans.hotel.Modul;

public class Hotel {
    
    String name;
    String location;
    int[] availablerooms; //type muss geändert werden zu room
    int[] bookedrooms;
    double rating;
    int[] allratings; //type muss geändert werden zu rating


    public Hotel(String name, String location, double rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
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

}
