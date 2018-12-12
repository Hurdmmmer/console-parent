package com.tdh.gps.console.token.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;


/**
 * 
 * @ClassName: MethodSecurityConfig  
 * @Description: (启用security注解，在方法上控制权限)  
 * @author wxf
 * @date 2018年12月4日 下午4:33:01  
 *
 */
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @SuppressWarnings("unused")
	@Autowired
    private SsoWebSecurityConfigurer securityConfig;
    
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }
}
