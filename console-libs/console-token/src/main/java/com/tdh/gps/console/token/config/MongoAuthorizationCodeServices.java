package com.tdh.gps.console.token.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tdh.gps.console.model.AuthorizationCode;

/**
 * 
 * @ClassName: MongoAuthorizationCodeServices
 * @Description: (mongo授权码service实现类)
 * @author wxf
 * @date 2018年12月6日 下午3:14:32
 *
 */
@Component
public class MongoAuthorizationCodeServices extends RandomValueAuthorizationCodeServices implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(MongoAuthorizationCodeServices.class);

	@Autowired
	private BeanInitConfig beanInitConfig;

	public MongoAuthorizationCodeServices() {
	}

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		AuthorizationCode authorizationCode = new AuthorizationCode().code(code).authentication(authentication);

		beanInitConfig.getAuthorizationService().saveAuthorizationCode(authorizationCode);
		LOG.debug("Store AuthorizationCode: {}", authorizationCode);
	}

	@Override
	protected OAuth2Authentication remove(String code) {
		AuthorizationCode authorizationCode = beanInitConfig.getAuthorizationService().removeAuthorizationCode(code);
		LOG.debug("Remove AuthorizationCode: {}", authorizationCode);
		return authorizationCode != null ? authorizationCode.authentication() : null;
	}

//    public void setOauthRepository(OauthRepository oauthRepository) {
//        this.oauthRepository = oauthRepository;
//    }

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(beanInitConfig.getAuthorizationService(), "oauthRepository is null");
	}
}
