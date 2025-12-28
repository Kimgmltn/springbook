package springbook.learningtest.jdk;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HelloTarget implements Hello {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankYou(String name) {
        return "Thank You " + name;
    }

    @Test
    public void simpleProxy(){
//        Hello hello = new HelloTarget();
//        Hello hello = new HelloUppercase(new HelloTarget());
        Hello hello = (Hello)Proxy.newProxyInstance(
                getClass().getClassLoader(),    //동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
                new Class[]{Hello.class},  //구현할 인터페이스
                new UppercaseHandler(new HelloTarget())
        );
        assertThat(hello.sayHello("Test1"), is("HELLO TEST1"));
        assertThat(hello.sayHi("Test1"), is("HI TEST1"));
        assertThat(hello.sayThankYou("Test1"), is("THANK YOU TEST1"));
    }
}
