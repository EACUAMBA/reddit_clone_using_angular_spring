package com.eacuamba.dev.reddit_clone_using_angular_spring.helper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Getter
@Component
@Slf4j
public final class ApplicationContextHelper implements ApplicationContextAware {
    private ApplicationContextHelper(){}
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T findBeanByClass(Class<T> tClass){
        log.debug(String.format("Tentando encontrar o bean tClass(%s) .", tClass.getName()));
        return applicationContext.getBean(tClass);
    }

    public static <T> T findBeanByClassAndType(Class<T> tClass, Class<?> type){
        log.debug(String.format("Tentando encontrar o bean tClass(%s) com o type (%s).", tClass.getName(), type.getName()));
        String[] applicationContextBeanNamesForType = ApplicationContextHelper.applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(tClass, type));
        if(ArrayUtils.isEmpty(applicationContextBeanNamesForType)){
            throw new RuntimeException(String.format("O bean tClass(%s) com o type (%s) não foi encontrado!", tClass.getName(), type.getName()));
        }
        return (T)applicationContext.getBean(applicationContextBeanNamesForType[0], tClass);
    }
}
