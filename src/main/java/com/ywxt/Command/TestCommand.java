package com.ywxt.Command;

import com.ywxt.Dao.Impl.UserDaoImpl;
import com.ywxt.Domain.AliAccount;
import com.ywxt.Domain.User;
import com.ywxt.Service.Ali.Impl.AliAccountServiceImpl;
import com.ywxt.Service.Impl.UserServiceImpl;
import com.ywxt.Utils.MD5Utils;
import com.ywxt.Utils.RedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class TestCommand {

    private static void saveAliAccount() {
        AliAccount aa = new AliAccount();
        aa.setUserName("asd");
        aa.setAccessKeyId("123");
        aa.setAccessKeySecret("154");
        aa.setStatus("normal");
        new AliAccountServiceImpl().saveAliAccount(aa);
    }

    private static void saveAdmin() {
        UserDaoImpl userDao = new UserDaoImpl();
        // 创建用户
        User u = new User();
        u.setUsername("admin");
        u.setPassword(MD5Utils.md5("123456"));
        u.setNickname("超级管理员");
        u.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        u.setIntroduction("我是超级管理员");
        String[] roles = {"admin"};
        u.setRoles(roles);
        userDao.saveUser(u);
    }

    private static void readUser() {
        // 读取用户
        User user = new UserServiceImpl().getUserById(1);
        for (String s : user.getRoles()) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println(user.getUsername());
    }

    public static void main(String[] args) {
        System.out.println(123);

        TestCommand.saveAdmin();
//        TestCommand.saveAliAccount();
    }
}