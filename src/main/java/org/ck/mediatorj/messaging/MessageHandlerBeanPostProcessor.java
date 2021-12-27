package org.ck.mediatorj.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chris
 * @since Dec 2, 2021
 *
 */
@Slf4j
public class MessageHandlerBeanPostProcessor implements DestructionAwareBeanPostProcessor, ApplicationContextAware {

    private GenericApplicationContext context;

    private Map<String, ResolvableType> responsibleHanderBeans = new HashMap<>();

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof MessageHandler) {
            registerMessageHandler(MessageHandler.class.cast(bean), beanName);
        }

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (GenericApplicationContext) applicationContext;
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return (bean instanceof MessageHandler);
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {

        String listenerBeanName = listenerBeanName(beanName);

        if (bean instanceof MessageHandler && this.context.containsBean(listenerBeanName)) {

            this.context.removeBeanDefinition(listenerBeanName);

            log.info("Removed bean '{}' of type [MessageApplicationEventListener]", listenerBeanName);

            synchronized (responsibleHanderBeans) {
                responsibleHanderBeans.remove(beanName);
            }

        }
    }

    private void registerMessageHandler(MessageHandler<?, ?> handler, String beanName) {

        String listenerBeanName = listenerBeanName(beanName);

        MessageApplicationEventListener<?, ?> listener = new MessageApplicationEventListener<>(handler);

        validateMessageApplicationEventListener(beanName, listener);

        this.context.registerBean(
            listenerBeanName,
            MessageApplicationEventListener.class,
            () -> listener);

        this.context.getBean(listenerBeanName, MessageApplicationEventListener.class);

        log.info("Registered MessageApplicationEventListener with bean name '{}' for MessageHandler of type [{}]",
            listenerBeanName,
            handler.getClass());

    }

    private String listenerBeanName(String beanName) {
        return beanName + "@MessageApplicationEventListener";
    }

    private void validateMessageApplicationEventListener(String beanName, MessageApplicationEventListener<?, ?> eventListener) {

        if (eventListener.isResponsible()) {

            synchronized (responsibleHanderBeans) {

                ResolvableType requestType = eventListener.getRequestType();

                Optional<String> cachedBeanName = responsibleHanderBeans.entrySet().stream()
                    .filter(item -> item.getValue().resolve().equals(requestType.resolve()))
                    .map(item -> item.getKey())
                    .findFirst();

                if (cachedBeanName.isPresent()) {
                    throw new IllegalMessageHandlerException(
                        String.format(
                            "%s can been handled by another responsible MessageHandler (bean: '%s'), one responsible MessageHandler should only handle one message type",
                            requestType.toString(),
                            cachedBeanName.get()));
                }

                responsibleHanderBeans.put(beanName, requestType);
            }

        }

    }

}
