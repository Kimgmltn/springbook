package springbook.learningtest.jdk.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import springbook.learningtest.jdk.Hello;
import springbook.learningtest.jdk.HelloTarget;
import springbook.learningtest.jdk.UppercaseHandler;

import java.lang.reflect.Proxy;

import static org.junit.Assert.assertThat;

public class DynamicProxyTest {
    @Test
    public void simpleProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    public void proxyFactoryBean(){
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("test1"), CoreMatchers.is("HELLO TEST1"));
        assertThat(proxiedHello.sayHi("test1"),  CoreMatchers.is("HI TEST1"));
        assertThat(proxiedHello.sayThankYou("test1"),  CoreMatchers.is("THANK YOU TEST1"));
    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("test1"), CoreMatchers.is("HELLO TEST1"));
        assertThat(proxiedHello.sayHi("test1"),  CoreMatchers.is("HI TEST1"));
        assertThat(proxiedHello.sayThankYou("test1"),  CoreMatchers.is("Thank You test1"));
    }

    @Test
    public void classNamePointcutAdvisor() {
        //포인트컷 준비
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> aClass) {
                        return aClass.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };
        //sayH* 로 시작하는 메소드 이름을 가진 메소드만 선정
        classMethodPointcut.setMappedName("sayH*");

        //테스트
        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget {};    //클래스 이름이 HelloT로 시작을 안하므로 적용 클래스 아님
        checkAdviced(new HelloWorld(), classMethodPointcut, false);

        class HelloTest extends HelloTarget {};
        checkAdviced(new HelloTest(), classMethodPointcut, true);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();

        if(adviced) {
            assertThat(proxiedHello.sayHello("test1"), CoreMatchers.is("HELLO TEST1"));
            assertThat(proxiedHello.sayHi("test1"),  CoreMatchers.is("HI TEST1"));
            assertThat(proxiedHello.sayThankYou("test1"),  CoreMatchers.is("Thank You test1"));
        }else{
            assertThat(proxiedHello.sayHello("test1"), CoreMatchers.is("Hello test1"));
            assertThat(proxiedHello.sayHi("test1"),  CoreMatchers.is("Hi test1"));
            assertThat(proxiedHello.sayThankYou("test1"),  CoreMatchers.is("Thank You test1"));
        }
    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String) methodInvocation.proceed();
            return ret.toUpperCase();
        }
    }


}
