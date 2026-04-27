package com.qlmaytinh.controller.api;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlmaytinh.Util.HttpUtil;
import com.qlmaytinh.Util.ValidationUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.request.DeleteRequest;
import com.qlmaytinh.dto.request.OrderCreateRequest;
import com.qlmaytinh.dto.request.OrderUpdateRequest;
import com.qlmaytinh.dto.response.ApiResponse;
import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.service.OrderService;

@WebServlet(urlPatterns = "/api-order")
public class OrderApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    req.setCharacterEncoding("UTF-8");
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    ApiResponse<OrderResponse> apiResponse;

	    try {
		    	HttpSession session = req.getSession(false);
		    	UserEntity user = (session != null)
		    	        ? (UserEntity) session.getAttribute(SystemConstant.USERENTITY)
		    	        : null;
	
		    	if (user == null) {
		    	    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    	    apiResponse = ApiResponse.<OrderResponse>builder()
		    	            .alert(SystemConstant.DANGER)
		    	            .messageText("Bạn cần đăng nhập trước khi đặt hàng!")
		    	            .entity(null)
		    	            .build();
		    	    new ObjectMapper().writeValue(resp.getOutputStream(), apiResponse);
		    	    return;
		    	}
	        OrderCreateRequest request = HttpUtil.of(req.getReader()).toModel(OrderCreateRequest.class);
	        ValidationUtil.validate(request);
	        
	        request.setUserId(user.getId());
	        OrderResponse orderResponse = orderService.createOrder(request);

	        apiResponse = ApiResponse.<OrderResponse>builder()
	                .alert(SystemConstant.SUCCESS)
	                .messageText("Tạo order thành công!")
	                .entity(orderResponse)
	                .build();
	        resp.setStatus(HttpServletResponse.SC_OK);

	    } catch (RuntimeException e) {
	        apiResponse = ApiResponse.<OrderResponse>builder()
	                .alert(SystemConstant.DANGER)
	                .messageText(e.getMessage())
	                .entity(null)
	                .build();
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
		ApiResponse<OrderResponse> apiResponse;

		try {
			OrderUpdateRequest request = HttpUtil.of(req.getReader()).toModel(OrderUpdateRequest.class);
			ValidationUtil.validate(request);

			if (!"PENDING".equals(request.getStatus())) {
			    throw new RuntimeException("Chỉ đơn hàng chưa thanh toán mới được chỉnh sửa!");
			}
			OrderResponse orderResponse = orderService.update(request);

			apiResponse = ApiResponse.<OrderResponse>builder()
					.alert(SystemConstant.SUCCESS)
					.messageText("Cập nhật order thành công!")
					.entity(orderResponse)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<OrderResponse>builder()
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
		ApiResponse<OrderResponse> apiResponse;

		try {
			DeleteRequest deleteRequest = HttpUtil.of(req.getReader()).toModel(DeleteRequest.class);
			orderService.delete(deleteRequest.getIds());

			apiResponse = ApiResponse.<OrderResponse>builder()
					.alert(SystemConstant.SUCCESS)
					.messageText("Xóa order thành công!")
					.entity(null)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<OrderResponse>builder()
					.alert(SystemConstant.DANGER)
					.messageText(e.getMessage())
					.entity(null)
					.build();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}
}

