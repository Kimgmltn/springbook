package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
    Hello target;

    //위임해야 하기 때문에 타깃 오브젝트를 주입받아 둔다.
    public UppercaseHandler(Hello target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //타깃으로 위임. 인터페이스의 메소드 호출에 모두 적용.
        // (String)은 Hello객체의 모든 return값이 String 이므로 검증없이 바로 String으로 형변환 함
        String ret = (String)method.invoke(target, args);
        return ret.toUpperCase();
    }
}
