package com.tdh.gps.console.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tdh.gps.console.dao.api.AccessTokenRepository;
import com.tdh.gps.console.model.AccessToken;
import com.tdh.gps.console.model.RefreshToken;
import com.tdh.gps.console.service.api.AccessTokenService;

/**
 * 
 * @ClassName: AccessTokenServiceImpl
 * @Description: (获取Token令牌Service实现类)
 * @author wxf
 * @date 2018年12月11日 上午10:30:50
 *
 */
@Service(timeout = 10000)
public class AccessTokenServiceImpl implements AccessTokenService {

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Override
	public void saveAccessToken(AccessToken accessToken) {
		accessTokenRepository.saveAccessToken(accessToken);

	}

	@Override
	public AccessToken findAccessToken(String tokenId) {
		return accessTokenRepository.findAccessToken(tokenId);
	}

	@Override
	public void removeAccessToken(String tokenId) {
		accessTokenRepository.removeAccessToken(tokenId);

	}

	@Override
	public void saveRefreshToken(RefreshToken refreshToken) {
		accessTokenRepository.saveRefreshToken(refreshToken);

	}

	@Override
	public RefreshToken findRefreshToken(String tokenId) {
		return accessTokenRepository.findRefreshToken(tokenId);
	}

	@Override
	public void removeRefreshToken(String tokenId) {
		accessTokenRepository.removeRefreshToken(tokenId);

	}

	@Override
	public void removeAccessTokenByRefreshToken(String refreshToken) {
		accessTokenRepository.removeAccessTokenByRefreshToken(refreshToken);

	}

	@Override
	public AccessToken findAccessTokenByRefreshToken(String refreshToken) {
		return accessTokenRepository.findAccessTokenByRefreshToken(refreshToken);
	}

	@Override
	public AccessToken findAccessTokenByAuthenticationId(String authenticationId) {
		return accessTokenRepository.findAccessTokenByAuthenticationId(authenticationId);
	}

	@Override
	public List<AccessToken> findAccessTokensByUsername(String userName) {
		return accessTokenRepository.findAccessTokensByUsername(userName);
	}

	@Override
	public List<AccessToken> findAccessTokensByClientId(String clientId) {
		return accessTokenRepository.findAccessTokensByClientId(clientId);
	}

	@Override
	public List<AccessToken> findAccessTokensByClientIdAndUsername(String clientId, String userName) {
		return accessTokenRepository.findAccessTokensByClientIdAndUsername(clientId, userName);
	}

}
