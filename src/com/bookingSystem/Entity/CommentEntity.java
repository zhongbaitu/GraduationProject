package com.bookingSystem.Entity;

import android.graphics.Bitmap;

public class CommentEntity {

	private Bitmap userImage;
	private String comment;
	private String commentTime;
	private boolean isMyMessage;

	public Bitmap getUserImage() {
		return userImage;
	}

	public void setUserImage(Bitmap userImage) {
		this.userImage = userImage;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public boolean getIsMyMessage() {
		return isMyMessage;
	}

	public boolean setIsMyMessage(boolean isMyMessage) {
		return this.isMyMessage = isMyMessage;
	}

}
