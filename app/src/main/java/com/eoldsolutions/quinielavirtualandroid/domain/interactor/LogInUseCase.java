package com.eoldsolutions.quinielavirtualandroid.domain.interactor;

import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.LogInRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.SaveUserRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.LogInDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.domain.LogInDomainResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.mapper.UserMapper;
import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;

public class LogInUseCase extends AsynchronousUseCase {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    private final String username;
    private final String password;
    private LogInDataResponse response;

    // ************************************************************************************************************************************************************************
    // * Constructor
    // ************************************************************************************************************************************************************************

    public LogInUseCase(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    // ************************************************************************************************************************************************************************
    // * Bus events factory methods
    // ************************************************************************************************************************************************************************

    @Override
    protected LogInRequest createRequest() {
        return new LogInRequest(username, password);
    }

    @Override
    protected LogInDomainResponse createResponse() {
        UserModel userModel = UserMapper.parseUser(response.getResponse().toString());
        return new LogInDomainResponse(userModel);
    }

    // ************************************************************************************************************************************************************************
    // * Use case execution
    // ************************************************************************************************************************************************************************

    public void onEventAsync(LogInDataResponse event) {

        this.response = event;
        if (!event.hasError()) {
            LogInDomainResponse response = createResponse();

            // Save current user
            post(new SaveUserRequest(UserMapper.parse(response.getUser())));

            // Return the current user
            post(response);
            unregisterFromBus();
        } else {
            handleException(event.getError());
        }
    }
}
