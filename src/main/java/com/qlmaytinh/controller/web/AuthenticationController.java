package com.qlmaytinh.controller.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapstruct.factory.Mappers;

import com.qlmaytinh.Util.FormUtil;
import com.qlmaytinh.Util.SessionUtil;
import com.qlmaytinh.Util.ValidationUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.request.LoginRequest;
import com.qlmaytinh.dto.response.ApiResponse;
import com.qlmaytinh.dto.response.LoginResponse;
import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.mapper.UserMapper;
import com.qlmaytinh.service.UserService;
@WebServlet(urlPatterns = {"/dang-nhap","/dang-ky","/thoat"})
public class AuthenticationController extends HttpServlet{
	private static final long serialVersionUID = -36154563743802162L;
	private static final String TRANGCHUMACDINH = "/trang-chu?page=1&maxPageItem=12&sortName=name&sortBy=desc&type=list";
	private UserMapper mapperUser = Mappers.getMapper(UserMapper.class);
	private UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String action = req.getParameter(SystemConstant.ACTION);

	    if (action != null && action.equals(SystemConstant.LOGOUT)) {
	        SessionUtil.getInstance().removeValue(req, SystemConstant.USERENTITY);
	        resp.sendRedirect(req.getContextPath() + TRANGCHUMACDINH);
	        return;
	    }

	    ApiResponse<?> apiResponse = (ApiResponse<?>) SessionUtil.getInstance().getValue(req, "apiResponse");
	    if (apiResponse != null) {
	        req.setAttribute("apiResponse", apiResponse);
	        SessionUtil.getInstance().removeValue(req, "apiResponse");
	    }
	    RequestDispatcher rd = req.getRequestDispatcher("/views/authentication/auth.jsp");
	    rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter(SystemConstant.ACTION);
		if(action != null && action.equals(SystemConstant.LOGIN)) {
			LoginRequest login = FormUtil.toModel(LoginRequest.class, req);
			try {
				ValidationUtil.validate(login);
				UserEntity userEntity = userService.findByUserNameAndPassword(login.getUsername(), login.getPassword());
				SessionUtil.getInstance().putValue(req, SystemConstant.USERENTITY, userEntity);
				if(userEntity.getRoleEntity().getCode().equals(SystemConstant.USER)) {
					resp.sendRedirect(req.getContextPath() + TRANGCHUMACDINH);
				} else if(userEntity.getRoleEntity().getCode().equals(SystemConstant.ADMIN)) {
					resp.sendRedirect(req.getContextPath() + "/admin-home");
				}
			} catch (RuntimeException e) {
				ApiResponse<LoginResponse> apiResponse;
				apiResponse = ApiResponse.<LoginResponse>builder()
											.alert(SystemConstant.DANGER) 
											.messageText(e.getMessage()) 
											.entity(mapperUser.toLoginResponse(login))
											.build();
//		        resp.setContentType("application/json");
//		        new ObjectMapper().writeValue(resp.getOutputStream(), apiResponse);
				req.setAttribute("apiResponse", apiResponse);
				RequestDispatcher rd = req.getRequestDispatcher("/views/authentication/auth.jsp");
	            rd.forward(req, resp);
			}
		} else{
			resp.sendRedirect(req.getContextPath() + TRANGCHUMACDINH);
		}
	}
	
}
