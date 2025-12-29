package springbook.learningtest.factorybean;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/FactoryBeanTest-context.xml"})
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean(){
        Object message = context.getBean("message"); //xml에서 id=message인 빈 찾기
        assertThat(message, is(Message.class));
        assertThat(((Message) message).getText(), is("Factory Bean")); //설정과 기능 확인
    }

    @Test
    public void getFactoryBean() throws Exception{
        Object factory = context.getBean("&message");//&를 붙이면 빈팩토리를 return해준다.
        assertThat(factory, is(MessageFactoryBean.class));
    }
}
