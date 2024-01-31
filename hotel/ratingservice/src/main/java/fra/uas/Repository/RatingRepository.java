package tech.titans.hotel.Repository;


import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import tech.titans.hotel.Model.Review;

@Repository
public class RatingRepository {

    public ArrayList<Review> reviewArrayList = new ArrayList<>();
    
}