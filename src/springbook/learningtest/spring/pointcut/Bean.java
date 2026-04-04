package springbook.learningtest.spring.pointcut;

import org.junit.Test;

public class Bean {
    public void method() throws RuntimeException{

    }

    @Test
    public void test() throws NoSuchMethodException {
        System.out.println(Target.class.getMethod("minus", int.class, int.class));
    }
}
