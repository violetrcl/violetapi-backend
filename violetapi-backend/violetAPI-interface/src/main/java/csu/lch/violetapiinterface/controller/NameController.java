package csu.lch.violetapiinterface.controller;

import csu.lch.violetapidubbointerface.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest httpServletRequest){
        return "你的名字是" + name;
    }

    @PostMapping("/user")
    public String getNameByPost(@RequestBody User user, HttpServletRequest httpServletRequest){
        return "用户名是" + user.getUserName();
    }
}
