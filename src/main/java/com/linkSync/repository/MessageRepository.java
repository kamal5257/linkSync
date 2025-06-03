package com.linkSync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkSync.entity.Collaboration;
import com.linkSync.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByCollaboration(Collaboration collaboration);
}

