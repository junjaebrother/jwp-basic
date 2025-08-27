package core.di.inject;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

import static org.springframework.beans.BeanUtils.instantiateClass;

public class FieldInjector implements Injector{
    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);
    private BeanFactory beanFactory;

    public FieldInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        Set<Field> injectedField = BeanFactoryUtils.getInjectedFields(clazz);
        for(Field field : injectedField) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstanticateBeans());
            Object bean = beanFactory.getBean(concreteClazz);
            if(bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            try {
                field.setAccessible(true);
                field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }
    }


}
