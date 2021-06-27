package com.laptrinhjavaweb.controller.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.SystemConstand.SystemConstand;
import com.laptrinhjavaweb.model.CommentModel;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICommentService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.CategoryService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.SessionUtil;
@WebServlet(urlPatterns = {"/trang-chu","/login","/logout","/detail"})
public class HomeController extends HttpServlet{
    @Inject
    private CategoryService categoryServicel;
	@Inject
    private INewService newService;
	@Inject
    private IUserService userService;
	@Inject
	private ICommentService commentService;
	private static final long serialVersionUID = 1L;
    ResourceBundle myBundle = ResourceBundle.getBundle("message");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action != null && action.equals("login")){
			String message = request.getParameter("message");
			String alert = request.getParameter("alert");
			if(message !=null && alert !=null){
				request.setAttribute("message", myBundle.getString(message));
				request.setAttribute("alert", "danger");
			}
			RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
			rd.forward(request, response);
		}else if(action != null && action.equals("logout")){
			SessionUtil.getInstance().removeValue(request, "USERMODEL");
			response.sendRedirect(request.getContextPath()+SystemConstand.HOMELINK);
		}
		else if(action != null && action.equals("category")){
			NewModel newModel = new NewModel();
			Long categoryId = Long.parseLong(request.getParameter("id")) ;
			newModel.setListResult(newService.findByCategoryId(categoryId));
			request.setAttribute("list", newModel);
			request.setAttribute("categories", categoryServicel.findAll());
			RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(request, response);
		}
		else if(action != null && action.equals("detail")){
			CommentModel commentModel = new CommentModel();
			CommentModel childCommentModel = new CommentModel();
			String id = request.getParameter("id");
			
			NewModel newModel = new NewModel();
			newModel = newService.findOne(Long.parseLong(id));
			commentModel.setListResult(commentService.findAll(Long.parseLong(id)));
			childCommentModel.setListResult(commentService.findAll(Long.parseLong(id)));
			request.setAttribute("item", newModel);
			request.setAttribute("comment", commentModel);
			request.setAttribute("child", childCommentModel);
			RequestDispatcher rd = request.getRequestDispatcher("/views/detail.jsp");
			rd.forward(request, response);
		}
		else{
			NewModel newModel = FormUtil.toModel(NewModel.class, request);
			Pageble pageble = new PageRequest(newModel.getPage(),newModel.getMaxPageItem(), new Sorter(newModel.getSortName(), newModel.getSortBy()));
			newModel.setListResult(newService.findAll(pageble));
			newModel.setTotalItem(newService.getCountItems());
			newModel.setTotalPage((int) Math.ceil((double) newModel.getTotalItem()/newModel.getMaxPageItem()));
			request.setAttribute("categories", categoryServicel.findAll());
			request.setAttribute("list", newModel);
			
			RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(request, response);
			
			
		}
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action != null && action.equals("login")){
			UserModel userModel = FormUtil.toModel(UserModel.class, request);
			userModel = userService.findByUserNameAndPasswordAndStatus(userModel.getUsername(), userModel.getPassword(), 1);
			if(userModel !=null){
				SessionUtil.getInstance().putValue(request,"USERMODEL",userModel );
				if(userModel.getRole().getCode().equals("USER")){
					response.sendRedirect(request.getContextPath()+SystemConstand.HOMELINK);
				}else if(userModel.getRole().getCode().equals("ADMIN")){
					response.sendRedirect(request.getContextPath()+"/admin-home");
				}
				
			}else{
				response.sendRedirect(request.getContextPath()+"/login?action=login&message=username_password_invalid&alert=danger");
				
			}
		}
	}
}
