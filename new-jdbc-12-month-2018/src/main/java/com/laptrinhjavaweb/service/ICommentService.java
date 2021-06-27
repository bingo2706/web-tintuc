package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.model.CommentModel;

public interface ICommentService {
	List<CommentModel> findAll(Long newId);
	CommentModel save(CommentModel comment);
	void delete(long[] ids);
	CommentModel Update(CommentModel commentModel);
}
