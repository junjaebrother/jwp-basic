package core.di.inject;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

public abstract class AbstractInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(AbstractInjector.class);

    private BeanFactory beanFactory;

    public AbstractInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        Set<?> injectedBeans = getInjectedBeans(clazz);
        for(Object injectedBean : injectedBeans) {
            Class<?> beanClass = getBeanClass(injectedBean);
            inject(injectedBean, instantiateClass(beanClass), beanFactory);
        }
    }

    abstract Set<?> getInjectedBeans(Class<?> clazz);

    abstract Class<?> getBeanClass(Object injectedBean);

    abstract void inject(Object injectedBean, Object bean, BeanFactory beanFactory);

    private Object instantiateClass(Class<?> clazz) {
        Class<?> concreateClass = findBeanClass(clazz, beanFactory.getPreInstanticateBeans());
        Object bean = beanFactory.getBean(concreateClass);
        if(bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreateClass);
        if(injectedConstructor == null) {
            bean = BeanUtils.instantiate(concreateClass);
            beanFactory.registerBean(concreateClass, bean);
            return bean;
        }

        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beanFactory.registerBean(concreateClass, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = findBeanClass(clazz, beanFactory.getPreInstanticateBeans());
            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    private Class<?> findBeanClass(Class<?> clazz, Set<Class<?>> preInstanticateBeans) {
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
        if (!preInstanticateBeans.contains(concreteClazz)) {
            throw new IllegalStateException(clazz + "는 Bean이 아니다.");
        }
        return concreteClazz;
    }
}
