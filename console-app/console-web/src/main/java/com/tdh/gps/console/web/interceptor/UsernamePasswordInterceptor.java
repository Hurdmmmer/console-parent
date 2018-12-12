package com.tdh.gps.console.web.interceptor;

import com.tdh.gps.console.model.AuthTokenInfo;
import com.tdh.gps.console.utils.CookieUtils;
import com.tdh.gps.console.utils.RedisUtil;
import com.tdh.gps.console.web.authentication.AuthenticationUtils;
import com.tdh.gps.console.web.config.SecurityProperties;
import com.tdh.gps.console.web.context.AuthenticationContext;
import com.tdh.gps.console.web.exception.LoginException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 账号密码拦截器
 */
public class UsernamePasswordInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Autowired
    private SecurityProperties securityProperties;

    public static AuthenticationContext<String> context = new AuthenticationContext<>();
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
        String redisKey = securityProperties.getRedisKey() + "." + request.getRequestedSessionId();
        String t = CookieUtils.getCookieValue(request, "token");
        if (StringUtils.isNotEmpty(t)) {
            return true;
        }
        try {
            String username = ServletRequestUtils.getStringParameter(request, "username");
            String password = ServletRequestUtils.getStringParameter(request, "password");
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                return true;
            }
            AuthTokenInfo token = authenticationUtils.getToken(username, password);
            // 将token存放到redis中
            Cookie cookie = new Cookie("token", token.getAccess_token());
            response.addCookie(cookie);
            redisUtil.set(redisKey, token.getAccess_token() + "," + username);
            redisUtil.expire(redisKey, token.getExpires_in(), TimeUnit.SECONDS);
            context.add(token.getAccess_token());
        } catch (ServletRequestBindingException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (LoginException e) {
            logger.warn(e.getMessage());
//            redirectStrategy.sendRedirect(request,response, "/login");
        }


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        // 清空 ThreadLocal 中的数据
        context.clean();
    }
}
