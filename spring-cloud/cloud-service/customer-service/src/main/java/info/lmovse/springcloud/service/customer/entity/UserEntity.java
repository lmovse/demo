package info.lmovse.springcloud.service.customer.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEntity {

    private String userName;

    private String password;

    private String email;

}
