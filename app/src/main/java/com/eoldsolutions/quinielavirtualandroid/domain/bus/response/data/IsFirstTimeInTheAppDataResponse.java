package com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data;

import com.eoldsolutions.quinielavirtualandroid.common.bus.response.data.BaseDataResponse;

public class IsFirstTimeInTheAppDataResponse extends BaseDataResponse {

    private boolean firstTimeInTheApp;

    public IsFirstTimeInTheAppDataResponse(boolean firstTimeInApp) {
        this.firstTimeInTheApp = firstTimeInApp;
    }

    public boolean isFirstTimeInTheApp() {
        return firstTimeInTheApp;
    }
}
