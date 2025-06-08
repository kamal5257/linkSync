package com.linkSync.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name="subcription_plan")
@Data
@Setter
@Getter
@Entity
public class SubscriptionPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	private String name;
	private String description;
	private Integer maxMembers;
	private Integer maxProjects;
	private String storeLimitGb;
	private Double pricePerMonth;
	
	@OneToMany(mappedBy = "plan")
	private Set<Organization> organizations = new HashSet<>();
}
