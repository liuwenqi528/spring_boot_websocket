package com.party.build.rural.mapper;

import com.party.build.rural.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/22
 * Time: 17:14
 */
@Repository
public interface LoginMapper {

    List<User> login(User user);
}
