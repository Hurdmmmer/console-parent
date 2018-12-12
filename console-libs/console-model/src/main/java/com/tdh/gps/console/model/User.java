package com.tdh.gps.console.model;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tdh.gps.console.utils.DateUtils;
import com.tdh.gps.console.utils.GuidGenerator;

/**
 * 
 * @ClassName: User  
 * @Description: (用户实体类)  
 * @author wxf
 * @date 2018年12月6日 下午2:52:09  
 *
 */
@Document(collection = "User")
public class User implements Serializable {


    private static final long serialVersionUID = -6117108610171201352L;


    @Id
    private String guid = GuidGenerator.generate();

    @CreatedDate
    private Date createTime = DateUtils.now();

    @Version
    private Long version;

    //unique
    private String username;

    private String password;

    private String phone;
    private String email;
    //Default user is initialed
    private boolean defaultUser = false;

    private Date lastLoginTime;

    private Set<Privilege> privileges = new HashSet<>();


    private boolean archived = false;


    public User() {
    }

    public User(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }


    public Long version() {
        return version;
    }

    public String guid() {
        return guid;
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    public Set<Privilege> privileges() {
        return privileges;
    }

    public Date createTime() {
        return createTime;
    }


    public boolean archived() {
        return archived;
    }

    public User archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    @Override
    public String toString() {
        return "{username='" + username + '\'' + ", phone='" + phone + '\'' + ", version='" + version + '\'' + ", defaultUser='" + defaultUser + '\'' + ", email='" + email + '\'' + '}';
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User phone(String phone) {
        this.phone = phone;
        return this;
    }


    public User username(String username) {
        this.username = username;
        return this;
    }


    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public void lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}