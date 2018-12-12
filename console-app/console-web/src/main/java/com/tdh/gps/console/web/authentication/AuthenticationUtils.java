package com.tdh.gps.console.web.authentication;

import com.tdh.gps.console.model.AuthTokenInfo;
import com.tdh.gps.console.web.config.SecurityProperties;
import com.tdh.gps.console.web.exception.LoginException;
import com.tdh.gps.console.web.interceptor.UsernamePasswordInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * 授权工具类
 */
@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthenticationUtils {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    private static final String QPM_PASSWORD_GRANT = "?grant_type=%s&username=%s&password=%s";

    /**
     * 根据用户信息获取 token
     *
     * @param username 账号
     * @param password 密码
     */
    public AuthTokenInfo getToken(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new LoginException("Account and password cannot be empty");
        }
        HttpHeaders headers = getHeadersWithClientCredentials(securityProperties.getAuthentication().getClientId(), securityProperties.getAuthentication().getSecret());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String param = String.format(QPM_PASSWORD_GRANT, securityProperties.getAuthentication().getGrantType(), username, password);
        ResponseEntity<AuthTokenInfo> resp = restTemplate.exchange(securityProperties.getAuthentication().getUrl() + param, HttpMethod.POST, httpEntity, AuthTokenInfo.class);
        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
            throw new LoginException("User does not exist or server is exception");
        }
        return resp.getBody();
    }

    /**
     * @param token 检查token是否正确
     */
    public boolean validateToken(String token, String redisToken) {
        String[] tmps = null;
        try {
            tmps = redisToken.split(",");
            UsernamePasswordInterceptor.context.clean();
            UsernamePasswordInterceptor.context.add(tmps[1]);
        } catch (Exception e) {
            return false;
        }
        return StringUtils.equals(token, tmps[0]);
    }

    //////////////////////////////////////构建请求头信息//////////////////////////////////////////////////
    private static HttpHeaders getHeadersWithClientCredentials(String clientId, String secret) {
        String plainClientCredentials = clientId + ":" + secret;
        String base64ClientCredentials = new String(Base64.encode(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        System.out.println("Authorization:" + "Basic " + base64ClientCredentials);
        return headers;
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}
