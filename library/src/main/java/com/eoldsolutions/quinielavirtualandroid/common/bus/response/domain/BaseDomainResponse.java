package com.eoldsolutions.quinielavirtualandroid.common.bus.response.domain;

import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BusEvent;
import com.eoldsolutions.quinielavirtualandroid.common.exception.DomainError;

/**
 * Common methods for responses
 */
public abstract class BaseDomainResponse implements BusEvent {

    protected DomainError error;

    public BaseDomainResponse() {
        this.error = null;
    }

    public BaseDomainResponse(DomainError error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }

    public DomainError getError() {
        return error;
    }

    public void setError(DomainError error) {
        this.error = error;
    }
}
