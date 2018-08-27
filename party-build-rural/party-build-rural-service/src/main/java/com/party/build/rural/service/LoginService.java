package com.party.build.rural.service;

import com.party.build.rural.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/22
 * Time: 17:03
 */

public interface LoginService {

    /**
     * 登陆
     * @param user
     * @return
     */
    User login(User user);

}
