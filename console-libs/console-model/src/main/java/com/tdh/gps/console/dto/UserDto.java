package com.tdh.gps.console.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tdh.gps.console.model.Privilege;
import com.tdh.gps.console.model.User;
import com.tdh.gps.console.utils.DateUtils;

/**
 * 
 * @ClassName: UserDto  
 * @Description: (用户Dto)  
 * @author wxf
 * @date 2018年12月6日 下午2:44:27  
 *
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2502329463915439215L;


    private String guid;

    private String username;

    private String phone;
    private String email;

    private String createTime;

    private Set<Privilege> privileges = new HashSet<>();


    public UserDto() {
    }


    public UserDto(User user) {
        this.guid = user.guid();
        this.username = user.username();
        this.phone = user.phone();
        this.email = user.email();

        this.privileges = user.privileges();
        this.createTime = DateUtils.toDateTime(user.createTime());
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public static List<UserDto> toDtos(List<User> users) {
        List<UserDto> dtos = new ArrayList<>(users.size());
        dtos.addAll(users.stream().map(UserDto::new).collect(Collectors.toList()));
        return dtos;
    }
}
