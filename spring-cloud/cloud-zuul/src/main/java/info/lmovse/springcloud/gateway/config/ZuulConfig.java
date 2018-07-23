package info.lmovse.springcloud.gateway.config;

import com.netflix.zuul.ZuulFilter;
import info.lmovse.springcloud.gateway.fallback.RouteFallback;
import info.lmovse.springcloud.gateway.filter.RequestInfoFilter;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZuulProxy
public class ZuulConfig {

    @Bean
    public FallbackProvider routeFallback() {
        return new RouteFallback();
    }

    @Bean
    public ZuulFilter requestInfoFilter() {
        return new RequestInfoFilter();
    }
}
