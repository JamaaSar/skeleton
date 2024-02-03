package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void delete(Integer id) {
        ratingRepository.deleteById(id);
    }

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }
    public Rating get(Integer id) {
        return ratingRepository.findById(id).orElseThrow();
    }
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
}
