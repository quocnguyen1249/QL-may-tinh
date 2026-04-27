package com.qlmaytinh.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapstruct.factory.Mappers;

import com.qlmaytinh.Util.SessionUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.response.OrderResponse;
import com.qlmaytinh.dto.response.UserResponse;
import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.mapper.UserMapper;
import com.qlmaytinh.service.OrderService;

@WebServlet(urlPatterns = {"/tai-khoan", "/quan-ly-don-hang"})
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private OrderService orderService = new OrderService();
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	 	UserEntity userEntity = (UserEntity)
                 SessionUtil.getInstance()
                         .getValue(req, SystemConstant.USERENTITY);
         if (userEntity == null) {
             resp.sendRedirect(req.getContextPath() + "/dang-nhap");
             return;
         }
         UserResponse user = userMapper.toUserResponse(userEntity);
         
         req.setAttribute("orderCount", orderService.countByUserId(user.getId()));
         req.setAttribute("user", user);
        String action = req.getServletPath();
        switch (action) {
            case "/tai-khoan":
                RequestDispatcher accountView = req.getRequestDispatcher("/views/web/user/account.jsp");
                accountView.forward(req, resp);
                break;
            case "/quan-ly-don-hang":

                String status = req.getParameter("status");

                if (status == null || status.trim().isEmpty()) {
                    status = "PENDING";
                }

                List<OrderResponse> orders =
                        orderService.findByUserIdAndStatus(user.getId(), status);

                req.setAttribute("orders", orders);
                req.setAttribute("currentStatus", status);

                RequestDispatcher orderView = req.getRequestDispatcher("/views/web/user/order.jsp");
                orderView.forward(req, resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}