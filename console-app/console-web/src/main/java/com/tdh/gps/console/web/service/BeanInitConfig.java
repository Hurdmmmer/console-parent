//package com.tdh.gps.console.web.service;
//
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.tdh.gps.console.service.api.OauthService;
//import com.tdh.gps.console.service.api.UserService;
//
///**
// *
// * @ClassName: BeanInitConfig
// * @Description: (bean初始化配置)
// * @author wxf
// * @date 2018年12月10日 下午12:48:21
// *
// */
////@Component
//public class BeanInitConfig {
//
//	@Reference(timeout = 10000)
//	private ClientDetailsService clientDetailsService;
//	@Reference(timeout = 10000)
//	private UserService userDetailsService;
//	@Reference(timeout = 10000)
//	private TokenStore tokenStore;
//	@Reference(timeout = 10000)
//	private OauthService oauthService;
//
//	public ClientDetailsService getClientDetailsService() {
//		return clientDetailsService;
//	}
//
//	public UserService getUserDetailsService() {
//		return userDetailsService;
//	}
//
//	public TokenStore getTokenStore() {
//		return tokenStore;
//	}
//
//	public OauthService getOauthService() {
//		return oauthService;
//	}
//}
