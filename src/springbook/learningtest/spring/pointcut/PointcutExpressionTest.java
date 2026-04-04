package springbook.learningtest.spring.pointcut;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PointcutExpressionTest {

    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException{
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int springbook.learningtest.spring.pointcut.Target.minus(int,int) throws java.lang.RuntimeException)");

        Assert.assertThat(pointcut.getClassFilter().matches(Target.class)
                        && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null)
        , CoreMatchers.is(true));

        Assert.assertThat(pointcut.getClassFilter().matches(Target.class)
                        && pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null)
        , CoreMatchers.is(false));

        Assert.assertThat(pointcut.getClassFilter().matches(Bean.class)
                        && pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null)
                , CoreMatchers.is(false));
    }
}
