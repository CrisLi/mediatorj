package org.ck.mediatorj.fixture;

import java.util.Collections;
import java.util.List;

import org.ck.mediatorj.fixture.SimpleQueryHandler.SimpleQuery;
import org.ck.mediatorj.fixture.SimpleQueryHandler.SimpleQueryResponse;
import org.ck.mediatorj.query.QueryHandler;
import org.ck.mediatorj.query.QueryMessage;

/**
 * 
 * @author Chris
 * @since Dec 9, 2021
 *
 */
public class SimpleQueryHandler implements QueryHandler<SimpleQuery, List<SimpleQueryResponse>> {

    @Override
    public List<SimpleQueryResponse> handleMessage(SimpleQuery message) {

        HandlerInvocationCounter.addHandledBy(this.getClass());

        return Collections.emptyList();
    }

    public static class SimpleQuery implements QueryMessage<List<SimpleQueryResponse>> {

    }

    public static class SimpleQueryResponse {

    }

}
