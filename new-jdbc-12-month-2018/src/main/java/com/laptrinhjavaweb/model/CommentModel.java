package com.laptrinhjavaweb.model;

public class CommentModel extends AbstarctModel<CommentModel> {
	private String content;
	private long userId;
	private long newId;
    private String nameUser;
    private long parentId;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public long getNewId() {
		return newId;
	}

	public void setNewId(long newId) {
		this.newId = newId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}
