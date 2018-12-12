package com.tdh.gps.console.token.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tdh.gps.console.model.AccessToken;
import com.tdh.gps.console.model.RefreshToken;
import com.tdh.gps.console.utils.MD5ExtractTokenKeyDigesterUtils;

/**
 * 
 * @ClassName: MongoTokenStore
 * @Description: (mongodbToken令牌存储service实现类)
 * @author wxf
 * @date 2018年12月6日 下午3:19:26
 *
 */
@Component
public class MongoTokenStore implements TokenStore, InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(MongoTokenStore.class);

	@Autowired
	private BeanInitConfig beanInitConfig;
	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	public MongoTokenStore() {
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		final String authenticationId = authenticationKeyGenerator.extractKey(authentication);
		OAuth2AccessToken accessToken = null;

		try {
			AccessToken token = beanInitConfig.getAccessTokenService().findAccessTokenByAuthenticationId(authenticationId);
			accessToken = token != null ? token.token() : null;
		} catch (IllegalArgumentException e) {
			LOG.error("Could not extract access token for authentication {}", authentication);
		}

		if (accessToken != null && !authenticationId
				.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			removeAccessToken(accessToken.getValue());
			// Keep the store consistent (maybe the same user is represented by this
			// authentication but the details have
			// changed)
			storeAccessToken(accessToken, authentication);
		}

		return accessToken;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		if (token != null) {
			if (!token.isExpired()) {
				LOG.info("The current token has not expired and is not stored.");
				return;
			}
		}
		LOG.debug("Call storeAccessToken, token = {}, authentication = {}", token, authentication);
		String refreshToken = token.getRefreshToken() != null ? token.getRefreshToken().getValue() : null;

		AccessToken accessToken = new AccessToken().tokenId(extractTokenKey(token.getValue())).token(token)
				.authenticationId(authenticationKeyGenerator.extractKey(authentication))
				.username(authentication.isClientOnly() ? null : authentication.getName())
				.clientId(authentication.getOAuth2Request().getClientId()).authentication(authentication)
				.refreshToken(extractTokenKey(refreshToken));

		beanInitConfig.getAccessTokenService().saveAccessToken(accessToken);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		LOG.debug("Call readAccessToken, tokenValue = {}", tokenValue);
		OAuth2AccessToken token = null;

		try {
			final String tokenId = extractTokenKey(tokenValue);

			final AccessToken accessToken = beanInitConfig.getAccessTokenService().findAccessToken(tokenId);
			token = accessToken == null ? null : accessToken.token();
		} catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize access token for {}", tokenValue);
			removeAccessToken(tokenValue);
		}

		return token;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		removeAccessToken(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		LOG.debug("Call readAuthentication, token = {}", token);
		OAuth2Authentication authentication = null;
		try {
			final String tokenId = extractTokenKey(token);

			AccessToken accessToken = beanInitConfig.getAccessTokenService().findAccessToken(tokenId);
			authentication = accessToken == null ? null : accessToken.authentication();

		} catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize authentication for {}", token);
			removeAccessToken(token);
		}

		return authentication;
	}

	protected void removeAccessToken(String tokenValue) {
		final String tokenId = extractTokenKey(tokenValue);
		beanInitConfig.getAccessTokenService().removeAccessToken(tokenId);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		LOG.debug("Call storeRefreshToken, refreshToken = {}, authentication = {}", refreshToken, authentication);

		RefreshToken token = new RefreshToken().tokenId(extractTokenKey(refreshToken.getValue())).token(refreshToken)
				.authentication(authentication);

		beanInitConfig.getAccessTokenService().saveRefreshToken(token);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		LOG.debug("Call readRefreshToken, tokenValue = {}", tokenValue);
		OAuth2RefreshToken refreshToken = null;

		try {
			final String tokenId = extractTokenKey(tokenValue);

			RefreshToken refreshTokenFounded = beanInitConfig.getAccessTokenService().findRefreshToken(tokenId);
			refreshToken = refreshTokenFounded == null ? null : refreshTokenFounded.token();
		} catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize refresh token for token {}", tokenValue);
			removeRefreshToken(tokenValue);
		}

		return refreshToken;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	protected void removeRefreshToken(String token) {
		final String tokenId = extractTokenKey(token);
		beanInitConfig.getAccessTokenService().removeRefreshToken(tokenId);
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	protected OAuth2Authentication readAuthenticationForRefreshToken(String tokenValue) {
		OAuth2Authentication authentication = null;

		try {
			final String tokenId = extractTokenKey(tokenValue);
			RefreshToken refreshToken = beanInitConfig.getAccessTokenService().findRefreshToken(tokenId);

			authentication = refreshToken == null ? null : refreshToken.authentication();
		} catch (IllegalArgumentException e) {
			LOG.warn("Failed to deserialize access token for {}", tokenValue);
			removeRefreshToken(tokenValue);
		}

		return authentication;
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	protected void removeAccessTokenUsingRefreshToken(String refreshTokenValue) {
		final String refreshToken = extractTokenKey(refreshTokenValue);
		beanInitConfig.getAccessTokenService().removeAccessTokenByRefreshToken(refreshToken);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		LOG.debug("Call findTokensByClientId, clientId = {}", clientId);
		List<OAuth2AccessToken> accessTokens = new ArrayList<>();

		List<AccessToken> tokenList = beanInitConfig.getAccessTokenService().findAccessTokensByClientId(clientId);
		for (AccessToken token : tokenList) {
			final OAuth2AccessToken accessToken = token.token();
			if (accessToken != null) {
				accessTokens.add(accessToken);
			}
		}

		return accessTokens;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		LOG.debug("Call findTokensByUserName, clientId = {}, username = {}", clientId, userName);
		List<OAuth2AccessToken> accessTokens = new ArrayList<>();

		List<AccessToken> tokenList = beanInitConfig.getAccessTokenService().findAccessTokensByClientIdAndUsername(clientId, userName);
		for (AccessToken token : tokenList) {
			final OAuth2AccessToken accessToken = token.token();
			if (accessToken != null) {
				accessTokens.add(accessToken);
			}
		}

		return accessTokens;
	}

	protected String extractTokenKey(String value) {
		return MD5ExtractTokenKeyDigesterUtils.digest(value);
	}

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(beanInitConfig.getAccessTokenService());
		Assert.notNull(authenticationKeyGenerator);
	}
}
