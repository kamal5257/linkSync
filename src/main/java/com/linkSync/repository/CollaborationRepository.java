package com.linkSync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkSync.entity.Collaboration;
import com.linkSync.entity.Startup;

public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
    List<Collaboration> findByStartup1OrStartup2(Startup startup1, Startup startup2);
}

