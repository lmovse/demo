package info.lmovse.springcloud.zuul.integration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${integration.provider.greeting-provider}")
public interface GreetingProvider {

    @GetMapping("/say-hello/{name}")
    String sayHello(@PathVariable("name") String name);
}
