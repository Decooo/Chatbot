package com.jakub.chatbot.repository;

import com.jakub.chatbot.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

	@Override
	Optional<Movie> findById(Integer integer);
}
