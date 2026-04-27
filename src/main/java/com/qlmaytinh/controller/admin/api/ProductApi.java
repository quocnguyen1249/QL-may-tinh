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
import com.qlmaytinh.dto.request.ProductCreationRequest;
import com.qlmaytinh.dto.request.ProductUpdateRequest;
import com.qlmaytinh.dto.response.ApiResponse;
import com.qlmaytinh.dto.response.ProductResponse;
import com.qlmaytinh.service.ProductService;


@WebServlet(urlPatterns = "/api-admin-product")
public class ProductApi extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		ApiResponse<ProductResponse> apiResponse;
		try {
			ProductCreationRequest product =
					HttpUtil.of(req.getReader()).toModel(ProductCreationRequest.class);
			ValidationUtil.validate(product);
			ProductResponse productResponse = productService.save(product);
			apiResponse = ApiResponse.<ProductResponse>builder()
					.alert(SystemConstant.SUCCESS)
					.messageText("Tạo sản phẩm thành công!")
					.entity(productResponse)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<ProductResponse>builder() 
					.alert(SystemConstant.DANGER) 
					.messageText(e.getMessage()) 
					.entity(null) 
					.build(); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		ApiResponse<ProductResponse> apiResponse;
		try {
			ProductUpdateRequest productUpdate =
					HttpUtil.of(req.getReader()).toModel(ProductUpdateRequest.class);
			ProductResponse productResponse = productService.update(productUpdate);
			apiResponse = ApiResponse.<ProductResponse>builder()
					.alert(SystemConstant.SUCCESS)
					.messageText("Cập nhật sản phẩm thành công!")
					.entity(productResponse)
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<ProductResponse>builder()
					.alert(SystemConstant.DANGER)
					.messageText(e.getMessage())
					.entity(null)
					.build();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		ApiResponse<ProductResponse> apiResponse;

		try {
			DeleteRequest deleteRequest =
					HttpUtil.of(req.getReader()).toModel(DeleteRequest.class);

			productService.delete(deleteRequest.getIds());

			apiResponse = ApiResponse.<ProductResponse>builder()
					.alert(SystemConstant.SUCCESS)
					.messageText("Xóa sản phẩm thành công!")
					.entity(null)
					.build();

			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (RuntimeException e) {
			apiResponse = ApiResponse.<ProductResponse>builder() 
					.alert(SystemConstant.DANGER) 
					.messageText(e.getMessage()) 
					.entity(null) 
					.build(); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		mapper.writeValue(resp.getOutputStream(), apiResponse);
	}
}
