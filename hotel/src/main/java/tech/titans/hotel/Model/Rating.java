package tech.titans.hotel.Model;

public class Rating{
    int stars;
    String comment;

    public Rating(int stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    public Rating(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return this.stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
