/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.tdh.gps.console.dao.impl;


import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.tdh.gps.console.dao.api.AccessTokenRepository;
import com.tdh.gps.console.model.AccessToken;
import com.tdh.gps.console.model.RefreshToken;

/**
 * 
 * @ClassName: AccessTokenRepositoryMongo  
 * @Description: (AccessTokenRepository Dao层实现)  
 * @author wxf
 * @date 2018年12月6日 下午3:10:07  
 *
 */
@Repository("accessTokenRepositoryMongo")
public class AccessTokenRepositoryMongo extends AbstractMongoSupport implements AccessTokenRepository {


    @Override
    public void saveAccessToken(AccessToken accessToken) {
        this.mongoTemplate().save(accessToken);
    }

    @Override
    public AccessToken findAccessToken(String tokenId) {
        return findById(AccessToken.class, tokenId);
    }

    @Override
    public void removeAccessToken(String tokenId) {
        final AccessToken accessToken = findAccessToken(tokenId);
        this.mongoTemplate().remove(accessToken);
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        this.mongoTemplate().save(refreshToken);
    }

    @Override
    public RefreshToken findRefreshToken(String tokenId) {
        return findById(RefreshToken.class, tokenId);
    }

    @Override
    public void removeRefreshToken(String tokenId) {
        final RefreshToken refreshToken = findRefreshToken(tokenId);
        this.mongoTemplate().remove(refreshToken);
    }

    @Override
    public void removeAccessTokenByRefreshToken(String refreshToken) {
        final AccessToken accessToken = findAccessTokenByRefreshToken(refreshToken);
        this.mongoTemplate().remove(accessToken);
    }

    @Override
    public AccessToken findAccessTokenByRefreshToken(String refreshToken) {
        Query query = new Query(new Criteria("refreshToken").is(refreshToken));
        return this.mongoTemplate().findOne(query, AccessToken.class);
    }

    @Override
    public AccessToken findAccessTokenByAuthenticationId(String authenticationId) {
        Query query = new Query(new Criteria("authenticationId").is(authenticationId));
        return this.mongoTemplate().findOne(query, AccessToken.class);
    }

    @Override
    public List<AccessToken> findAccessTokensByUsername(String username) {
        Query query = new Query(new Criteria("username").is(username));
        return mongoTemplate().find(query, AccessToken.class);
    }

    @Override
    public List<AccessToken> findAccessTokensByClientId(String clientId) {
        Query query = new Query(new Criteria("clientId").is(clientId));
        return mongoTemplate().find(query, AccessToken.class);
    }

    @Override
    public List<AccessToken> findAccessTokensByClientIdAndUsername(String clientId, String userName) {
        Query query = new Query(new Criteria("clientId").is(clientId));
        query.addCriteria(new Criteria("username").is(userName));
        return mongoTemplate().find(query, AccessToken.class);
    }
}
