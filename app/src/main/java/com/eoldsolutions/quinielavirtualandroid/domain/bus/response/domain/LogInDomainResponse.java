package com.eoldsolutions.quinielavirtualandroid.domain.bus.response.domain;

import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BaseEvent;
import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;

public class LogInDomainResponse extends BaseEvent {

    private final UserModel userModel;

    public LogInDomainResponse(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUser() {
        return userModel;
    }
}
