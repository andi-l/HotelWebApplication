

package fra.uas.model;

import lombok.Data;

@Data
  public class Room {

    private static int lastId = 0; // Static variable to keep track of the last assigned id
    private int id;
    private String type;
    private double pricePerNight;
    private int capacity;
    private Hotel hotel;
    private boolean isClean;

    public Room(String type, double pricePerNight, int capacity) {
      this.id = ++lastId; // Increment lastId and assign it to id
      this.type = type;
      this.pricePerNight = pricePerNight;
      this.capacity = capacity;
      this.hotel = hotel;
      this.isClean = true;
    }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public boolean isClean() {
    return isClean;
}

public void setClean(boolean clean) {
    isClean = clean;
}

  @Override
  public String toString() {
    String roomFormat;
    roomFormat = "Room Type: " + type + ", Price per night: " + pricePerNight + ", Capacity: " + capacity;
    return roomFormat;
  }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }
  }