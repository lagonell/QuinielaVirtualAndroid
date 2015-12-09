package com.eoldsolutions.quinielavirtualandroid.common.bus.response.data;

import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BusEvent;
import com.eoldsolutions.quinielavirtualandroid.common.exception.DataError;

/**
 * Common methods for responses
 */
public abstract class BaseDataResponse implements BusEvent {

    protected DataError error;

    public BaseDataResponse() {
        this.error = null;
    }

    public BaseDataResponse(DataError error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }

    public DataError getError() {
        return error;
    }

    public void setError(DataError error) {
        this.error = error;
    }

}
