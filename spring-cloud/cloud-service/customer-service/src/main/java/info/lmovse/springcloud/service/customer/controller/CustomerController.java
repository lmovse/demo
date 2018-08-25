package info.lmovse.springcloud.service.customer.controller;

import info.lmovse.springcloud.service.customer.entity.UserEntity;
import info.lmovse.springcloud.service.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser() {
        return ResponseEntity.ok(customerService.getUser());
    }
}
