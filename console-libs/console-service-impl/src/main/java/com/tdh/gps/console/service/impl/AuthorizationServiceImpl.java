package com.tdh.gps.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tdh.gps.console.dao.api.OauthRepository;
import com.tdh.gps.console.model.AuthorizationCode;
import com.tdh.gps.console.model.OauthClientDetails;
import com.tdh.gps.console.service.api.AuthorizationService;

/**
 * 
 * @ClassName: AuthorizationServiceImpl
 * @Description: (认证Service实现类)
 * @author wxf
 * @date 2018年12月11日 上午10:38:34
 *
 */
@Service(timeout = 10000)
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private OauthRepository oauthRepository;

	@Override
	public OauthClientDetails findOauthClientDetails(String clientId) {
		return oauthRepository.findOauthClientDetails(clientId);
	}

	@Override
	public List<OauthClientDetails> findAllOauthClientDetails() {
		return oauthRepository.findAllOauthClientDetails();
	}

	@Override
	public boolean updateOauthClientDetailsArchive(String clientId, boolean archive) {
		return oauthRepository.updateOauthClientDetailsArchive(clientId, archive);
	}

	@Override
	public void saveOauthClientDetails(OauthClientDetails clientDetails) {
		oauthRepository.saveOauthClientDetails(clientDetails);

	}

	@Override
	public boolean removeOauthClientDetails(OauthClientDetails clientDetails) {
		return oauthRepository.removeOauthClientDetails(clientDetails);
	}

	@Override
	public void saveAuthorizationCode(AuthorizationCode authorizationCode) {
		oauthRepository.saveAuthorizationCode(authorizationCode);

	}

	@Override
	public AuthorizationCode removeAuthorizationCode(String code) {
		return oauthRepository.removeAuthorizationCode(code);
	}

}
