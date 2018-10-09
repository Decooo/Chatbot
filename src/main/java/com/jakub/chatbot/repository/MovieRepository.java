package com.jakub.chatbot.repository;

import com.jakub.chatbot.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
