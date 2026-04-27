package com.qlmaytinh.controller.admin.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlmaytinh.Util.HttpUtil;
import com.qlmaytinh.Util.ValidationUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.request.DeleteRequest;
import com.qlmaytinh.dto.request.UserCreationRequest;
import com.qlmaytinh.dto.request.UserUpdateRequest;
import com.qlmaytinh.dto.response.ApiResponse;
import com.qlmaytinh.dto.response.UserResponse;
import com.qlmaytinh.service.UserService;


@WebServlet(urlPatterns = "/api-admin-user")
public class UserApi extends HttpServlet {
	private static final long serialVersionUID = -6748693091760711961L;
	private UserService userService = new UserService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		ApiResponse<UserResponse> apiResponse;
		try {
			UserCreationRequest user = HttpUtil.of(req.getReader()).toModel(UserCreationRequest.class);
			ValidationUtil.validate(user);
			UserResponse userResponse =  userService.save(user);
			apiResponse = ApiResponse.<UserResponse>builder()
					.alert(SystemConstant.SUCCESS) 
					.messageText("Tạo user thành công!")
					.entity(userResponse)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<UserResponse>builder() 
					.alert(SystemConstant.DANGER) 
					.messageText(e.getMessage()) 
					.entity(null) .build(); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} 
		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		ApiResponse<UserResponse> apiResponse;
		try {
			UserUpdateRequest userUpdate = HttpUtil.of(req.getReader()).toModel(UserUpdateRequest.class);
			UserResponse userResponse = userService.update(userUpdate);
			apiResponse = ApiResponse.<UserResponse>builder()
					.alert(SystemConstant.SUCCESS) 
					.messageText("Cập nhật user thành công!")
					.entity(userResponse)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<UserResponse>builder() 
					.alert(SystemConstant.DANGER) 
					.messageText(e.getMessage()) 
					.entity(null) 
					.build(); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		ApiResponse<UserResponse> apiResponse;
		try {
			DeleteRequest deleteRequest = HttpUtil.of(req.getReader()).toModel(DeleteRequest.class);
			userService.delete(deleteRequest.getIds());
			apiResponse = ApiResponse.<UserResponse>builder()
					.alert(SystemConstant.SUCCESS) 
					.messageText("Xóa user thành công!")
					.entity(null)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<UserResponse>builder() 
					.alert(SystemConstant.DANGER) 
					.messageText(e.getMessage()) 
					.entity(null)
					.build(); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}
}
