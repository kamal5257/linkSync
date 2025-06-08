package com.linkSync.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Table(name="organization")
@Entity
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orgId;
	private String orgName;
	@Column(columnDefinition = "TEXT")
	private String description;
	private Integer totalMembers;
	private Integer activeProjects;	
	private Date createdAt;
	private Date updatedAt;
	private String logoUrl;
	private String contactEmail;
	private Boolean isActive;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="creator_user_id", nullable=false)
	private User creatorUser;
	
	@Column(columnDefinition = "JSON")
	private String settingJson;
	
	@OneToMany(mappedBy = "org", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<OrgMember> orgMembers = new HashSet<>();
	
	@OneToMany(mappedBy = "org", cascade = CascadeType.ALL, orphanRemoval =  true)
	private Set<Project> projects = new HashSet<>();
	
	@OneToMany(mappedBy = "org", cascade = CascadeType.ALL, orphanRemoval =  true)
	private Set<Invitation> invitations = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="plan_id", nullable=false)
	private SubscriptionPlan plan;
}
