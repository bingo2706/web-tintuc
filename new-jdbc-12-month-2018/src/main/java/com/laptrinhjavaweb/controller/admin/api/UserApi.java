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
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.utils.HttpUtil;
import com.laptrinhjavaweb.utils.SessionUtil;

@WebServlet(urlPatterns = { "/api-admin-user" })
public class UserApi extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	@Inject
    private IUserService userService;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   ObjectMapper mapper = new ObjectMapper();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			 UserModel userModel =  HttpUtil.of(request.getReader()).toModel(UserModel.class);
			 userModel.setCreatedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUsername());
			 userModel = userService.save(userModel);
			 mapper.writeValue(response.getOutputStream(), userModel);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		UserModel updateUser =  HttpUtil.of(request.getReader()).toModel(UserModel.class);
		updateUser.setModifiedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUsername()); 
		updateUser = userService.Update(updateUser);
		 mapper.writeValue(response.getOutputStream(), updateUser);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		UserModel deleteUser =  HttpUtil.of(request.getReader()).toModel(UserModel.class);
		userService.delete(deleteUser.getIds());
		 mapper.writeValue(response.getOutputStream(), "{}");
	}
	
	
}
