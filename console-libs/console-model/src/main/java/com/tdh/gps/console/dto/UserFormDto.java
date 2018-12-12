package com.tdh.gps.console.dto;

import com.tdh.gps.console.model.Privilege;
import com.tdh.gps.console.model.User;
import com.tdh.gps.console.utils.PasswordHandler;

/**
 * 
 * @ClassName: UserFormDto  
 * @Description: (用户表单Dto)  
 * @author wxf
 * @date 2018年12月6日 下午2:44:49  
 *
 */
public class UserFormDto extends UserDto {
    private static final long serialVersionUID = 7959857016962260738L;


    private String password;

    public UserFormDto() {
    }


    public Privilege[] getAllPrivileges() {
        return new Privilege[]{Privilege.MOBILE, Privilege.UNITY};
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User newUser() {
        final User user = new User()
                .username(getUsername())
                .phone(getPhone())
                .email(getEmail())
                .password(PasswordHandler.md5(getPassword()));
        user.privileges().addAll(getPrivileges());
        return user;
    }
}
