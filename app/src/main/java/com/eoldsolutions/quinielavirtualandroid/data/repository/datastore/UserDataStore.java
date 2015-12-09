package com.eoldsolutions.quinielavirtualandroid.data.repository.datastore;

import com.eoldsolutions.quinielavirtualandroid.data.repository.net.RepositoryCallback;

/**
 * TODO: Specific methods for this model goes here. REST methods are excluded
 */
public interface UserDataStore {

    void logIn(String username, String password, RepositoryCallback callback);

    boolean isFirstTimeInApp();
}
