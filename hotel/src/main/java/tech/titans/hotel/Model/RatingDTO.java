package tech.titans.hotel.Model;

public class RatingDTO {
    private int stars;

    public RatingDTO() {
    }

    public RatingDTO(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
