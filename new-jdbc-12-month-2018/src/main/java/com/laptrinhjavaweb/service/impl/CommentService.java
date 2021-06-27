package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.ICommentDAO;
import com.laptrinhjavaweb.model.CommentModel;
import com.laptrinhjavaweb.service.ICommentService;

public class CommentService implements ICommentService{
    @Inject
    private ICommentDAO commentDao;
	@Override
	public List<CommentModel> findAll(Long newId) {
		
		return commentDao.findAll(newId);
	}

	@Override
	public CommentModel save(CommentModel comment) {
		comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		Long commentId = commentDao.save(comment);
		return commentDao.findOne(commentId);
	}

	@Override
	public void delete(long[] ids) {
		for (long id: ids) {	
			commentDao.Delete(id);
		}
		
	}

	@Override
	public CommentModel Update(CommentModel commentModel) {
		CommentModel oldComment = commentDao.findOne(commentModel.getId());
		commentModel.setCreatedDate(oldComment.getCreatedDate());
		commentModel.setCreatedBy(oldComment.getCreatedBy());
		commentModel.setModifieddate(new Timestamp(System.currentTimeMillis()));
		commentDao.Update(commentModel);
		return commentDao.findOne(commentModel.getId());
	}

}
