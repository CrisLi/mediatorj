package org.ck.mediatorj.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.ck.mediatorj.configuration.MediatorConfiguration;
import org.ck.mediatorj.event.FailsafeEventPublisherTests.TestsConfig;
import org.ck.mediatorj.fixture.ChildernEventHandlers.DaughtEvent;
import org.ck.mediatorj.fixture.ChildernEventHandlers.DaughtEventHandler;
import org.ck.mediatorj.fixture.ChildernEventHandlers.ListStringPayloadEventHandler;
import org.ck.mediatorj.fixture.ChildernEventHandlers.ParentEvent;
import org.ck.mediatorj.fixture.ChildernEventHandlers.ParentEventHandler;
import org.ck.mediatorj.fixture.ChildernEventHandlers.SonEvent;
import org.ck.mediatorj.fixture.ChildernEventHandlers.SonEventHandler;
import org.ck.mediatorj.fixture.ChildernEventHandlers.StringPayloadEventHandler;
import org.ck.mediatorj.fixture.HandlerInvocationCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
@SpringBootTest(classes = TestsConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class FailsafeEventPublisherTests {

    @Autowired
    private FailsafeEventPublisher eventPublisher;

    @BeforeEach
    void beforeEach() {
        HandlerInvocationCounter.cleanUp();
    }

    @Test
    @Order(10)
    void whenSonEventPublished() {

        SonEvent event = new SonEvent();

        eventPublisher.publish(event);

        int count = HandlerInvocationCounter.totalCountOf(SonEventHandler.class);

        assertThat(count).isEqualTo(1);

        count = HandlerInvocationCounter.totalCountOf(ParentEventHandler.class);

        assertThat(count).isEqualTo(1);

        count = HandlerInvocationCounter.totalCountOf(DaughtEventHandler.class);

        assertThat(count).isEqualTo(0);

    }

    @Test
    @Order(20)
    void whenParentEventPublished() {

        ParentEvent event = new ParentEvent();

        eventPublisher.publish(event);

        int count = HandlerInvocationCounter.totalCountOf(SonEventHandler.class);

        assertThat(count).isEqualTo(0);

        count = HandlerInvocationCounter.totalCountOf(ParentEventHandler.class);

        assertThat(count).isEqualTo(1);

        count = HandlerInvocationCounter.totalCountOf(DaughtEventHandler.class);

        assertThat(count).isEqualTo(0);
    }

    @Test
    @Order(30)
    void whenDaughtEventPublished() {

        DaughtEvent event = new DaughtEvent();

        eventPublisher.publish(event);

        int count = HandlerInvocationCounter.totalCountOf(SonEventHandler.class);

        assertThat(count).isEqualTo(0);

        count = HandlerInvocationCounter.totalCountOf(ParentEventHandler.class);

        assertThat(count).isEqualTo(1);

        count = HandlerInvocationCounter.totalCountOf(DaughtEventHandler.class);

        assertThat(count).isEqualTo(1);

    }

    @Test
    @Order(40)
    void whenPayloadEventPublished() {

        PayloadEvent<String> event = new PayloadEvent<>("payload", String.class);

        eventPublisher.publish(event);

        int count = HandlerInvocationCounter.totalCountOf(StringPayloadEventHandler.class);

        assertThat(count).isEqualTo(1);

    }

    @Test
    @Order(50)
    void whenListPayloadEventPublished() {

        PayloadEvent<List<String>> event = new PayloadEvent<List<String>>(Arrays.asList("p1", "p2"), List.class, String.class);

        eventPublisher.publish(event);

        int count = HandlerInvocationCounter.totalCountOf(ListStringPayloadEventHandler.class);

        assertThat(count).isEqualTo(1);

    }

    @Configuration
    @Import(MediatorConfiguration.class)
    static class TestsConfig {

        @Bean
        ParentEventHandler parentEventHandler() {
            return new ParentEventHandler();
        }

        @Bean
        SonEventHandler sonEventHandler() {
            return new SonEventHandler();
        }

        @Bean
        DaughtEventHandler daughtEventHandler() {
            return new DaughtEventHandler();
        }

        @Bean
        StringPayloadEventHandler stringPayloadEventHandler() {
            return new StringPayloadEventHandler();
        }

        @Bean
        ListStringPayloadEventHandler listStringPayloadEventHandler() {
            return new ListStringPayloadEventHandler();
        }

    }

}
