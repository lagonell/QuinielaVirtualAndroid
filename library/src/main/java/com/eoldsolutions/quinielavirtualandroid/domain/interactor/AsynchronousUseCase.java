package com.eoldsolutions.quinielavirtualandroid.domain.interactor;

import com.eoldsolutions.quinielavirtualandroid.common.bus.BusHandler;
import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BusEvent;

public abstract class AsynchronousUseCase extends BusHandler implements UseCase {

    public void execute() {
        BusEvent request = createRequest();
        if (request != null) {
            post(request);
        }
    }

    protected abstract BusEvent createRequest();

    protected abstract BusEvent createResponse();

}
