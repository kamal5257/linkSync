package com.linkSync.dto;

public class CollaborationDTO {
    private Long collaborationId;
    private Long startupId1; // Reference to the first startup
    private Long startupId2; // Reference to the second startup
    private String description;
    private String status; // Can use Enum for better type safety
    
	public Long getCollaborationId() {
		return collaborationId;
	}
	public void setCollaborationId(Long collaborationId) {
		this.collaborationId = collaborationId;
	}
	public Long getStartupId1() {
		return startupId1;
	}
	public void setStartupId1(Long startupId1) {
		this.startupId1 = startupId1;
	}
	public Long getStartupId2() {
		return startupId2;
	}
	public void setStartupId2(Long startupId2) {
		this.startupId2 = startupId2;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}

