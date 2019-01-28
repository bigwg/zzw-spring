package com.zzw.trta.reflect;

import lombok.ToString;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/2 12:46
 */
public class ReflectTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //反射
//        Class<User> clazz = User.class;
//        Constructor[] pcs = clazz.getConstructors();
//        Constructor<User> pc = clazz.getConstructor();
//        User user = pc.newInstance();
//        System.out.println(user.toString());
//        for (Constructor c : pcs) {
//            String param = " ";
//            for (Type t : c.getGenericParameterTypes()) {
//                param = param + t.getTypeName() + " ";
//            }
//            System.out.println(Modifier.toString(c.getModifiers()) + " " + c.getName() + param);
//        }
        //JDK动态代理
        long begin = System.currentTimeMillis();
        UserService userService = new UserServiceImpl();
        InvocationHandler handler = new UserServiceHandler(userService);
        UserService us = (UserService)Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), handler);
        us.add();
        //Cglib动态代理
//        MethodInterceptor interceptor = new CglibInterceptor();
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(UserServiceImpl.class);
//        enhancer.setCallback(interceptor);
//        UserServiceImpl o = (UserServiceImpl)enhancer.create();
//        o.add();
        System.out.println(System.currentTimeMillis() - begin);
    }
}

@ToString
class User {
    private String name;
    public int age;

    public User() {

    }

    private User(int age) {
        super();
        this.age = age;
    }

    public User(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }
}

interface UserService{
    void add();
}

class UserServiceImpl implements UserService {

    @Override
    public void add() {
        System.out.println("------------------------add----------------------");
    }
}

class UserServiceHandler implements InvocationHandler{

    private UserService userService;

    public UserServiceHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---------------before---------------");
        Object invoke = method.invoke(userService, args);
        System.out.println("---------------after----------------");
        return invoke;
    }
}

class CglibInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("----------------cglib before--------------");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("----------------cglib after---------------");
        return result;
    }
}
