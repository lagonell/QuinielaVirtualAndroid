package com.eoldsolutions.quinielavirtualandroid.data.repository;

import com.eoldsolutions.quinielavirtualandroid.common.bus.BusHandler;
import com.eoldsolutions.quinielavirtualandroid.common.exception.DataError;
import com.eoldsolutions.quinielavirtualandroid.common.exception.JsonDataException;
import com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.factory.UserDataStoreFactory;
import com.eoldsolutions.quinielavirtualandroid.data.repository.net.RepositoryCallback;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.GetUserRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.GetUsersRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.IsFirstTimeInTheAppRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.IsUserLoggedInRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.LogInRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.LogOutRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.request.SaveUserRequest;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.GetUserDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.GetUsersDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.IsFirstTimeInTheAppDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.IsUserLoggedInDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.LogInDataResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.data.LogOutDataResponse;

import org.json.JSONObject;

/**
 * All the data management must be here. Nothing related to business logic
 */
public class UserRepository extends BusHandler {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    private static UserRepository repository;

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    /**
     * This method must be called when starting the app to connect to the EventBus
     */
    public static void initialize() {
        if (repository == null) {
            repository = new UserRepository();
        }
    }

    // ************************************************************************************************************************************************************************
    // * Synchronous event handler methods
    // ************************************************************************************************************************************************************************

    public void onEvent(IsUserLoggedInRequest request) {
        post(new IsUserLoggedInDataResponse(UserDataStoreFactory.getInstance().getCurrentUser() != null));
    }

    public void onEvent(IsFirstTimeInTheAppRequest request) {
        post(new IsFirstTimeInTheAppDataResponse(UserDataStoreFactory.getInstance().isFirstTimeInApp()));
    }

    public void onEvent(GetUserRequest request) {
        post(new GetUserDataResponse(UserDataStoreFactory.getInstance().getCurrentUser()));
    }

    public void onEvent(LogOutRequest request) {
        UserDataStoreFactory.getInstance().logOut();
        post(new LogOutDataResponse());
    }

    // ************************************************************************************************************************************************************************
    // * Asynchronous event handler methods
    // ************************************************************************************************************************************************************************

    public void onEventBackgroundThread(LogInRequest request) {
        UserDataStoreFactory.getInstance().logIn(request.getUsername(), request.getPassword(), new RepositoryCallback<JSONObject>() {
            @Override
            public void failure(JsonDataException error) {
                postException(new DataError(error.getMessage(), error.getCode()));
            }

            @Override
            public void success(JSONObject response) {
                post(new LogInDataResponse(response));
            }
        });
    }

    public void onEventBackgroundThread(GetUsersRequest request) {
        UserDataStoreFactory.getInstance().getAll(new RepositoryCallback<JSONObject>() {
            @Override
            public void failure(JsonDataException error) {
                postException(new DataError(error.getMessage(), error.getCode()));
            }

            @Override
            public void success(JSONObject response) {
                post(new GetUsersDataResponse(response));
            }
        });
    }

    public void onEventBackgroundThread(SaveUserRequest request) {
        UserDataStoreFactory.getInstance().put(request.getUser());
    }

}
