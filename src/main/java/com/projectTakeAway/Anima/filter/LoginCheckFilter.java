package com.projectTakeAway.Anima.filter;

/**
 * @author CodemyMind
 * @Date 2022/5/15 3:33 PM
 * Description：
 */


import com.alibaba.fastjson.JSON;
import com.projectTakeAway.Anima.common.BaseContext;
import com.projectTakeAway.Anima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@SuppressWarnings("all")
/**
 * 检查用户是否完成登陆
 * @author CodemyMind
 */

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 专门用来路径比较的 路径匹配器， 支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1、获取本次请求的`URL`
        String requestUrl = request.getRequestURI();
        log.info("拦截到的请求：{}", requestUrl);

        // 不需要处理的请求
        String[] urls = new String[] {
                "/employee/login", "/employee/logout",
                "/backend/**", "/front/**",
                "/user/sendMsg","/user/login",
                "/doc.html","/webjars/**",
                "/swagger-resources","/v2/api-docs"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestUrl);

        //3、若不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestUrl);
            filterChain.doFilter(request, response);   //放行
            return;
        }

        //4-1、判断后台页面登陆状态，若已登陆，则直接放行
        if(request.getSession().getAttribute("employee") != null) {
            log.info("用户已登陆，用户id为{}", request.getSession().getAttribute("employee"));

            // 取出当前用户登陆的id，强转成Long
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);   //放行
            return;
        }

        //4-2、判断移动端用户登陆状态，若已登陆，则直接放行
        if(request.getSession().getAttribute("user") != null) {
            log.info("移动端用户已登陆，用户id为{}", request.getSession().getAttribute("user"));

            // 取出当前用户登陆的id，强转成Long
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);   //放行
            return;
        }

        log.info("用户未登陆");
        //5、若未登陆则返回未登陆结果， 通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestUrl
     * @return
     */
    public boolean check(String[] urls, String requestUrl) {
        for (String url : urls) {
            // match()方法需要注意，其参数位置要放对，否则效果难达预期
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
