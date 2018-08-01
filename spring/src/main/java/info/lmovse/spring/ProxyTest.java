package info.lmovse.spring;

import info.lmovse.spring.service.IUserService;
import info.lmovse.spring.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Proxy;

public class ProxyTest {

    private IUserService userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    public void jdkProxyTest() {
        IUserService proxyInstance = (IUserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(), (proxy, method, args1) -> {
                    if (method.getName().equals("sayHello")) {
                        System.out.println("invoking sayHello method by JdkProxy...");
                        return method.invoke(userService, args1);
                    }
                    return method.invoke(userService, args1);
                });
        proxyInstance.sayHello("jdk");
    }

    @Test
    public void cglibProxyTest() {
        IUserService proxyInstance = (IUserService) Enhancer.create(userService.getClass().getSuperclass(),
                userService.getClass().getInterfaces(), (MethodInterceptor) (o, method, objects, methodProxy) -> {
                    if (method.getName().equals("sayHello")) {
                        System.out.println("invoking sayHello method by CglibProxy...");
                        return method.invoke(userService, objects);
                    }
                    return method.invoke(userService, objects);
                });
        proxyInstance.sayHello("cglib");
    }
}
