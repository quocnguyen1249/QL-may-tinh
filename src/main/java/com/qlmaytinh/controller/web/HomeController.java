package com.qlmaytinh.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mapstruct.factory.Mappers;

import com.qlmaytinh.Util.FormUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.request.PagingRequest;
import com.qlmaytinh.dto.response.PagingResponse;
import com.qlmaytinh.dto.response.ProductResponse;
import com.qlmaytinh.entity.UserEntity;
import com.qlmaytinh.mapper.PagingMapper;
import com.qlmaytinh.paging.PageRequest;
import com.qlmaytinh.paging.Pageble;
import com.qlmaytinh.service.OrderService;
import com.qlmaytinh.service.ProductService;
import com.qlmaytinh.sort.Sorter;

@WebServlet(urlPatterns = {"/trang-chu"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = -36154563743802162L;
    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();
    private PagingMapper mapperPaging = Mappers.getMapper(PagingMapper.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PagingRequest pagingRequest = FormUtil.toModel(PagingRequest.class, req);
        PagingResponse<ProductResponse> pagingResponse = new PagingResponse<>();
        Pageble pageble = PageRequest.builder()
                .page(pagingRequest.getPage())
                .sorter(Sorter.builder()
                        .sortName(pagingRequest.getSortName())
                        .sortBy(pagingRequest.getSortBy())
                        .build())
                .maxPageItem(pagingRequest.getMaxPageItem())
                .build();

        String keyword = pagingRequest.getKeyword();
        String[] brandsArr = pagingRequest.getBrands();
        List<String> listBrand = brandsArr != null ? Arrays.asList(brandsArr) : new ArrayList<>();
        String[] filtersArr = pagingRequest.getFilters();
        List<String> filters = filtersArr != null ? Arrays.asList(filtersArr) : new ArrayList<>();

        Double minPrice = pagingRequest.getMinPrice() == null ? 0.0 : pagingRequest.getMinPrice();
        Double maxPrice = pagingRequest.getMaxPrice() == null ? 100000000.0 : pagingRequest.getMaxPrice();
        pagingRequest.setMinPrice(minPrice);
        pagingRequest.setMaxPrice(maxPrice);

        Double minDiscount = pagingRequest.getMinDiscount();
        Double maxDiscount = pagingRequest.getMaxDiscount();
        if (minDiscount != null) minDiscount = minDiscount / 100;
        if (maxDiscount != null) maxDiscount = maxDiscount / 100;

        pagingResponse.setListResult(
                productService.findAll(
                        keyword,
                        listBrand,
                        minPrice,
                        maxPrice,
                        minDiscount,
                        maxDiscount,
                        filters,
                        pageble)
        );

        pagingRequest.setTotalItem(
                productService.countFilterProducts(
                        keyword,
                        listBrand,
                        minPrice,
                        maxPrice,
                        minDiscount,
                        maxDiscount,
                        filters)
        );
        pagingRequest.setTotalPage(
                (int) Math.ceil((double) pagingRequest.getTotalItem() / pagingRequest.getMaxPageItem())
        );
        Object userObj = req.getSession().getAttribute(SystemConstant.USERENTITY);
        int orderCount = 0;

        if (userObj != null) {
            UserEntity user = (UserEntity) userObj;
            orderCount = orderService.countByUserId(user.getId());
        }

        req.setAttribute("orderCount", orderCount);
        
        
        
        mapperPaging.toPagingResponse(pagingResponse, pagingRequest);
        req.setAttribute(SystemConstant.BRANDS, productService.findAllBrands());
        req.setAttribute(SystemConstant.MODEL, pagingResponse);

        RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }
}