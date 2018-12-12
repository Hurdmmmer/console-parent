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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tdh.gps.console.dao.api.OauthRepository;
import com.tdh.gps.console.model.AuthorizationCode;
import com.tdh.gps.console.model.OauthClientDetails;

/**
 * 
 * @ClassName: OauthRepositoryMongo
 * @Description: (OauthRepository Dao层实现)
 * @author wxf
 * @date 2018年12月6日 下午3:11:00
 *
 */
@Repository("oauthRepositoryMongo")
public class OauthRepositoryMongo extends AbstractMongoSupport implements OauthRepository {

	private static final Logger LOG = LoggerFactory.getLogger(OauthRepositoryMongo.class);

	@Override
	public OauthClientDetails findOauthClientDetails(String clientId) {
		LOG.debug("Call findOauthClientDetails, clientId = {}", clientId);
		Criteria criteria = Criteria.where("clientId").is(clientId);
		Query query = new Query();
		query.addCriteria(criteria);
		List<OauthClientDetails> oauthClientDetails = this.mongoTemplate().find(query, OauthClientDetails.class);
		return CollectionUtils.isEmpty(oauthClientDetails) ? new OauthClientDetails() : oauthClientDetails.get(0);
	}

	@Override
	public List<OauthClientDetails> findAllOauthClientDetails() {
		Query query = new Query();
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
		return this.mongoTemplate().find(query, OauthClientDetails.class);
	}

	@Override
	public boolean updateOauthClientDetailsArchive(String clientId, boolean archive) {
		LOG.debug("Call updateOauthClientDetailsArchive, clientId = {}, archive = {}", clientId, archive);
		Update update = new Update();
		update.set("archived", archive);

		Query query = createIDQuery(clientId);

		this.mongoTemplate().updateFirst(query, update, OauthClientDetails.class);
		return true;
	}

	@Override
	public void saveOauthClientDetails(OauthClientDetails clientDetails) {
		this.mongoTemplate().insert(clientDetails);
	}

	@Override
	public boolean removeOauthClientDetails(OauthClientDetails clientDetails) {
		LOG.debug("Call removeOauthClientDetails, clientDetails = {}", clientDetails);
		mongoTemplate().remove(clientDetails);
		return true;
	}

	@Override
	public void saveAuthorizationCode(AuthorizationCode authorizationCode) {
		this.mongoTemplate().save(authorizationCode);
	}

	@Override
	public AuthorizationCode removeAuthorizationCode(String code) {
		final AuthorizationCode authorizationCode = findById(AuthorizationCode.class, code);
		this.mongoTemplate().remove(authorizationCode);
		return authorizationCode;
	}
}
