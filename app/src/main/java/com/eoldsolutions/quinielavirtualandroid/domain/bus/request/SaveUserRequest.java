package com.eoldsolutions.quinielavirtualandroid.domain.bus.request;

import com.eoldsolutions.quinielavirtualandroid.common.bus.event.BaseEvent;
import com.eoldsolutions.quinielavirtualandroid.domain.entity.UserEntity;

public class SaveUserRequest extends BaseEvent {

    private final UserEntity user;

    public SaveUserRequest(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
