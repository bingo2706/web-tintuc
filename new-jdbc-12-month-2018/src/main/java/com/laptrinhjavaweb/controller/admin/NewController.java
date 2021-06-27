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

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;
@WebServlet(urlPatterns = {"/admin-news"})
public class NewController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Inject
    private INewService newService;
	@Inject
    private ICategoryService categoryService;
	ResourceBundle myBundle = ResourceBundle.getBundle("message");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameter rồi match với NewModel
		NewModel newModel = FormUtil.toModel(NewModel.class, request);
		String view ="";
		
		if(newModel.getType().equals("list")){
			Pageble pageble = new PageRequest(newModel.getPage(),newModel.getMaxPageItem(), new Sorter(newModel.getSortName(), newModel.getSortBy()));
			newModel.setListResult(newService.findAll(pageble));
			newModel.setTotalItem(newService.getCountItems());
			newModel.setTotalPage((int) Math.ceil((double) newModel.getTotalItem()/newModel.getMaxPageItem()));
			
			view = "/views/admin/new/list.jsp";
		}else if(newModel.getType().equals("edit")){
			if(newModel.getId() != null){
				newModel = newService.findOne(newModel.getId());
			}
			
			request.setAttribute("categories", categoryService.findAll());
			view = "/views/admin/new/edit.jsp";
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
		
		request.setAttribute("list", newModel);
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
