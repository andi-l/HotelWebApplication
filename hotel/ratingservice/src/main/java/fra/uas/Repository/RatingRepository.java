package fra.uas.repository;


import java.util.ArrayList;

import fra.uas.model.Booking;

import org.springframework.stereotype.Repository;
import fra.uas.model.Review;
import java.util.HashMap;


@Repository
public class RatingRepository {

    //public ArrayList<Review> reviewArrayList = new ArrayList<>();

    public HashMap<Integer, Review> reviewHashMap = new HashMap<Integer, Review>();


}