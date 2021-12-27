package org.ck.mediatorj.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.ck.mediatorj.fixture.HandlerInvocationCounter;
import org.ck.mediatorj.fixture.SimpleCommandHandler;
import org.ck.mediatorj.fixture.SimpleCommandHandler.SimpleCommand;
import org.ck.mediatorj.fixture.SimpleCommandHandler.SimpleCommandResponse;
import org.ck.mediatorj.fixture.SimpleEventHandler;
import org.ck.mediatorj.fixture.SimpleEventHandler.SimpleEvent;
import org.ck.mediatorj.fixture.SimpleQueryHandler;
import org.ck.mediatorj.fixture.SimpleQueryHandler.SimpleQuery;
import org.ck.mediatorj.fixture.SubSimpleCommandHandler;
import org.ck.mediatorj.fixture.SubSimpleCommandHandler.SubSimpleCommand;
import org.ck.mediatorj.fixture.SubSimpleEventHandler;
import org.ck.mediatorj.fixture.SubSimpleEventHandler.SubSimpleEvent;
import org.ck.mediatorj.messaging.MessageHandlerBeanPostProcessorTests.TestsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

/**
 * 
 * @author Chris
 * @since Dec 8, 2021
 *
 */
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
@SpringBootTest(classes = TestsConfig.class)
public class MessageHandlerBeanPostProcessorTests {

    @Autowired
    private GenericApplicationContext context;

    @BeforeEach
    void beforeEach() {
        HandlerInvocationCounter.cleanUp();
    }

    @Test
    @Order(10)
    void whenSimpleCommandHandlerRegistered() {

        SimpleCommand command = new SimpleCommand();

        MessageApplicationEvent<SimpleCommand, SimpleCommandResponse> event = new MessageApplicationEvent<>(this, command);

        context.publishEvent(event);

        int count = HandlerInvocationCounter.totalCountOf(SimpleCommandHandler.class);

        assertThat(count).isEqualTo(1);

    }

    @Test
    @Order(20)
    void when2SimpleCommandHandlerRegistered() {

        Throwable error = assertThrows(BeanCreationException.class,
            () -> registerMessageHandler("anotherSimpleCommandHandler", new SimpleCommandHandler()));

        assertThat(error.getCause()).isInstanceOf(IllegalMessageHandlerException.class);

    }

    @Test
    @Order(30)
    void whenSubSimpleCommandHandlerRegistered() {

        registerMessageHandler("subSimpleCommandHandler", new SubSimpleCommandHandler());

        SubSimpleCommand command = new SubSimpleCommand();

        context.publishEvent(new MessageApplicationEvent<>(this, command));

        int count = HandlerInvocationCounter.totalCountOf(SubSimpleCommandHandler.class);

        assertThat(count).isEqualTo(1);

    }

    @Test
    @Order(40)
    void whenSimpleEventPublished() {

        SimpleEvent event = new SimpleEvent();

        context.publishEvent(new MessageApplicationEvent<>(this, event));

        int count = HandlerInvocationCounter.totalCountOf(SimpleEventHandler.class);

        assertThat(count).isEqualTo(1);

        count = HandlerInvocationCounter.totalCountOf(SubSimpleEventHandler.class);

        assertThat(count).isEqualTo(0);

    }

    @Test
    @Order(50)
    void whenSubSimpleEventPublished() {

        SubSimpleEvent event = new SubSimpleEvent();

        context.publishEvent(new MessageApplicationEvent<>(this, event));

        int count = HandlerInvocationCounter.totalCountOf(SubSimpleEventHandler.class);

        assertThat(count).isEqualTo(1);

        count = HandlerInvocationCounter.totalCountOf(SimpleEventHandler.class);

        assertThat(count).isEqualTo(1);

    }

    @Test
    @Order(60)
    void whenProxyHandlerRegistered() {

        SimpleQuery query = new SimpleQuery();

        context.publishEvent(new MessageApplicationEvent<>(this, query));

        int count = HandlerInvocationCounter.totalCountOf(SimpleQueryHandler.class);

        assertThat(count).isEqualTo(1);

    }

    void registerMessageHandler(String beanName, MessageHandler<?, ?> handler) {
        context.registerBean(beanName, MessageHandler.class, () -> handler);
        context.getBean(beanName);
    }

    @Configuration
    static class TestsConfig {

        @Bean
        MessageHandlerBeanPostProcessor messageHandlerBeanPostProcessor() {
            return new MessageHandlerBeanPostProcessor();
        }

        @Bean
        SimpleCommandHandler simpleCommandHandler() {
            return new SimpleCommandHandler();
        }

        @Bean
        SimpleEventHandler simpleEventHandler() {
            return new SimpleEventHandler();
        }

        @Bean
        SubSimpleEventHandler subSimpleEventHandler() {
            return new SubSimpleEventHandler();
        }

        @Bean
        SimpleQueryHandler simpleQueryHandler() {

            ProxyFactory factory = new ProxyFactory(new SimpleQueryHandler());

            factory.setProxyTargetClass(true);
            factory.setExposeProxy(true);

            SimpleQueryHandler handler = (SimpleQueryHandler) factory.getProxy(SimpleQueryHandler.class.getClassLoader());

            return handler;
        }

    }

}
