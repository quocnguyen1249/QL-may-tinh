package com.qlmaytinh.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlmaytinh.service.StatsService;

@WebServlet(urlPatterns = {"/admin-home"})
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 6044684852708998804L;

    private StatsService statsService = new StatsService();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var revenueByDate = statsService.getRevenueByDate();
        var statusRatio = statsService.getOrderStatusRatio();
        var topProducts = statsService.getTopSellingProducts();
        var brandRevenue = statsService.getRevenueByBrand();
        req.setAttribute("revenueByDateJson", mapper.writeValueAsString(revenueByDate));
        req.setAttribute("statusRatioJson", mapper.writeValueAsString(statusRatio));
        req.setAttribute("topProductsJson", mapper.writeValueAsString(topProducts));
        req.setAttribute("brandRevenueJson", mapper.writeValueAsString(brandRevenue));

        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/home.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }
}
