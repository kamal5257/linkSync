package com.linkSync.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name="repository")
@Data
@Setter
@Getter
@Entity
public class Repository {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer repoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable=false)
	private Project project;
	private String name;
	private String description;
	private String visibility;
	private Date createdAt;
	private Date updatedAt;
	private String lastCommitHash;
	private String mainBranch;
}
