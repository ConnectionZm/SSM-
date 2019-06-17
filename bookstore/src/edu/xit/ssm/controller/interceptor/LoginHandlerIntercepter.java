package edu.xit.ssm.controller.interceptor;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginHandlerIntercepter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestURI = request.getRequestURI(); 
		 String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
//		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 
//             //如果是ajax请求响应头会有，x-requested-with  
//             return true;
//             //response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
//         }
		 HttpSession session = request.getSession();
		//如果获取到的这个url路径是在查询后台数据的方法的时候,进行拦截
        if(requestURI.indexOf("insertToCart.action")>0||requestURI.indexOf("queryCart.action")>0||requestURI.indexOf("queryOrder.action")>0
        		||requestURI.indexOf("deleteCart.action")>0||requestURI.indexOf("deleteCart1.action")>0||requestURI.indexOf("selectAccountByUid.action")>0
        		||requestURI.indexOf("deleteAllCart.action")>0||requestURI.indexOf("deleteCart1.action")>0||requestURI.indexOf("queryOrder.action")>0
        		||requestURI.indexOf("insertOrder.action")>0||requestURI.indexOf("insertOneOrder")>0) {  
            Object user = session.getAttribute("user");
            	if(user!=null){  
                    //登陆成功的用户  
                    return true;  
                }else{  
                   //没有登陆，转向登陆界面  
                	if (request.getHeader("x-requested-with")!= null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                	  ServletOutputStream out = response.getOutputStream();
                      out.print("unlogin");//返回给前端页面的未登陆标识
                      out.flush();
                      out.close();
                	}
                    request.getRequestDispatcher("/login.jsp").forward(request,response);  
                    return false;
                }  
            
        }else {
        	//如果不是以上两种方法的话,都进行放过
        	return true;  
        }
		
   
	}
	//对于请求是ajax请求重定向问题的处理方法
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //获取当前请求的路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
        //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理 否则直接重定向就可以了
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            //告诉ajax我是重定向
            response.setHeader("REDIRECT", "REDIRECT");
            //告诉ajax我重定向的路径
            response.setHeader("CONTENTPATH", basePath+"/login.html");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            response.sendRedirect(basePath + "/login.html");
        }
    }

}

