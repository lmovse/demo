package info.lmovse.springcloud.service.greeting;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class GreetingService {

    public static void main(String[] args) {
        SpringApplication.run(GreetingService.class, args);
    }

}
