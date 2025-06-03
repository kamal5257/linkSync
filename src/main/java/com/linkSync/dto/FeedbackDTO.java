package com.linkSync.dto;

public class FeedbackDTO {
	private Long feedbackId;
	private Long startupId; // Reference to the startup
	private Long userId; // Reference to the user
	private int rating;
	private String comments;

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
