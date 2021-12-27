package org.ck.mediatorj.fixture;

import java.util.List;

import org.ck.mediatorj.event.EventHandler;
import org.ck.mediatorj.event.EventMessage;
import org.ck.mediatorj.event.PayloadEvent;

/**
 * 
 * @author Chris
 * @since Dec 3, 2021
 *
 */
public class ChildernEventHandlers {

    static abstract class AbstractEvent implements EventMessage {

    }

    public static class ParentEvent extends AbstractEvent {

    }

    public static class SonEvent extends ParentEvent {

    }

    public static class DaughtEvent extends ParentEvent {

    }

    public static class ParentEventHandler implements EventHandler<ParentEvent> {

        @Override
        public void handle(ParentEvent event) {
            HandlerInvocationCounter.addHandledBy(this.getClass());
        }

    }

    public static class SonEventHandler implements EventHandler<SonEvent> {

        @Override
        public void handle(SonEvent event) {
            HandlerInvocationCounter.addHandledBy(this.getClass());
        }

    }

    public static class GenericParentEventHandler<T extends ParentEvent> implements EventHandler<T> {

        @Override
        public void handle(T event) {
            HandlerInvocationCounter.addHandledBy(this.getClass());
        }

    }

    public static class DaughtEventHandler extends GenericParentEventHandler<DaughtEvent> {

    }

    public static class StringPayloadEventHandler implements EventHandler<PayloadEvent<String>> {

        @Override
        public void handle(PayloadEvent<String> event) {
            HandlerInvocationCounter.addHandledBy(this.getClass());
        }

    }

    public static class ListStringPayloadEventHandler implements EventHandler<PayloadEvent<List<String>>> {

        @Override
        public void handle(PayloadEvent<List<String>> event) {
            HandlerInvocationCounter.addHandledBy(this.getClass());
        }

    }

}
