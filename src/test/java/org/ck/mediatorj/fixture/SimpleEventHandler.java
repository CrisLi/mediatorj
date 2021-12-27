package org.ck.mediatorj.fixture;

import org.ck.mediatorj.event.EventHandler;
import org.ck.mediatorj.event.EventMessage;
import org.ck.mediatorj.fixture.SimpleEventHandler.SimpleEvent;

/**
 * 
 * @author Chris
 * @since Dec 3, 2021
 *
 */
public class SimpleEventHandler implements EventHandler<SimpleEvent> {

    @Override
    public void handle(SimpleEvent event) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

    }

    public static class SimpleEvent implements EventMessage {

    }

}
