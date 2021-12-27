package org.ck.mediatorj.query;

import org.ck.mediatorj.messaging.MessageHandler;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface QueryHandler<Q extends QueryMessage<R>, R> extends MessageHandler<Q, R> {

}
