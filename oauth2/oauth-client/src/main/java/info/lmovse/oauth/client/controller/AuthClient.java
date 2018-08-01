package info.lmovse.oauth.client.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import info.lmovse.oauth.client.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by lmovse on 2017/8/25.
 * Tomorrow is a nice day.
 */
@Controller
public class AuthClient {
    private static final String CLIENT_ID = "asdjfa2323";
    private static final String CLIENT_SECRET = "123";
    private static final String AUTH_SERVER_URI = "http://localhost:8080";
    private static final String REDIRECT_URI = "http://localhost:8081/callback";
    private static final String RES_SERVER_URI = "http://localhost:8082";
    private String ACCESS_TOKEN = null;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        // 如果没有 access_token ，引导用户进行认证服务器认证授权
        if (null == ACCESS_TOKEN) {
            model.addAttribute("client_id", CLIENT_ID);
            model.addAttribute("redirect_uri", REDIRECT_URI);
            model.addAttribute("auth_server_uri", AUTH_SERVER_URI + "/authorize");
            return "auth";
        }
        // 有 token 的话通过 token 去资源服务器获取 token 对应用户的资源
        // 这里通过后台访问 URL 的方式获取，实际应该是以分布式服务的方式进行获取
        InputStream in = new URL(RES_SERVER_URI + "/user/profile?access_token=" + ACCESS_TOKEN).openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        int len;
        byte[] b = new byte[1024];
        if ((len = in.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        User user = JSON.parseObject(bos.toString("UTF-8"), User.class);
        model.addAttribute("CURRENT_USER", user);
        return "show";
    }

    @GetMapping("/callback")
    public String auth(String code) throws IOException {
        if (code == null) {
            throw new RuntimeException("授权码为空");
        } else {
            // 通过 code 去访问认证服务器获取 access_token
            URL urlGetToken = new URL(AUTH_SERVER_URI + "/code");
            HttpURLConnection connectionGetToken = (HttpURLConnection) urlGetToken.openConnection();
            connectionGetToken.setRequestMethod("POST");
            connectionGetToken.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connectionGetToken.getOutputStream(), Charset.forName("UTF-8"));
            writer.write("code=" + code + "&");
            writer.write("redirect_uri=" + REDIRECT_URI + "&");
            writer.write("client_id=" + CLIENT_ID + "&");
            writer.write("client_secret=" + CLIENT_SECRET + "&");
            writer.write("grant_type=authorization_code");
            writer.close();
            System.out.println(connectionGetToken.getResponseCode());
            if (connectionGetToken.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connectionGetToken.getInputStream(), "utf-8"))) {
                    String strLine;
                    while ((strLine = reader.readLine()) != null) {
                        sb.append(strLine);
                    }
                }
                JSONObject jsonObject = JSONObject.parseObject(sb.toString());
                // 客户端保存 access_token
                ACCESS_TOKEN = jsonObject.getString("access_token");
            }
            // 返回到主界面
            return "redirect:/";
        }
    }

}
