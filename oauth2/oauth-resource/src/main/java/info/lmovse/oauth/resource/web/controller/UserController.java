package info.lmovse.oauth.resource.web.controller;

import com.alibaba.fastjson.JSON;
import info.lmovse.oauth.resource.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lmovse on 2017/8/25.
 * Tomorrow is a nice day.
 */
@RestController
public class UserController {

    private final RedisTemplate template;

    @Autowired
    public UserController(final RedisTemplate template) {
        this.template = template;
    }

    @GetMapping(value = "/user/profile", produces = "application/json; charset=UTF-8")
    public String getUser(@RequestParam(value = "access_token") String accessToken) {
        Long userId = Long.valueOf(template.opsForValue().get(accessToken).toString());
        System.out.println(String.format("正在获取%s的用户数据", userId));
        System.out.println("===================");
        System.out.println("用户数据获取成功！");
        User user = new User();
        user.setUserId(userId);
        user.setAge(18);
        user.setUsername("张光光");
        user.setPassword("123");
        user.setHobby("吃东西");
        return JSON.toJSONString(user);
    }
}
