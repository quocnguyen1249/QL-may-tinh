package com.qlmaytinh.controller.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapstruct.factory.Mappers;

import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.response.ProductResponse;
import com.qlmaytinh.mapper.ProductMapper;
import com.qlmaytinh.repository.ProductRepository;


@WebServlet(urlPatterns = {"/item"})
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductRepository productRepository = new ProductRepository();
	private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Long id = (long) Integer.parseInt(req.getParameter("id"));
		ProductResponse productResponse = mapper.toProductResponse(productRepository.findOne(id));
        req.setAttribute(SystemConstant.MODEL, productResponse);

        RequestDispatcher rd = req.getRequestDispatcher("/views/web/item.jsp");
        rd.forward(req, resp);
	}
}
