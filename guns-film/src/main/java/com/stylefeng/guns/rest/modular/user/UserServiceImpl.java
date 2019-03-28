package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.UserMapper;
import com.stylefeng.guns.rest.common.persistence.dao.UserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.UserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Service(interfaceClass = UserAPI.class, loadbalance = "roundrobin")
public class UserServiceImpl implements UserAPI{

    @Autowired
    private UserTMapper userTMapper;




    @Override
    public boolean register(UserModel userModel) {
        //获取注册信息

        //将注册信息实体转化为数据实体[user_t]
        UserT userT = new UserT();
        userT.setUserName(userModel.getUsername());
        //userT.setUserPwd(userModel.getPassword()); //注意
        userT.setEmail(userModel.getEmail());
        userT.setAddress(userModel.getAddress());
        userT.setUserPhone(userModel.getPhone());
        // 创建时间和修改时间 -> current_timestamp

        // 数据加密 【MD5混淆加密 + 盐值 -> Shiro加密】
        String md5Password = MD5Util.encrypt(userModel.getPassword());
        userT.setUserPwd(md5Password);
        //将数据实体存入数据库
        Integer insert = userTMapper.insert(userT);
        if(insert > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int login(String name, String password) {
        // 根据登陆账号获取数据库信息
        UserT userT = new UserT();
        userT.setUserName(name);

        UserT result = userTMapper.selectOne(userT);
        // 获取到的结果，然后与加密以后的密码做匹配
        if(result != null && result.getUuid() > 0){
            String md5Password = MD5Util.encrypt(password);
            if(result.getUserPwd().equals(md5Password)){
                return result.getUuid();
            }
        }
        return 0;
    }


    @Override
    public boolean checkUsername(String username) {

        EntityWrapper<UserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        Integer result = userTMapper.selectCount(entityWrapper);
        if(result != null && result > 0){
            return false;
        }else{
            return true;
        }

    }


    private UserInfoModel do2UserInfo(UserT userT){
        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(userT.getUuid());
        userInfoModel.setHeadAddress(userT.getHeadUrl());
        userInfoModel.setPhone(userT.getUserPhone());
        userInfoModel.setUpdateTime(userT.getUpdateTime().getTime());
        userInfoModel.setEmail(userT.getEmail());
        userInfoModel.setUsername(userT.getUserName());
        userInfoModel.setNickname(userT.getNickName());
        userInfoModel.setLifeState(""+userT.getLifeState());
        userInfoModel.setBirthday(userT.getBirthday());
        userInfoModel.setAddress(userT.getAddress());
        userInfoModel.setSex(userT.getUserSex());
        userInfoModel.setBeginTime(userT.getBeginTime().getTime());
        userInfoModel.setBiography(userT.getBiography());

        return userInfoModel;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        // 根据主键查询用户信息 [UserT]
        UserT userT = userTMapper.selectById(uuid);
        // 将UserT转换UserInfoModel
        UserInfoModel userInfoModel = do2UserInfo(userT);
        // 返回UserInfoModel
        return userInfoModel;
    }

    private java.util.Date time2Date(long time){
        java.util.Date date = new Date(time);
        return date;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        // 将传入的数据转换为UserT
        System.out.println("+++++++++++++++++++++++++++++");
        UserT userT = new UserT();
        userT.setUuid(userInfoModel.getUuid());
        userT.setNickName(userInfoModel.getNickname());
        userT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        userT.setBirthday(userInfoModel.getBirthday());
        userT.setBiography(userInfoModel.getBiography());
        userT.setBeginTime(null);
        userT.setHeadUrl(userInfoModel.getHeadAddress());
        userT.setEmail(userInfoModel.getEmail());
        userT.setAddress(userInfoModel.getAddress());
        userT.setUserPhone(userInfoModel.getPhone());
        userT.setUserSex(userInfoModel.getSex());
        userT.setUpdateTime(time2Date(System.currentTimeMillis()));
        // 将数据存入数据库

        Integer isSuccess = userTMapper.updateById(userT);
        if(isSuccess > 0){
            // 按照Id将用户信息查出来
            UserInfoModel userInfo = getUserInfo(userT.getUuid());
            // 返回给前端
            return userInfo;
        }else {
            return userInfoModel;
        }
    }
}
