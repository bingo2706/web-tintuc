package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.CommentModel;

public interface ICommentDAO {
	List<CommentModel> findAll(Long newId);
	Long save(CommentModel comment);
	void Update(CommentModel comment);
	void Delete(Long id);
	CommentModel findOne(Long id);
}
