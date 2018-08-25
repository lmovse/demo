package info.lmovse.springcloud.service.customer.service;

import info.lmovse.springcloud.service.customer.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public UserEntity getUser() {
        return UserEntity.builder().userName("admin").password("admin123").email("hmilyssy@gmail.com").build();
    }

}
