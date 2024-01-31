package fra.uas.repository;


import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import fra.uas.model.Review;

@Repository
public class RatingRepository {

    public ArrayList<Review> reviewArrayList = new ArrayList<>();
    
}