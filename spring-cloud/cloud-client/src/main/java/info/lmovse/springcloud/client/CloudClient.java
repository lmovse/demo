package info.lmovse.springcloud.client;

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
public class CloudClient {

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public CloudClient(final LoadBalancerClient loadBalancerClient) {
        this.restTemplate = new RestTemplate();
        this.loadBalancerClient = loadBalancerClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudClient.class, args);
    }

    @GetMapping("/invoke/{name}")
    public String sayHello(@PathVariable final String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        System.out.println(url);
        return restTemplate.getForObject(url + "/say-hello/" + name, String.class);
    }
}