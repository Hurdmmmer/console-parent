package com.tdh.gps.console.dao.api;

import java.util.List;

import com.tdh.gps.console.model.User;

/**
 * 
 * @ClassName: UserRepository  
 * @Description: (UserRepository Dao层Api)  
 * @author wxf
 * @date 2018年12月6日 下午2:59:00  
 *
 */
public interface UserRepository extends Repository {

    User findByGuid(String guid);

    void saveUser(User user);

    boolean updateUser(User user);

    User findByUsername(String username);

    List<User> findAllUsers();

    boolean removeUser(User user);

    List<User> findUsersByUsername(String username);
}