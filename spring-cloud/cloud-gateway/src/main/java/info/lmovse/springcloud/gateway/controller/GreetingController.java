package info.lmovse.springcloud.gateway.controller;

import info.lmovse.springcloud.gateway.integration.GreetingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private final GreetingProvider greetingProvider;

    @Autowired
    public GreetingController(final GreetingProvider greetingProvider) {
        this.greetingProvider = greetingProvider;
    }

    @GetMapping("/api/greeting/{name}")
    public String greeting(@PathVariable final String name) {
        return greetingProvider.sayHello(name);
    }
}
