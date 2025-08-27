package core.di.inject;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.instantiateClass;

public class ConstructInjector implements Injector{
    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    private BeanFactory beanFactory;

    public ConstructInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        Class<?>[] pTypes = injectedConstructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz2 : pTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz2, beanFactory.getPreInstanticateBeans());
            if (!beanFactory.getPreInstanticateBeans().contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }
            instantiateClass(concreteClazz);
        }
    }

}
