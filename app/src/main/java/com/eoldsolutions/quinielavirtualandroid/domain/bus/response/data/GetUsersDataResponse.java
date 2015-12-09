package com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data;

import com.eoldsolutions.quinielavirtualandroid.common.bus.response.data.BaseDataResponse;

import org.json.JSONObject;

public class GetUsersDataResponse extends BaseDataResponse {

    private final JSONObject response;

    public GetUsersDataResponse(JSONObject response) {
        this.response = response;
    }

    public JSONObject getResponse() {
        return response;
    }
}
