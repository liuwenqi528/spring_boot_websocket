package com.party.build.rural.service.impl;

import com.party.build.rural.entity.User;
import com.party.build.rural.mapper.LoginMapper;
import com.party.build.rural.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/22
 * Time: 17:08
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    /**
     * 登陆
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        List<User> userList = loginMapper.login(user);
        if(userList!=null && !userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }
}