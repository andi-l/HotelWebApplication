package tech.titans.hotel.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.titans.hotel.Model.Review;
import tech.titans.hotel.Repository.RatingRepository;
import tech.titans.hotel.Service.RatingService;

import jakarta.annotation.PostConstruct;


@Component
public class InitData {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingService ratingService;
    
    
    @PostConstruct
    public void init() {

        
        Review review1 = new Review(3, "hallo");






        
        
}
}
