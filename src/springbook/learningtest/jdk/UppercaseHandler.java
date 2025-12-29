package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
    Object target;

    //위임해야 하기 때문에 타깃 오브젝트를 주입Object받아 둔다.
    //어떤 종류의 인터페이스를 구현한 타겟이도 적용 가능하도록 Object타입으로 수정
    public UppercaseHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //타깃으로 위임. 인터페이스의 메소드 호출에 모두 적용.
        Object ret = method.invoke(target, args);
        //호출한 메소드의 return값이 String일 경우에만 대문자 변경 가능하도록 수정
//        if(ret instanceof String){
//            return ((String)ret).toUpperCase();
//        }else{
//            return ret;
//        }

        //리턴타입과 메소드 이름이 일치하는 경우에만 부가기능을 적용한다.
        if(ret instanceof String && method.getName().startsWith("say")) {
            return ((String) ret).toUpperCase();
        }else{
            return ret;
        }
    }
}
