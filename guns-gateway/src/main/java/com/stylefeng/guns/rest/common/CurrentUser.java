package com.stylefeng.guns.rest.common;

public class CurrentUser {

    private static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public static void saveUserId(String userId){
        threadLocal.set(userId);
    }

    public static String getCurrentUser(){
        return threadLocal.get();
    }

    //线程绑定的存储空间
//    private static final ThreadLocal<UserInfoModel> threadLocal = new ThreadLocal<>();
//    //将用户信息放入存储空间
//    public static void saveUserInfo(UserInfoModel userInfoModel){
//        threadLocal.set(userInfoModel);
//    }
//    // 将用户信息取出
//    public static UserInfoModel getCurrentUser(){
//        return threadLocal.get();
//    }

}
