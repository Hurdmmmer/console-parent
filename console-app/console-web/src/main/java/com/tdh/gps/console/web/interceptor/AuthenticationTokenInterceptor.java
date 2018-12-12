package com.tdh.gps.console.web.interceptor;

import com.tdh.gps.console.utils.CookieUtils;
import com.tdh.gps.console.utils.RedisUtil;
import com.tdh.gps.console.web.authentication.AuthenticationUtils;
import com.tdh.gps.console.web.config.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 拦截处理器
 */
public class AuthenticationTokenInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenInterceptor.class);

    /**
     * 重定向支持
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Autowired
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * redis 工具类
     */
    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(StringRedisTemplate redisTemplate) {
        this.redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String access_token = CookieUtils.getCookieValue(request, "token");
        String redisKey = securityProperties.getRedisKey() + "." + request.getRequestedSessionId();
        if (StringUtils.isEmpty(access_token)) {
            // 从登陆中刚刚登陆中获取 token
            access_token = UsernamePasswordInterceptor.context.get();
        }
        if ((StringUtils.equalsIgnoreCase(request.getRequestURI(), "/login") ) && StringUtils.isEmpty(access_token)) {
            return true;
        }
        if (StringUtils.isNotEmpty(access_token)) {
            // 校验 token
            String redisToken = redisUtil.get(redisKey);
            if (StringUtils.isNotEmpty(redisToken) && authenticationUtils.validateToken(access_token, redisToken)) {
                if (StringUtils.equalsIgnoreCase(request.getRequestURI(), "/login")) {
                    redirectStrategy.sendRedirect(request, response, "index");
                    return false;
                }
                return true;
            } else {
                logger.warn("Token has expired. token: {}", access_token);
                Cookie cookie = new Cookie("token", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                redisUtil.delete(redisKey);
                redirectStrategy.sendRedirect(request, response, "/login");
                return false;
            }

        }
        logger.warn("The authentication token is not included in the current request. url: {}", request.getRequestURI());
        redirectStrategy.sendRedirect(request, response, "/login");
        return false;
    }
}
