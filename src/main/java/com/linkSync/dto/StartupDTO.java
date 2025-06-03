package com.linkSync.dto;

public class StartupDTO {
    private Long startupId;
    private Long userId; // Reference to the user
    private String name;
    private String description;
    private String businessModel;
    private String industry;
    private String status;
    
	public Long getStartupId() {
		return startupId;
	}
	public void setStartupId(Long startupId) {
		this.startupId = startupId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBusinessModel() {
		return businessModel;
	}
	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 

}

