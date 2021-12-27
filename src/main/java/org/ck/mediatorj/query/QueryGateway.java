package org.ck.mediatorj.query;

/**
 * 
 * @author Chris
 * @since Nov 26, 2021
 *
 */
public interface QueryGateway {

    <Q extends QueryMessage<R>, R> R send(Q query);

}
