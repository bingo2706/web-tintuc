package com.laptrinhjavaweb.controller.admin.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.model.categoryModel;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.utils.HttpUtil;
import com.laptrinhjavaweb.utils.SessionUtil;

@WebServlet(urlPatterns = { "/api-admin-category" })
public class CategoryApi extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Inject
    private ICategoryService categoryService;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   ObjectMapper mapper = new ObjectMapper();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			 categoryModel categoryModel =  HttpUtil.of(request.getReader()).toModel(categoryModel.class);
			 categoryModel.setCreatedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUsername());
			 categoryModel = categoryService.save(categoryModel);
			 mapper.writeValue(response.getOutputStream(), categoryModel);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		categoryModel categoryModel =  HttpUtil.of(request.getReader()).toModel(categoryModel.class);
		categoryModel.setModifiedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUsername()); 
		categoryModel = categoryService.Update(categoryModel);
		 mapper.writeValue(response.getOutputStream(), categoryModel);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		categoryModel deleteCategory =  HttpUtil.of(request.getReader()).toModel(categoryModel.class);
		categoryService.delete(deleteCategory.getIds());
		 mapper.writeValue(response.getOutputStream(), "{}");
		
	}
	
	
}
