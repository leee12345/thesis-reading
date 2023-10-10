package com.thesisreading.service;

import com.thesisreading.model.UserExample;
import java.util.List;
import com.thesisreading.model.User;
public interface UserService{


    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String userId);

    int updateByExampleSelective(User record,UserExample example);

    int updateByExample(User record,UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateBatch(List<User> list);

    int batchInsert(List<User> list);

    /**
     * 登录验证密码
     * @param userid
     * @param password
     * @return
     */
    User loginUserById(String userid, String password);
}
