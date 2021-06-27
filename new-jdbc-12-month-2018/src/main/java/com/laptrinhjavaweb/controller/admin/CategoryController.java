package com.laptrinhjavaweb.controller.admin;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.model.categoryModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.IRoleService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.NewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;
@WebServlet(urlPatterns = {"/admin-category"})
public class CategoryController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Inject
	private ICategoryService categoryService;
	
	ResourceBundle myBundle = ResourceBundle.getBundle("message");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		categoryModel categoryModel = FormUtil.toModel(categoryModel.class, request);
		String view ="";
		if(categoryModel.getType().equals("list")){
			
			categoryModel.setListResult(categoryService.findAll());
			
			view = "/views/admin/category/list.jsp";
		}else if(categoryModel.getType().equals("edit")){
			if(categoryModel.getId() !=null){
				categoryModel = categoryService.findOne(categoryModel.getId());
			}
			
			view = "/views/admin/category/edit.jsp";
		}
		if(request.getParameter("message") != null){
			String messageReponse = "";
			String alert = "";
			String message = request.getParameter("message");
			if(message.equals("insert_success")){
				messageReponse = "Insert Success !!!";
				alert = "success";
			}
			else if(message.equals("error_system")){
				messageReponse = "Error System !!!";
				alert = "danger";
			}
			else if(message.equals("update_success")){
				messageReponse = "Update Sucess !!!";
				alert = "success";
			}
			else if(message.equals("delete_success")){
				messageReponse = "Delete Sucess !!!";
				alert = "success";
			}
			request.setAttribute("messageResponse", messageReponse);
			request.setAttribute("alert", alert);
		}
		
		
		request.setAttribute("list", categoryModel);
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}
