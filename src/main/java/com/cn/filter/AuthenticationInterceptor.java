package com.cn.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cn.bean.User;
import com.cn.element.PassToken;
import com.cn.element.UserLoginToken;
import com.cn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description 编写拦截器去获取token并验证token
 * @Author Wangbo
 * @Date 2019/11/21
 * @Version V1.0
 **/
//  实现一个拦截器就需要实现HandlerInterceptor接口
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    //  一、.boolean preHandle ()：
    //  预处理回调方法,实现处理器的预处理，第三个参数为响应的处理器,自定义Controller,返回值为true表示继续流程（如调用下一个拦截器或处理器）
    //  或者接着执行postHandle()和afterCompletion()；false表示流程中断，不会继续调用其他的拦截器或处理器，中断执行。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        System.out.println("开始token验证");
        String token = request.getHeader("token");  //  从http请求头中获取token
        System.out.println(token);
        //  如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //  检查是否有pass token注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //  检查有没有需要用户权限的注释
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                //   执行认证
                if (token == null) {
                    throw new RuntimeException("没有token令牌，请重新登陆");
                }
                //  获取token中的userId
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    throw new RuntimeException("获取token中的userId失败");
                }
                //  根据 id 获取用户信息
                System.out.println("userId：" + userId);
                User user = userMapper.getUserById(userId);
                System.out.println(user.toString());
                //  用户不存在
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登陆");
                }
                //  验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("token验证失败");
                }
                return true;
            }
        }
        return true;
    }

    //  二、void postHandle()：后处理回调方法，实现处理器的后处理（DispatcherServlet进行视图返回渲染之前进行调用）
    //  此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView modelAndView) throws Exception {

    }

    //  三、void afterCompletion():
    //  整个请求处理完毕回调方法,该方法也是需要当前对应的Interceptor的preHandle()的返回值为true时才会执行，
    //  也就是在DispatcherServlet渲染了对应的视图之后执行。用于进行资源清理。整个请求处理完毕回调方法。
    //  如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，
    //  类似于try-catch-finally中的finally，但仅调用处理器执行链中
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object object, Exception e) throws Exception {

    }
}
