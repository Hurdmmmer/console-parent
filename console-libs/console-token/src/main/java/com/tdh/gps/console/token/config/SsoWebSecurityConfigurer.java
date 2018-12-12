package com.tdh.gps.console.token.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 
 * @ClassName: SsoWebSecurityConfigurer
 * @Description: (SsoWebSecurity服务配置类)
 * @author wxf
 * @date 2018年12月4日 下午3:43:56
 *
 */
@Configuration
@EnableWebSecurity
public class SsoWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private BeanInitConfig beanInitConfig;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 在内存中创建两个用户
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
//		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("admin1").and().withUser("bob")
//				.password("abc123").roles("USER");
	}

	/**
	 * 设置获取token的url
	 * 
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().authorizeRequests().antMatchers("/oauth/token").permitAll();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Bean
//	@Autowired
//	public TokenStore tokenStore(DataSource dataSource) {
//		return new JdbcTokenStore(dataSource);
//	}

//	/**
//	 * 实例化一个TokenStore，他的实现是InMemoryTokenStore，会把OAuth授权的token保存在内存中
//	 * 
//	 * @return
//	 */
//	@Bean
//	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
//	}

//	@Bean
//	@Autowired
//	public OauthUserApprovalHandler userApprovalHandler(TokenStore tokenStore,ClientDetailsService clientDetailsService) {
//		OauthUserApprovalHandler handler = new OauthUserApprovalHandler();
//		handler.setTokenStore(tokenStore);
//		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//		handler.setClientDetailsService(clientDetailsService);
//		handler.setOauthService(beanInitConfig.getOauthService());
//		return handler;
//	}

	@Bean
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
}
