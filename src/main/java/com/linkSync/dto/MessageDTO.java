package com.linkSync.dto;

public class MessageDTO {
	private Long messageId;
	private Long collaborationId;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getCollaborationId() {
		return collaborationId;
	}

	public void setCollaborationId(Long collaborationId) {
		this.collaborationId = collaborationId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private Long senderId;
	private String content;

}
