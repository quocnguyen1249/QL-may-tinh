package com.qlmaytinh.controller.web.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapstruct.factory.Mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlmaytinh.Util.HttpUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.request.OrderUpdateRequest;
import com.qlmaytinh.dto.response.ApiResponse;
import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.mapper.UserMapper;
import com.qlmaytinh.service.OrderService;
import com.qlmaytinh.service.UserService;

@WebServlet(urlPatterns = "/api-order-user")
public class OrderApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
	private UserService userService = new UserService();
	
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    req.setCharacterEncoding("UTF-8");
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    ApiResponse<Object> apiResponse;

	    try {
	        OrderUpdateRequest request = HttpUtil.of(req.getReader()).toModel(OrderUpdateRequest.class);

	        UserEntity user = (UserEntity) req.getSession().getAttribute(SystemConstant.USERENTITY);
	        if (!userService.isUserInfoComplete(user.getId())) {
	            throw new RuntimeException("Thông tin chưa đầy đủ");
	        }

	        orderService.payOrder(request.getId());

	        apiResponse = ApiResponse.builder()
	                .alert(SystemConstant.SUCCESS)
	                .messageText("Thanh toán đơn hàng thành công!")
	                .entity(null)
	                .build();
	        resp.setStatus(HttpServletResponse.SC_OK);

	    } catch (RuntimeException e) {
	        UserEntity user = (UserEntity) req.getSession().getAttribute(SystemConstant.USERENTITY);

	        apiResponse = ApiResponse.builder()
	                .alert(SystemConstant.DANGER)
	                .messageText(e.getMessage())
	                .entity(userService.findOne(user.getId()))
	                .build();
	        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }

	    mapper.writeValue(resp.getOutputStream(), apiResponse);
	}
}
