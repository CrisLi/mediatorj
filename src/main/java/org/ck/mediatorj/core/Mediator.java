package org.ck.mediatorj.core;

import org.ck.mediatorj.command.CommandGateway;
import org.ck.mediatorj.event.EventPublisher;
import org.ck.mediatorj.query.QueryGateway;

/**
 * 
 * @author Chris
 * @since Nov 19, 2021
 *
 */
public interface Mediator extends CommandGateway, QueryGateway, EventPublisher {

}
