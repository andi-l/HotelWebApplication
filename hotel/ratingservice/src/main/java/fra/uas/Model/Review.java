package fra.uas.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty(required = true)
    private Integer stars;

    @JsonProperty(required = false)
    private String comment;

    public Review(Integer stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    public Review() {
    }

    public Integer getStars() {
        return this.stars;
    }

    public void setStars(Integer stars) {
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