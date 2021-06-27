package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.categoryModel;


public interface ICategoryDAO extends GenericDAO<categoryModel>{
	List<categoryModel> findAll();
	categoryModel findOneByCode(String code);
	categoryModel findOne(Long id);
	
	void Update(categoryModel categoryModel);
	void Delete(Long id);
	Long save(categoryModel categoryModel);
}
