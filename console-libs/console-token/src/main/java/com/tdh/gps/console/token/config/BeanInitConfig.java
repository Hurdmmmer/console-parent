package com.tdh.gps.console.token.config;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tdh.gps.console.service.api.AccessTokenService;
import com.tdh.gps.console.service.api.AuthorizationService;
import com.tdh.gps.console.service.api.OauthService;
import com.tdh.gps.console.service.api.UserService;

/**
 * 
 * @ClassName: BeanInitConfig
 * @Description: (bean初始化配置)
 * @author wxf
 * @date 2018年12月10日 下午12:48:21
 *
 */
@Component
public class BeanInitConfig {

	@Reference(timeout = 10000)
	private AccessTokenService accessTokenService;
	@Reference(timeout = 10000)
	private AuthorizationService authorizationService;
	@Reference(timeout = 10000)
	private UserService userService;
	@Reference(timeout = 10000)
	private OauthService oauthService;

	public AccessTokenService getAccessTokenService() {
		return accessTokenService;
	}

	public AuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public UserService getUserService() {
		return userService;
	}

	public OauthService getOauthService() {
		return oauthService;
	}

}
