package info.lmovse.springcloud.zuul.controller;

import info.lmovse.springcloud.zuul.integration.GreetingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GreetingController {
    private final GreetingProvider greetingProvider;

    @Autowired
    public GreetingController(final GreetingProvider greetingProvider) {
        this.greetingProvider = greetingProvider;
    }

    @GetMapping("/api/greeting/{name}")
    public String greeting(@PathVariable final String name) {
        log.info("start invoke greeting service...");
        return greetingProvider.sayHello(name);
    }
}
