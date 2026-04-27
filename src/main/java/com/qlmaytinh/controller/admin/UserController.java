package com.qlmaytinh.controller.admin;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.mapstruct.factory.Mappers;

import com.qlmaytinh.Util.FormUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.request.PagingRequest;
import com.qlmaytinh.dto.response.PagingResponse;
import com.qlmaytinh.dto.response.UserResponse;
import com.qlmaytinh.mapper.PagingMapper;
import com.qlmaytinh.paging.PageRequest;
import com.qlmaytinh.paging.Pageble;
import com.qlmaytinh.service.UserService;
import com.qlmaytinh.sort.Sorter;

@WebServlet(urlPatterns = {"/admin-users"})
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    private PagingMapper mapperPaging = Mappers.getMapper(PagingMapper.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String type = req.getParameter("type");
        PagingRequest pagingRequest = FormUtil.toModel(PagingRequest.class, req);
        if (type == null || type.equals("list")) {

            PagingResponse<UserResponse> pagingResponse = new PagingResponse<>();

            Pageble pageble = PageRequest.builder()
                    .page(pagingRequest.getPage())
                    .sorter(Sorter.builder()
                            .sortName(pagingRequest.getSortName())
                            .sortBy(pagingRequest.getSortBy())
                            .build())
                    .maxPageItem(pagingRequest.getMaxPageItem())
                    .build();

            pagingResponse.setListResult(
                    userService.findAll(pageble)
            );
            pagingRequest.setTotalItem(
            			userService.countUsers()
            );
            pagingRequest.setTotalPage(
                    (int) Math.ceil((double) pagingRequest.getTotalItem() / pagingRequest.getMaxPageItem())
            );

            mapperPaging.toPagingResponse(pagingResponse, pagingRequest);

            req.setAttribute(SystemConstant.MODEL, pagingResponse);

            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/list.jsp");
            rd.forward(req, resp);
        }
        else if ("edit".equals(type)) {
            UserResponse model = new UserResponse();

            if (pagingRequest.getId() != null) {
                model = userService.findOne(pagingRequest.getId());
            }

            req.setAttribute(SystemConstant.MODEL, model);

            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/edit.jsp");
            rd.forward(req, resp);
        }
    }
}