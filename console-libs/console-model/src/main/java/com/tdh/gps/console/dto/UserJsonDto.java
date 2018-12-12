package com.tdh.gps.console.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tdh.gps.console.model.Privilege;
import com.tdh.gps.console.model.User;

/**
 * 
 * @ClassName: UserJsonDto  
 * @Description: (用户JsonDto)  
 * @author wxf
 * @date 2018年12月6日 下午2:45:22  
 *
 */
public class UserJsonDto implements Serializable {


    private static final long serialVersionUID = 3323307820018705154L;
    private String guid;
    private boolean archived;

    private String username;
    private String phone;
    private String email;

    private List<String> privileges = new ArrayList<>();

    public UserJsonDto() {
    }

    public UserJsonDto(User user) {
        this.guid = user.guid();
        this.archived = user.archived();
        this.username = user.username();

        this.phone = user.phone();
        this.email = user.email();

        final Set<Privilege> privilegeList = user.privileges();
        this.privileges.addAll(privilegeList.stream().map(Privilege::name).collect(Collectors.toList()));
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
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

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}