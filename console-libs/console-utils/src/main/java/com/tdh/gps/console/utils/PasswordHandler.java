package com.tdh.gps.console.utils;

/**
 * 
 * @ClassName: PasswordHandler  
 * @Description: (密码处理者)  
 * @author wxf
 * @date 2018年12月6日 下午2:34:45  
 *
 */
public abstract class PasswordHandler {


    private PasswordHandler() {
    }


    public static String md5(String password) {
//        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//        return encoder.encodePassword(password, null);
    	return password;
    }
}
