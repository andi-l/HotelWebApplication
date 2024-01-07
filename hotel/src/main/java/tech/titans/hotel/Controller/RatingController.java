package tech.titans.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.titans.hotel.Model.Rating;
import tech.titans.hotel.Service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public void addRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
    }

    @PostMapping("/average")
    public void getAverage() {
        ratingService.getAverage();
    }

    @PostMapping("/comment")
    public void leaveComment(@RequestParam int starsRating, @RequestParam String comment) {
        ratingService.leaveComment(starsRating, comment);
    }
}








