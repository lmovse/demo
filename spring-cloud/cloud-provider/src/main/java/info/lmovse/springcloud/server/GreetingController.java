package info.lmovse.springcloud.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public GreetingController(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/say-hello/{name}")
    public String sayHello(@PathVariable final String name) {
        System.out.println("Services:" + discoveryClient.getServices());
        return "Hello, " + name;
    }
}
