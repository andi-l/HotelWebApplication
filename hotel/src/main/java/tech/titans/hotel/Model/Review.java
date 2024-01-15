package tech.titans.hotel.Model;

public class Review {
    int stars;
    String comment;

    public Review(int stars, String comment) {
        this.stars = stars;
        this.comment = comment;
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

    @Override
    public String toString() {
        return "[" + "stars = " + stars +
                ", comment ='" + comment + '\'' + "]";
    }

}

