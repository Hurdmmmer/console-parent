package com.tdh.gps.console.service.api;

import java.util.List;

import com.tdh.gps.console.dto.OauthClientDetailsDto;
import com.tdh.gps.console.model.OauthClientDetails;

/**
 * 
 * @ClassName: OauthService
 * @Description: (认证服务接口)
 * @author wxf
 * @date 2018年12月6日 下午2:26:51
 *
 */
public interface OauthService {

	OauthClientDetails loadOauthClientDetails(String clientId);

	List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

	void archiveOauthClientDetails(String clientId);

	OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

	void registerClientDetails(OauthClientDetailsDto formDto);
}