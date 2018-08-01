package info.lmovse.oauth.resource.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lmovse on 2017/8/25.
 * Tomorrow is a nice day.
 */
@Component
public class TokenCheck extends HandlerInterceptorAdapter {

    private RedisTemplate<String, String> template;

    @Autowired
    public TokenCheck(final RedisTemplate<String, String> template) {
        this.template = template;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String access_token = request.getParameter("access_token");
        return template.hasKey(access_token) != null && super.preHandle(request, response, handler);
    }
}
