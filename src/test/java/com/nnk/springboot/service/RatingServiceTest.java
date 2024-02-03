package com.nnk.springboot.service;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private RatingService ratingService;

    private Rating rating = new Rating();
    private Rating rating1 = new Rating();
    private List<Rating> ratingList = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating 0");
        rating.setSandPRating("Sand PRating 0");
        rating.setFitchRating("Fitch Rating 0");
        rating.setOrderNumber(1);



        rating1.setId(2);
        rating1.setMoodysRating("Moodys Rating 1");
        rating1.setSandPRating("Sand PRating 1");
        rating1.setFitchRating("Fitch Rating 1");
        rating1.setOrderNumber(2);
        ratingList.add(rating);
        ratingList.add(rating1);

    }

    @Test
    @DisplayName("get All Rating")
    public void testGetAllRating() {

        // Given.

        // When.
        when(ratingRepository.findAll()).thenReturn(ratingList);
        List<Rating> list = ratingService.getAll();

        // Then.
        assertEquals(ratingList.size(), list.size());
        verify(ratingRepository, times(1)).findAll();


    }
    @Test
    @DisplayName("get Rating by id")
    public void testGetRatingById() {

        // Given.

        // When.
        when(ratingRepository.findById(1)).thenReturn(Optional.ofNullable(rating1));
        Rating res = ratingService.get(1);

        // Then.
        assertEquals(res.getFitchRating(), rating1.getFitchRating());
        assertEquals(res.getMoodysRating(), rating1.getMoodysRating());
        verify(ratingRepository, times(1)).findById(1);


    }

    @Test
    @DisplayName("save Rating")
    public void testSaveRating() {

        // Given.
        Rating rating2 = new Rating();

        rating2.setId(3);
        rating2.setMoodysRating("Moodys Rating 2");
        rating2.setSandPRating("Sand PRating 2");
        rating2.setFitchRating("Fitch Rating 2");
        rating2.setOrderNumber(3);
        // When.
        ratingService.save(rating2);

        // Then.
        verify(ratingRepository, times(1)).save(rating2);


    }
    @Test
    @DisplayName("delete Rating")
    public void testDeleteRating() {

        // Given.

        // When.
        ratingService.delete(3);

        // Then.
        verify(ratingRepository, times(1)).deleteById(3);


    }


}
