package org.ck.mediatorj.configuration;

import org.ck.mediatorj.command.CommandGateway;
import org.ck.mediatorj.command.FailsafeCommandGateway;
import org.ck.mediatorj.core.DefaultMediator;
import org.ck.mediatorj.core.Mediator;
import org.ck.mediatorj.event.EventPublisher;
import org.ck.mediatorj.event.FailsafeEventPublisher;
import org.ck.mediatorj.messaging.MessageBus;
import org.ck.mediatorj.messaging.MessageHandlerBeanPostProcessor;
import org.ck.mediatorj.messaging.SimpleMessageBus;
import org.ck.mediatorj.query.FailsafeQueryGateway;
import org.ck.mediatorj.query.QueryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
@Configuration
public class MediatorConfiguration {

    @Bean
    MessageHandlerBeanPostProcessor messageHandlerBeanPostProcessor() {
        return new MessageHandlerBeanPostProcessor();
    }

    @Bean
    MessageBus messageBus() {
        return new SimpleMessageBus();
    }

    @Bean
    CommandGateway commandGateway(MessageBus messageBus) {
        return new FailsafeCommandGateway(messageBus);
    }

    @Bean
    QueryGateway queryGateway(MessageBus messageBus) {
        return new FailsafeQueryGateway(messageBus);
    }

    @Bean
    EventPublisher eventPublisher(MessageBus messageBus) {
        return new FailsafeEventPublisher(messageBus);
    }

    @Bean
    Mediator mediator(CommandGateway commandGateway, QueryGateway queryGateway, EventPublisher eventPublisher) {
        return new DefaultMediator(commandGateway, queryGateway, eventPublisher);
    }

}
