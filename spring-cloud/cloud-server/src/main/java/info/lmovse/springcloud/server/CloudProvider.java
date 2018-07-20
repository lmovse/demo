package info.lmovse.springcloud.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
@SpringBootApplication
public class CloudProvider {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public CloudProvider(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudProvider.class, args);
    }

    @GetMapping("/say-hello/{name}")
    public String sayHello(@PathVariable final String name) {
        System.out.println("Services:" + discoveryClient.getServices());
        return "Hello, " + name;
    }
}
