package tech.titans.hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import tech.titans.hotel.Model.User;
//import tech.titans.hotel.Model.RatingRepository;
import tech.titans.hotel.Model.Rating;
import tech.titans.hotel.Repository.RatingRepository;

import java.util.Scanner;
import java.util.ArrayList;



@Service
public class RatingService3 {
    
    @Autowired RatingRepository ratingrepository;

    static ArrayList<Integer> stars = new ArrayList<Integer>();
    static ArrayList<String> ratings = new ArrayList<String>();

    //create a evaluation
    @Autowired
    public void evaluateHotel(Rating stars) {
        ratings.add(stars);
    }

    //create a rating 
    @Autowired
    public void rateHotel(Rating rating) {
        ratings.add(rating);
    }
}
