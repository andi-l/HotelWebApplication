package tech.titans.hotel.Modul;

public class Room {

  private String type;
  private double pricePerNight;
  private int capacity;

  public Room(String type, double pricePerNight, int capacity) {
    this.type = type;
    this.pricePerNight = pricePerNight;
    this.capacity = capacity;
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

  @Override
  public String toString() {
    String roomFormat;
    roomFormat = "Room Type: " + type + ", Price per night: " + pricePerNight + ", Capacity: " + capacity;
    return roomFormat;
  }
}

