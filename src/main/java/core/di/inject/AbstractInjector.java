package core.di.inject;

import core.di.factory.BeanFactory;

import static org.springframework.beans.BeanUtils.instantiateClass;

public abstract class AbstractInjector implements Injector {
    private BeanFactory beanFactory;

    public AbstractInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
    }
}
