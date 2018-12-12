package com.tdh.gps.console.token.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tdh.gps.console.model.User;
import com.tdh.gps.console.model.WdcyUserDetails;

/**
 * 
 * @ClassName: UserDetailsServiceImpl
 * @Description: (userDetailsService实现类)
 * @author wxf
 * @date 2018年12月4日 下午4:06:42
 *
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private BeanInitConfig beanInitConfig;
//	@Reference(timeout = 10000)
//	UserService userService;
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//
//	@Bean
//	@Autowired
//	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
//		return new JdbcTemplate(dataSource);
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		List<Map<String, Object>> result = jdbcTemplate.queryForList(
//				"select user_name,password,r.role_name from user_info u INNER JOIN user_role_relation rel ON u.id=rel.user_id INNER JOIN role r ON rel.role_id=r.id  where user_name=?",
//				new Object[] { username });
//		List<Role> roles = new ArrayList<Role>();
//		if (CollectionUtils.isNotEmpty(result)) {
//			for (Map<String, Object> map : result) {
//				Role role = new Role(map.get("role_name").toString());
//				roles.add(role);
//			}
//			return new UserInfo(result.get(0).get("user_name").toString(), result.get(0).get("password").toString(),
//					roles);
//		}
//		return null;
		User user = beanInitConfig.getUserService().loadUserByUsername(username);
		return null != user ? new WdcyUserDetails(user) : null;
	}

}
