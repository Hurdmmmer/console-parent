package com.tdh.gps.console.service.api;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tdh.gps.console.dto.UserFormDto;
import com.tdh.gps.console.dto.UserJsonDto;
import com.tdh.gps.console.dto.UserOverviewDto;
import com.tdh.gps.console.model.User;

/**
 * 
 * @ClassName: UserService
 * @Description: (用户服务接口)
 * @author wxf
 * @date 2018年12月6日 下午2:27:31
 *
 */
public interface UserService {

	User loadUserByUsername(String username) throws UsernameNotFoundException;

	UserJsonDto loadCurrentUserJsonDto();

	UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

	boolean isExistedUsername(String username);

	String saveUser(UserFormDto formDto);
}