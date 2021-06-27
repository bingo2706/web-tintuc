package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.dao.INewDAO;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.categoryModel;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.INewService;

public class NewService implements INewService{
	@Inject
	private INewDAO NewDao;
	@Inject
	private ICategoryDAO categoryDAO;
	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		
		return NewDao.findByCategoryId(categoryId);
	}
	@Override
	public NewModel save(NewModel newModel) {
		newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		categoryModel category = categoryDAO.findOneByCode(newModel.getCategoryCode());
		newModel.setCategoryid(category.getId());
		Long newId = NewDao.save(newModel);
		return NewDao.findOne(newId);
	}
	@Override
	public NewModel update(NewModel updateNew) {
		NewModel oldNew = NewDao.findOne(updateNew.getId());
		updateNew.setCreatedDate(oldNew.getCreatedDate());
		updateNew.setCreatedBy(oldNew.getCreatedBy());
		updateNew.setModifieddate(new Timestamp(System.currentTimeMillis()));
		categoryModel category = categoryDAO.findOneByCode(updateNew.getCategoryCode());
		updateNew.setCategoryid(category.getId());
		NewDao.update(updateNew);
		return NewDao.findOne(updateNew.getId());
	}
	@Override
	public void delete(long[] ids) {
		for (long id: ids) {	
			NewDao.delete(id);
		}
	}
	@Override
	public List<NewModel> findAll(Pageble pageble) {
		
		return NewDao.findAll(pageble);
	}
	@Override
	public int getCountItems() {
		// TODO Auto-generated method stub
		return NewDao.getCountItems();
	}
	@Override
	public NewModel findOne(Long id) {
		NewModel newModel = NewDao.findOne(id);
        categoryModel categoryModel = categoryDAO.findOne(newModel.getCategoryid());
        newModel.setCategoryCode(categoryModel.getCode());
        return newModel;
	}

}
