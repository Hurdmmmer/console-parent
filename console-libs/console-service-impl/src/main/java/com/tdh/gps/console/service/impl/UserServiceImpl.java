package com.tdh.gps.console.service.impl;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.tdh.gps.console.dao.api.UserRepository;
import com.tdh.gps.console.dto.UserDto;
import com.tdh.gps.console.dto.UserFormDto;
import com.tdh.gps.console.dto.UserJsonDto;
import com.tdh.gps.console.dto.UserOverviewDto;
import com.tdh.gps.console.model.User;
import com.tdh.gps.console.model.WdcyUserDetails;
import com.tdh.gps.console.service.api.UserService;

/**
 * 
 * @ClassName: UserServiceImpl
 * @Description: (用户service实现类)
 * @author wxf
 * @date 2018年12月6日 下午3:24:59
 *
 */
@Service(timeout = 10000)
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null || user.archived()) {
			throw new UsernameNotFoundException("Not found any user for username[" + username + "]");
		}
		return user;
	}

	@Override
	public UserJsonDto loadCurrentUserJsonDto() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final Object principal = authentication.getPrincipal();

		if (authentication instanceof OAuth2Authentication && (principal instanceof String
				|| principal instanceof org.springframework.security.core.userdetails.User)) {
			return loadOauthUserJsonDto((OAuth2Authentication) authentication);
		} else {
			final WdcyUserDetails userDetails = (WdcyUserDetails) principal;
			return new UserJsonDto(userRepository.findByGuid(userDetails.user().guid()));
		}
	}

	@Override
	public UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto) {
		List<User> users = userRepository.findUsersByUsername(overviewDto.getUsername());
		Gson gson = new Gson();
		logger.info(gson.toJson(users));
		overviewDto.setUserDtos(UserDto.toDtos(users));
		return overviewDto;
	}

	@Override
	public boolean isExistedUsername(String username) {
		final User user = userRepository.findByUsername(username);
		return user != null;
	}

	@Override
	public String saveUser(UserFormDto formDto) {
		User user = formDto.newUser();
		userRepository.saveUser(user);
		return user.guid();
	}

	private UserJsonDto loadOauthUserJsonDto(OAuth2Authentication oAuth2Authentication) {
		UserJsonDto userJsonDto = new UserJsonDto();
		userJsonDto.setUsername(oAuth2Authentication.getName());

		final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			userJsonDto.getPrivileges().add(authority.getAuthority());
		}

		return userJsonDto;
	}
}