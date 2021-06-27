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
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.IRoleService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.NewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;
@WebServlet(urlPatterns = {"/admin-users"})
public class UserController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Inject
	private IUserService userService;
	@Inject
	private IRoleService roleService;
	ResourceBundle myBundle = ResourceBundle.getBundle("message");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserModel userModel = FormUtil.toModel(UserModel.class, request);
		String view ="";
		if(userModel.getType().equals("list")){
			Pageble pageble = new PageRequest(userModel.getPage(),userModel.getMaxPageItem(), new Sorter(userModel.getSortName(), userModel.getSortBy()));
			userModel.setListResult(userService.findAll(pageble));
			userModel.setTotalItem(userService.getCountItems());
			userModel.setTotalPage((int) Math.ceil((double) userModel.getTotalItem()/userModel.getMaxPageItem()));
			view = "/views/admin/user/list.jsp";
		}else if(userModel.getType().equals("edit")){
			if(userModel.getId() !=null){
				userModel = userService.findOne(userModel.getId());
			}
			request.setAttribute("role", roleService.findAll());
			view = "/views/admin/user/edit.jsp";
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
		
		
		request.setAttribute("list", userModel);
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}
