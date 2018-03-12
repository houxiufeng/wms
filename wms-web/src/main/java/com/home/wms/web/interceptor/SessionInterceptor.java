package com.home.wms.web.interceptor;

import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.utils.AppConstants;
import com.home.wms.utils.AppContextManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by fitz on 2018/2/22.
 */
public class SessionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
		if(req.getSession().getAttribute(AppConstants.CURRENT_USER) == null) {
			PrintWriter out = res.getWriter();
			out.println("<html lang='en'>");
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("</head>");
			out.println("<script>");
			out.println("if(window.confirm('会话失效，请重新登录！')) {window.top.location.href = 'login';}");
			out.println("</script>");
			out.println("</html>");
			return false;
		}
		//把当前用户信息保存到threadlocal, 随时可取出来用
		AppContextManager.setCurrentUserInfo((CurrentUserInfo)req.getSession().getAttribute(AppConstants.CURRENT_USER));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
