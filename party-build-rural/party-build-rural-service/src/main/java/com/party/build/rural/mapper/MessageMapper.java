package com.party.build.rural.mapper;

import com.party.build.rural.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/28
 * Time: 15:08
 */
@Repository
public interface MessageMapper {

    void save(Message message);

    List<Message> selectMessage(@Param("uid") Integer uid, @Param("toUid") Integer toUid);

    void updateReceivedFlag(@Param("uid") Integer uid, @Param("toUid") Integer toUid);
}
