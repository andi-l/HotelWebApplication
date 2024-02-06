package fra.uas.Repository;


import java.util.ArrayList;

import fra.uas.Model.Booking;

import org.springframework.stereotype.Repository;
import fra.uas.Model.Review;
import java.util.HashMap;


@Repository
public class RatingRepository {

    //public ArrayList<Review> reviewArrayList = new ArrayList<>();

    public HashMap<Integer, Review> reviewHashMap = new HashMap<Integer, Review>();




//        public void addReviewId(Review review) {
//        int reviewId = Booking.getID();
//
//        reviewHashMap.put(reviewId, review);
//       }


}