package com.neteasy.senior.aspect;

import com.neteasy.senior.annotation.MyCached;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class CacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;


    @Around("@annotation(com.neteasy.senior.annotation.MyCached)")//方法执行前后执行
    public Object queryCached(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = pjp.getTarget().getClass().getMethod(signature.getName(),signature.getMethod().getParameterTypes());
        MyCached myCached = method.getAnnotation(MyCached.class);
        System.out.println("方法执行前执行..增强");//从缓存中获取数据
        String keyEl = myCached.key();
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(keyEl);
        EvaluationContext context =new StandardEvaluationContext();
        Object[] args = pjp.getArgs();
        String[] paramNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
        for(int i =0; i < args.length; i++){
            context.setVariable(paramNames[i],args[i]);
        }
        String key = expression.getValue(context).toString();
        Object value = redisTemplate.opsForValue().get(key);
        if (value !=null){
            //若获取到数据后直接返回
            return value;

        }

        Object res = pjp.proceed();
        System.out.println("方法执行后执行..增强");//刷新缓存
        redisTemplate.opsForSet().add(key,res);
        return res;
    }


}
