package com.para.springboot.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YixinZhang
 * @Date: Created in 14:43 2020/1/8
 * @Description: 登录检查
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    /*目标方法执行之前*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser =request.getSession().getAttribute("loginUser");
        Object msg =request.getSession().getAttribute("msg");

        /*if(loginUser==null && msg==null){
            //未登录，返回登录页面
            request.setAttribute("msg1","没有权限，请先登录。");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else if(loginUser!=null && msg!=null){

            return  false;
        }else{
            return true;
        }*/

        if(loginUser == null){

            //未登录状态
            //时间戳
            long timestamp= System.currentTimeMillis();

            //重定向到IAM系统进行登录
            response.sendRedirect("http://47.96.187.200/profile/oauth2/authorize?client_id=RywqJUEK6F" +
                    "&redirect_uri=http://127.0.0.1:8080/callback&oauth_timestamp="+String.valueOf(timestamp)+"&response_type=code");

            return false;
        }else{

            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*HttpSession session = request.getSession();
        Object msg = session.getAttribute("msg");
        if(msg!=null){
            session.setAttribute("msg",null);
        }*/
    }
}
