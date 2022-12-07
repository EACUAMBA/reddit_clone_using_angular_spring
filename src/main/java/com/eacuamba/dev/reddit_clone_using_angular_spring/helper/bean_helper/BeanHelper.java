package com.eacuamba.dev.reddit_clone_using_angular_spring.helper.bean_helper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * This helper class is responsible for help manage object instances, POJOs, JavaBeans and etc.
 */
@Component
public class BeanHelper {

    /**
     * This is responsible for copying the propertiees values from an object to another ignoring the null fields.
     * @param source the class with the null properties
     * @param target th class with values
     * @param <T> type of the class
     */
    public <T> void copyNonNullProperties(T source, T target) {
        BeanUtils.copyProperties(source, target, this.getNullProperties(source));
    }

    /**
     * This class is responsible to get all properties that are null.
     * @param t the bean with the null properties.
     * @return String[] an array with the propperties names with null values.
     * @param <T> the type of bean.
     */
    private <T> String[] getNullProperties(T t) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(t);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        List<String> nullProperties = new ArrayList<>();
        for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
            Object propertyValue = beanWrapper.getPropertyValue(propertyDescriptor.getName());
            if(isNull(propertyValue)){
                nullProperties.add(propertyDescriptor.getName());
            }
        }

        return nullProperties.toArray(new String[]{});
    }
}