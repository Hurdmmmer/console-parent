package com.tdh.gps.console.token.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tdh.gps.console.model.OauthClientDetails;

/**
 * 
 * @ClassName: MongoClientDetailsService
 * @Description: (mongo客户端详情service实现类)
 * @author wxf
 * @date 2018年12月6日 下午3:17:16
 *
 */
@Component
public class MongoClientDetailsService implements ClientDetailsService, InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(MongoClientDetailsService.class);

	@Autowired
	private BeanInitConfig beanInitConfig;

	public MongoClientDetailsService() {
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		final OauthClientDetails oauthClientDetails = beanInitConfig.getAuthorizationService().findOauthClientDetails(clientId);
		if (oauthClientDetails == null || oauthClientDetails.archived()) {
			LOG.warn("Not found ClientDetails by clientId '{}', because null or archived", clientId);
			throw new ClientRegistrationException(
					"Not found ClientDetails by clientId '" + clientId + "', because null or archived");
		}
		return oauthClientDetails.toClientDetails();
	}

//    public void setOauthRepository(OauthRepository oauthRepository) {
//        this.oauthRepository = oauthRepository;
//    }

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(beanInitConfig.getAuthorizationService());
	}
}
