package info.lmovse.auth.server.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lmovse on 2017/8/25.
 * Tomorrow is a nice day.
 */
@Controller
public class AuthServer {

    private RedisTemplate<String, String> template;

    @Autowired
    public AuthServer(final RedisTemplate<String, String> template) {
        this.template = template;
    }

    @GetMapping("/authorize")
    public String authUi(String redirect_uri, String client_id, Model model) {
        model.addAttribute("redirect_uri", redirect_uri);
        model.addAttribute("client_id", client_id);
        return "auth";
    }

    @PostMapping("/auth")
    public String auth(String username, String password, String redirect_uri, String client_id) {
        System.out.println("正在验证用户。。。");
        System.out.println("================");
        System.out.println("验证身份成功, username: " + username
                + ", password: " + password + "client_id: " + client_id);
        // 生成 code
        String code = UUIDUtils.getCode().toLowerCase();
        // 将 code 对应的用户存入 redis 中，这里假设用户 ID 为 1 ，并设置 10 分钟过期
        template.opsForValue().set(code, 1 + "");
        template.expire(code, 600, TimeUnit.SECONDS);
        // 返回 code
        return "redirect:" + redirect_uri + "?code=" + code;
    }

    @PostMapping(value = "/code", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String code(String client_id, String redirect_uri, String code, String grant_type, String client_secret) {
        // 模拟 client 的合法性验证
        System.out.println("正在验证 client_id...");
        System.out.println("client_id: " + client_id
                + ", client_secret: " + client_secret
                + ", redirect_url: " + redirect_uri
                + ", grant_type: " + grant_type);
        System.out.println("验证通过");
        Map<String, Object> map = new HashMap<>();
        // 验证 code 的合法性
        if (!template.hasKey(code)) {
            map.put("error", 400);
            map.put("message", "不存在的 code，请重新申请!");
        } else {
            // 生成 access_token 与 refresh_token 绑定当前用户 ID 存入 redis 中，并设置过期时间
            String userId = template.opsForValue().get(code);
            String refreshToken = UUIDUtils.getCode().toLowerCase();
            String accessToken = UUIDUtils.getCode().toLowerCase();
            template.opsForValue().set(accessToken, userId);
            template.opsForValue().set(refreshToken, userId);
            template.expire(client_id + "_access_token", 3600, TimeUnit.SECONDS);
            template.expire(client_id + "_refresh_token", 30, TimeUnit.DAYS);
            map.put("access_token", accessToken);
            map.put("refresh_token", refreshToken);
            map.put("expires_in", 3600);
        }
        // 返回 json 响应数据
        return JSON.toJSONString(map);
    }

}
