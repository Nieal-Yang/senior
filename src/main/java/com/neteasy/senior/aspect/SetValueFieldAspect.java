package com.neteasy.senior.aspect;

import com.neteasy.senior.util.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Aspect
public class SetValueFieldAspect {
    @Autowired
    private BeanUtil beanUtil;

    @Around("@annotation(com.neteasy.senior.annotation.NeedSetFieldValue)")
    public Object doSetFieldValue(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = pjp.proceed();//等价于去执行被@NeedSetFieldValue修饰的方法
        if (ret instanceof Collection) {
            this.beanUtil.setValueFieldForCollection((Collection) ret);
        }

        return ret;
    }


}
