package com.tdh.gps.console.token.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.tdh.gps.console.model.OauthClientDetails;

/**
 * 
 * @ClassName: OauthUserApprovalHandler
 * @Description: (授权用户批准处理类)
 * @author wxf
 * @date 2018年12月6日 下午4:12:38
 *
 */
@Component("oauthUserApprovalHandler")
public class OauthUserApprovalHandler extends TokenStoreUserApprovalHandler {

	@Autowired
	private BeanInitConfig beanInitConfig;
//	@Reference
//	private OauthService oauthService;
//	@Reference
//	private ClientDetailsService clientDetailsService;
//	@Reference
//	private TokenStore tokenStore;

	@Autowired
	public void tokenStore(TokenStore tokenStore, ClientDetailsService clientDetailsService) {
		super.setTokenStore(tokenStore);
		super.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		super.setClientDetailsService(clientDetailsService);
	}

//	public void setOauthService(OauthService oauthService) {
//		this.oauthService = beanInitConfig.getOauthService();
//	}

	public OauthUserApprovalHandler() {
//		super.setTokenStore(tokenStore);
//		super.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//		super.setClientDetailsService(clientDetailsService);
	}

	public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
		if (super.isApproved(authorizationRequest, userAuthentication)) {
			return true;
		}
		if (!userAuthentication.isAuthenticated()) {
			return false;
		}

		OauthClientDetails clientDetails = beanInitConfig.getOauthService()
				.loadOauthClientDetails(authorizationRequest.getClientId());
		return clientDetails != null && clientDetails.trusted();

	}

//	public void setOauthService(OauthService oauthService) {
//		this.oauthService = oauthService;
//	}
}
