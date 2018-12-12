package com.tdh.gps.console.dao.api;

import java.util.List;

import com.tdh.gps.console.model.AuthorizationCode;
import com.tdh.gps.console.model.OauthClientDetails;

/**
 * 
 * @ClassName: AccessTokenRepository  
 * @Description: (OauthRepository Dao层Api)  
 * @author wxf
 * @date 2018年12月6日 下午2:56:03  
 *
 */
public interface OauthRepository extends Repository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    boolean updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);

    boolean removeOauthClientDetails(OauthClientDetails clientDetails);

    void saveAuthorizationCode(AuthorizationCode authorizationCode);

    AuthorizationCode removeAuthorizationCode(String code);
}