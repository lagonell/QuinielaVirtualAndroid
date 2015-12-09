package com.eoldsolutions.quinielavirtualandroid.common.bus;

import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BusEvent;
import com.eoldsolutions.quinielavirtualandroid.common.exception.DataError;

public abstract class BusHandler {

    // ************************************************************************************************************************************************************************
    // * Constructor
    // ************************************************************************************************************************************************************************

    public BusHandler() {
        registerToBus();
    }

    // ************************************************************************************************************************************************************************
    // * Registration methods
    // ************************************************************************************************************************************************************************

    protected void registerToBus() {
        BusProvider.register(this);
    }

    protected void unregisterFromBus() {
        BusProvider.unregister(this);
    }

    // ************************************************************************************************************************************************************************
    // * Post methods
    // ************************************************************************************************************************************************************************

    protected void postException(DataError exception) {
        BusProvider.post(exception);
    }

    protected void postSticky(BusEvent busEvent) {
        BusProvider.postSticky(busEvent);
    }

    protected void post(BusEvent busEvent) {
        BusProvider.post(busEvent);
    }

    // ************************************************************************************************************************************************************************
    // * Exception handler methods
    // ************************************************************************************************************************************************************************

    public void onEventAsync(DataError exception) {
        handleException(exception);
    }

    protected void handleException(DataError exception) {
        postException(exception);
        unregisterFromBus();
    }
}
