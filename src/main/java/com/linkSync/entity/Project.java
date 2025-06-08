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

@Table(name="project")
@Data
@Setter
@Getter
@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id", nullable=false)
	private Organization org;
	
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable=false)
	private User leadUser;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private Date startDate;
	private Date endDate;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Repository> repositories = new HashSet<>();
}
