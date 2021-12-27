package org.ck.mediatorj.fixture;

import org.ck.mediatorj.event.EventHandler;
import org.ck.mediatorj.fixture.SimpleEventHandler.SimpleEvent;
import org.ck.mediatorj.fixture.SubSimpleEventHandler.SubSimpleEvent;

/**
 * 
 * @author Chris
 * @since Dec 9, 2021
 *
 */
public class SubSimpleEventHandler implements EventHandler<SubSimpleEvent> {

    @Override
    public void handle(SubSimpleEvent event) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

    }

    public static class SubSimpleEvent extends SimpleEvent {

    }

}
