package com.linkSync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkSync.entity.Startup;
import com.linkSync.entity.User;

public interface StartupRepository extends JpaRepository<Startup, Long> {
    List<Startup> findByUser(User user);
}

