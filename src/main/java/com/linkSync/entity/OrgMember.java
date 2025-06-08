package com.linkSync.entity;

import java.util.Date;

import javax.annotation.Generated;
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

@Table(name="org_member")
@Data
@Getter
@Setter
@Entity
public class OrgMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orgMemberId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id", nullable=false)
	private Organization org;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable=false)
	private User user;
	private String role;
	private Date joinedAt;
	private Boolean isActive;
}
