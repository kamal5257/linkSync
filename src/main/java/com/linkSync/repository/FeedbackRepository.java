package com.linkSync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkSync.entity.Feedback;
import com.linkSync.entity.Startup;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStartup(Startup startup);
}

