package com.linkSync.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(unique = true, nullable = false, name="username")
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String passwordHash;

	private String profilePicture;
	private String bio;
	private String firstName;
	private String lastName;
	private Date lastLoginAt;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt = LocalDateTime.now();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval =  true)
	private Set<OrgMember> orgMemberShips = new HashSet<>();
	
	@OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL, orphanRemoval =  true)
	private Set<Organization> createdOrg = new HashSet<>();
	
	@OneToMany(mappedBy = "leadUser", cascade = CascadeType.ALL, orphanRemoval =  true)
	private Set<Project> projectsLead = new HashSet<>();
	
	@OneToMany(mappedBy = "invitedByUser", cascade = CascadeType.ALL, orphanRemoval =  true)
	private Set<Invitation> sentInvitations = new HashSet<>();
		
}
