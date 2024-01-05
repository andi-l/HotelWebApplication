package tech.titans.hotel.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import tech.titans.hotel.Model.Rating;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import tech.titans.hotel.Service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    public RatingService ratingService;

    //@GetMapping("/bewertungen")
    //@ResponseBody
    //public List<Integer> getRatings() {
        //return rating;
    //}


    @PostMapping("/add")
    public String addRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
        return "Rating added successfully!";
    }
}






