package com.jakub.chatbot.repository;

import com.jakub.chatbot.entity.Movie;
import com.jakub.chatbot.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {

	List<Rating> findByMovie(Movie movie);

}
