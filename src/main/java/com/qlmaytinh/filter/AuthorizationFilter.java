package com.qlmaytinh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qlmaytinh.Util.SessionUtil;
import com.qlmaytinh.constant.SystemConstant;
import com.qlmaytinh.dto.response.ApiResponse;
import com.qlmaytinh.entity.UserEntity;

public class AuthorizationFilter implements Filter {
	private ServletContext context;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.context = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
	        throws IOException, ServletException {

	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) resp;

	    String url = request.getRequestURI()
	            .replaceFirst(request.getContextPath(), "");
	    if (!url.startsWith("/admin")) {
	        chain.doFilter(request, response);
	        return;
	    }
	    if (url.startsWith("/dang-nhap") || url.startsWith("/dang-ky")) {
	        chain.doFilter(request, response);
	        return;
	    }
	    UserEntity user = (UserEntity) SessionUtil.getInstance()
	            .getValue(request, SystemConstant.USERENTITY);
	    if (user == null) {
	        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
	                .alert(SystemConstant.DANGER)
	                .messageText("Bạn chưa đăng nhập")
	                .build();
	        request.setAttribute("apiResponse", apiResponse);
	        SessionUtil.getInstance().putValue(request, "apiResponse", apiResponse);
	        response.sendRedirect(request.getContextPath() + "/dang-nhap?action=login");
	        return;
	    }
	    String roleCode = user.getRoleEntity().getCode();
	    if (!SystemConstant.ADMIN.equals(roleCode)) {
	        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
	                .alert(SystemConstant.DANGER)
	                .messageText("Bạn không có quyền truy cập trang này")
	                .build();
	        SessionUtil.getInstance().putValue(request, "apiResponse", apiResponse);
	        response.sendRedirect(request.getContextPath() + "/dang-nhap?action=login");
	        return;
	    }
	    chain.doFilter(request, response);
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}