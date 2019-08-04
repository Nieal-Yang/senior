package com.neteasy.senior.util;

import com.neteasy.senior.annotation.NeedSetValue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

@Component
public class BeanUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }

    public void setValueFieldForCollection(Collection col) throws Exception {

        Class<?> clazz = col.iterator().next().getClass();

        Field[] fields = clazz.getFields();

        for (Field needField : fields) {
            NeedSetValue nsv = needField.getAnnotation(NeedSetValue.class);
            if (nsv == null) continue;
            needField.setAccessible(true);

            Field targetField = null;

            Object bean = this.applicationContext.getBean(nsv.beanClass());
            Method method = nsv.beanClass().getMethod(nsv.method(), clazz.getDeclaredField(nsv.param()).getType());
            Field fieldParam = clazz.getDeclaredField(nsv.param());
            fieldParam.setAccessible(true);

            for (Object obj : col) {
                Object paramValue = fieldParam.get(obj);
                if (paramValue == null) continue;
                Object value = method.invoke(bean, paramValue);

                if(value != null){
                    targetField = value.getClass().getDeclaredField(nsv.targetField());
                }

                value = targetField.get(value);
                needField.set(obj,value);


            }

        }

    }
}
