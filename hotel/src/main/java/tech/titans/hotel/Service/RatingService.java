package tech.titans.hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.titans.hotel.Repository.RatingRepository;
import tech.titans.hotel.Model.Rating;

@Service
public class RatingService implements RatingServiceInterface {

    @Autowired
    public RatingRepository ratingRepository;

    @Override
    public void addRating(Rating rating) {
        ratingRepository.ratingArrayList.add(rating);
        System.out.println("New Rating " + rating.getStars() + rating.getComment() + " created!");
    }
}
