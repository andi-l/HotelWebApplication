package tech.titans.hotel.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import tech.titans.hotel.HotelApplication;
import tech.titans.hotel.Model.Rating;
import tech.titans.hotel.Repository.RatingRepository;
import tech.titans.hotel.Service.RatingService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


import tech.titans.hotel.Repository.*;


@Component
public class InitData {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingService ratingService;
    
    
    @PostConstruct
    public void init() {

        
        Rating rating1 = new Rating(3, "hallo";)






        
        
}
}
