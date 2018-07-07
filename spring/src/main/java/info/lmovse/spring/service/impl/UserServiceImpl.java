package info.lmovse.spring.service.impl;

import info.lmovse.spring.service.IUserService;

public class UserServiceImpl implements IUserService {

    @Override
    public void sayHello(final String name) {
        System.out.println("Hello, " + name);
    }
}
