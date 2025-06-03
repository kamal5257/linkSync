package com.linkSync.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "collaborations")
public class Collaboration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collaborationId;

    @ManyToOne
    @JoinColumn(name = "startup_id_1", nullable = false)
    private Startup startup1;

    @ManyToOne
    @JoinColumn(name = "startup_id_2", nullable = false)
    private Startup startup2;

    private String description;

    @Enumerated(EnumType.STRING)
    private CollaborationStatus status = CollaborationStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

	public Long getCollaborationId() {
		return collaborationId;
	}

	public void setCollaborationId(Long collaborationId) {
		this.collaborationId = collaborationId;
	}

	public Startup getStartup1() {
		return startup1;
	}

	public void setStartup1(Startup startup1) {
		this.startup1 = startup1;
	}

	public Startup getStartup2() {
		return startup2;
	}

	public void setStartup2(Startup startup2) {
		this.startup2 = startup2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CollaborationStatus getStatus() {
		return status;
	}

	public void setStatus(CollaborationStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    // Getters and Setters
    
}

enum CollaborationStatus {
    PENDING,
    ACTIVE,
    COMPLETED,
    CANCELLED
}

