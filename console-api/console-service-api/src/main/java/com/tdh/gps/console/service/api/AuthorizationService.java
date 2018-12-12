package com.tdh.gps.console.service.api;

import java.util.List;

import com.tdh.gps.console.model.AuthorizationCode;
import com.tdh.gps.console.model.OauthClientDetails;

/**
 * 
 * @ClassName: AuthorizationService  
 * @Description: (AuthorizationService Service层Api)  
 * @author wxf
 * @date 2018年12月6日 下午2:56:03  
 *
 */
public interface AuthorizationService{

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    boolean updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);

    boolean removeOauthClientDetails(OauthClientDetails clientDetails);

    void saveAuthorizationCode(AuthorizationCode authorizationCode);

    AuthorizationCode removeAuthorizationCode(String code);
}