package com.eoldsolutions.quinielavirtualandroid.domain.bus.request;

import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BaseEvent;

public class LogInRequest extends BaseEvent {

    private final String username;
    private final String password;

    public LogInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
