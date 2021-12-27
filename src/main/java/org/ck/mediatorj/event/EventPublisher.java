package org.ck.mediatorj.event;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface EventPublisher {

    <E extends EventMessage> void publish(E event);

}
