package com.tdh.gps.console.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.tdh.gps.console.utils.DateUtils;

/**
 * 
 * @ClassName: AuthorizationCode  
 * @Description: (授权码实体类)  
 * @author wxf
 * @date 2018年12月6日 下午2:48:58  
 *
 */
@Document(collection = "AuthorizationCode")
public class AuthorizationCode implements Serializable {

    private static final long serialVersionUID = 2775272095671208572L;


    @Id
    private String code;


    @CreatedDate
    private Date createTime = DateUtils.now();

    @Version
    private Long version;

    private byte[] authenticationBytes;


    public AuthorizationCode() {
    }


    public String code() {
        return code;
    }

    public AuthorizationCode code(String code) {
        this.code = code;
        return this;
    }

    public Date createTime() {
        return createTime;
    }


    public Long version() {
        return version;
    }


    public OAuth2Authentication authentication() {
        return SerializationUtils.deserialize(authenticationBytes);
    }

    public AuthorizationCode authentication(OAuth2Authentication authentication) {
        this.authenticationBytes = SerializationUtils.serialize(authentication);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", createTime=" + createTime +
                ", version=" + version +
                ", authenticationBytes=" + authenticationBytes +
                '}';
    }
}
