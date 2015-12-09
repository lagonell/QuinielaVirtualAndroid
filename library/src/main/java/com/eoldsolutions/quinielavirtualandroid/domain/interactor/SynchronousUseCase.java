package com.eoldsolutions.quinielavirtualandroid.domain.interactor;

import com.eoldsolutions.quinielavirtualandroid.common.bus.BusHandler;
import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BusEvent;

/**
 * Some use cases could be executed without any synchronization
 */
public abstract class SynchronousUseCase extends BusHandler implements UseCase {

    protected abstract BusEvent createRequest();

    public abstract Object execute();
}
