package csu.lch.violetapi.service;

import csu.lch.violetapidubbointerface.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author violetRcl
 */

@SpringBootTest
class UserServiceTest {

//    @Resource
//    private UserService userService;
//
//    @Test
//    void testAddUser() {
//        User user = new User();
//        boolean result = userService.save(user);
//        System.out.println(user.getId());
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void testUpdateUser() {
//        User user = new User();
//        boolean result = userService.updateById(user);
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void testDeleteUser() {
//        boolean result = userService.removeById(1L);
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void testGetUser() {
//        User user = userService.getById(1L);
//        Assertions.assertNotNull(user);
//    }
//
//    @Test
//    public void userRegister() {
//        //账号不能为空
//        String userAccount = "";
//        String userPassword = "12345678";
//        String checkPassword = "12345678";
//        long result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        //账号长度不小于4
//        userAccount = "li";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        //账号不能包含特殊字符
//        userAccount = "lch+rcl";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "lchRcl2";
//
//        //密码不能为空
//        userPassword = "";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        //密码长度不小于8
//        userPassword = "123456";
//        checkPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        //校验密码不能为空
//        userPassword = "12345678";
//        checkPassword = "";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//        //校验密码不能与密码不同
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        //编写正确测试用例，插入数据
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertTrue(result > 0);
//    }
}