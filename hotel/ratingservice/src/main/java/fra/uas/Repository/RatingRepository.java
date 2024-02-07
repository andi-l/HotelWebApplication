package fra.uas.Repository;


import java.util.ArrayList;

import fra.uas.Model.*;
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