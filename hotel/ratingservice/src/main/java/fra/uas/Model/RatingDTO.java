package fra.uas.model;

import fra.uas.model.Booking;

public class RatingDTO {

    private Booking booking;
    private Review review;

    public RatingDTO(Booking booking, Review review) {
        this.booking = booking;
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking() {
        this.booking = booking;
    }

}
