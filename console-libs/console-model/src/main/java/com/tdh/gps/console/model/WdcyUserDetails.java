package com.tdh.gps.console.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @ClassName: WdcyUserDetails
 * @Description: (用户详情实体类)
 * @author wxf
 * @date 2018年12月6日 下午2:53:08
 *
 */
public class WdcyUserDetails implements UserDetails {

	private static final long serialVersionUID = 3957586021470480642L;

	protected static final String ROLE_PREFIX = "ROLE_";
	protected static final GrantedAuthority DEFAULT_USER_ROLE = new SimpleGrantedAuthority(
			ROLE_PREFIX + Privilege.USER.name());

	protected User user;

	protected List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

	public WdcyUserDetails() {
	}

	public WdcyUserDetails(User user) {
		this.user = user;
		initialAuthorities();
	}

	private void initialAuthorities() {
		// Default, everyone have it
		this.grantedAuthorities.add(DEFAULT_USER_ROLE);
		// default user have all privileges
		if (user.defaultUser()) {
			this.grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + Privilege.UNITY.name()));
			this.grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + Privilege.MOBILE.name()));
		} else {
			final Set<Privilege> privileges = user.privileges();
			this.grantedAuthorities.addAll(
					privileges.stream().map(privilege -> new SimpleGrantedAuthority(ROLE_PREFIX + privilege.name()))
							.collect(Collectors.toList()));
		}
	}

	/**
	 * Return authorities, more information see {@link #initialAuthorities()}
	 *
	 * @return Collection of GrantedAuthority
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.password();
	}

	@Override
	public String getUsername() {
		return user.username();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User user() {
		return user;
	}

	@Override
	public String toString() {
		return "{user=" + user + '}';
	}
}