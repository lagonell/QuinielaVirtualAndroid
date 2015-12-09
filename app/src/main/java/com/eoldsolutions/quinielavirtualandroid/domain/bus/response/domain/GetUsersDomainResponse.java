package com.eoldsolutions.quinielavirtualandroid.domain.bus.response.domain;

import com.eoldsolutions.quinielavirtualandroid.common.bus.response.domain.BaseDomainResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;

import java.util.List;

public class GetUsersDomainResponse extends BaseDomainResponse {

    private final List<UserModel> usersList;

    public GetUsersDomainResponse(List<UserModel> usersList) {
        this.usersList = usersList;
    }

    public List<UserModel> getUsersList() {
        return usersList;
    }

}
