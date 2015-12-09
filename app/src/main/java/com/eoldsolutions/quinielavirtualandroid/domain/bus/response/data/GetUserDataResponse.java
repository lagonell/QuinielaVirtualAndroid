package com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data;

import com.eoldsolutions.quinielavirtualandroid.common.bus.response.data.BaseDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.entity.UserEntity;

public class GetUserDataResponse extends BaseDataResponse {

    private final UserEntity user;

    public GetUserDataResponse(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
