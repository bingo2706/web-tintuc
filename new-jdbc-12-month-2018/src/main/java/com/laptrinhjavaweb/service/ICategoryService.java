package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.model.categoryModel;

public interface ICategoryService {
   List<categoryModel> findAll();
   categoryModel save(categoryModel categoryModel);
   void delete(long[] ids);
   categoryModel Update(categoryModel categoryModel);
   categoryModel findOne(Long id);
}
