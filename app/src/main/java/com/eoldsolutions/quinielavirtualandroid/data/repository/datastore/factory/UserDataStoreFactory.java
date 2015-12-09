package com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.factory;

import com.eoldsolutions.quinielavirtualandroid.data.cache.impl.UserCacheImpl;
import com.eoldsolutions.quinielavirtualandroid.data.cache.inter.UserCache;
import com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.cloud.UserCloudDataStore;
import com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.disk.UserDiskDataSource;
import com.eoldsolutions.quinielavirtualandroid.data.repository.net.RepositoryCallback;
import com.eoldsolutions.quinielavirtualandroid.domain.entity.UserEntity;

import org.json.JSONObject;

/**
 * This class applies the logic of selecting which data source
 * use for each situation and the use of the cache
 */
public class UserDataStoreFactory {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    private static UserDataStoreFactory instance;
    private final UserCloudDataStore userCloudDataStore;
    private final UserCache userCache;
    private final UserDiskDataSource userDiskDataSource;

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    public UserDataStoreFactory() {
        userCache = new UserCacheImpl();
        userCloudDataStore = new UserCloudDataStore();
        userDiskDataSource = new UserDiskDataSource();
    }

    public static UserDataStoreFactory getInstance() {
        if (instance == null) {
            instance = new UserDataStoreFactory();
        }
        return instance;
    }

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    public UserEntity getCurrentUser() {
        return userDiskDataSource.get();
    }

    public void getAll(RepositoryCallback<JSONObject> callback) {
        userCloudDataStore.getAll(callback);
    }

    public void logIn(String username, String password, RepositoryCallback<JSONObject> callback) {
        userCloudDataStore.logIn(username, password, callback);
    }

    public void logOut() {
        userDiskDataSource.delete();
    }

    public void put(UserEntity user) {
        userDiskDataSource.put(user);
    }

    public boolean isFirstTimeInApp() {
        return userDiskDataSource.isFirstTimeInApp();
    }
}
