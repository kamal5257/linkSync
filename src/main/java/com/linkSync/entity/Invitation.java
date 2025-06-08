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

@Table(name="invitation")
@Data
@Setter
@Getter
@Entity
public class Invitation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer invitationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id", nullable=false)
	private Organization org;
	private String invitedEmail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invited_by_user_id", nullable=false)
	private User invitedByUser;
	private String initialRole;
	private String invitationToken;
	private String status;
	private Date createAt;
}
