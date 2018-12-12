package com.tdh.gps.console.token.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 
 * @ClassName: SsoAuthorizationServerConfigurer
 * @Description: (sso认证服务配置类)
 * @author wxf
 * @date 2018年12月4日 下午3:42:15
 *
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
	private static String REALM = "MY_OAUTH_REALM";

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;

//	@Autowired
//	private BeanInitConfig beanInitConfig;

//	@Autowired
//	@Qualifier("dataSource")
//	private DataSource dataSource = SpringUtils.getBean(DataSource.class);

//
//	@Autowired
//	private ClientDetailsService clientDetailsService;

//	@Bean
//	public TokenStore tokenStore() {
//		return new JdbcTokenStore(SpringUtils.getBean(DataSource.class));
//	}

//	@Bean
//	public ClientDetailsService clientDetails() {
//		return new JdbcClientDetailsService(SpringUtils.getBean(DataSource.class));
//	}
//	@Autowired(required = false)
//	private TokenEnhancer jwtTokenEnhancer;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.withClientDetails(clientDetailsService);
		clients.inMemory().withClient("my-trusted-client")// 客户端ID
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust")// 授权用户的操作权限
				.secret("secret")// 密码
				.accessTokenValiditySeconds(6000).refreshTokenValiditySeconds(6000);// token有效期为6000秒
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
				.authenticationManager(authenticationManager);
		// 配置TokenServices参数
//		DefaultTokenServices tokenServices = new DefaultTokenServices();
//		tokenServices.setTokenStore(endpoints.getTokenStore());
//		tokenServices.setSupportRefreshToken(false);
//		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//		tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
//		endpoints.tokenServices(tokenServices);
		if (jwtAccessTokenConverter != null) {
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancers = new ArrayList<TokenEnhancer>();
//            enhancers.add(jwtTokenEnhancer);
			enhancers.add(jwtAccessTokenConverter);
			enhancerChain.setTokenEnhancers(enhancers);
			endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
		}

	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM + "/client");
	}

}
