package info.lmovse.springcloud.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class CloudConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConsumer.class);
    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public CloudConsumer(final LoadBalancerClient loadBalancerClient) {
        this.restTemplate = new RestTemplate();
        this.loadBalancerClient = loadBalancerClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudConsumer.class, args);
    }

    @GetMapping("/invoke/{name}")
    public String sayHello(@PathVariable final String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        LOGGER.info("=== Service url: {}", url);
        return restTemplate.getForObject(url + "/say-hello/" + name, String.class);
    }
}
