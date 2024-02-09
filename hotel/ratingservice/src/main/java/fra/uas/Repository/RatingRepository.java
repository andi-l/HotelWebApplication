package fra.uas.repository;


import fra.uas.model.Booking;
import fra.uas.model.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;


@Repository
public class RatingRepository {
    public HashMap<Booking, Review> reviewHashMap = new HashMap<Booking, Review>();

    public void addReview(Booking booking, Review review) {
        reviewHashMap.put(booking, review);
    }

    public Collection<Review> getAllReviews() {
        return reviewHashMap.values();
    }

}