package com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.cloud;

import com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.UserDataStore;
import com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.cloud.util.ServiceGenerator;
import com.eoldsolutions.quinielavirtualandroid.data.repository.net.RepositoryCallback;
import com.eoldsolutions.quinielavirtualandroid.data.util.NetworkManager;
import com.eoldsolutions.quinielavirtualandroid.domain.entity.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserCloudDataStore implements UserDataStore, CloudRestDataSource<UserEntity> {

    @Override
    public void logIn(String username, String password, RepositoryCallback callback) {
        // Fake response
        try {
            callback.success(new JSONObject("{\r\n  \"id\": 47,\r\n  \"last_login\": \"2015-07-07T14:31:23.133Z\",\r\n  \"is_superuser\": false,\r\n  \"username\": \"SuperUserName\",\r\n  \"first_name\": \"MyFirstName\",\r\n  \"last_name\": \"MyLastName\",\r\n  \"email\": \"my_ultra_fake_email_address@gmail.com\",\r\n  \"is_staff\": false,\r\n  \"is_active\": true,\r\n  \"date_joined\": \"2015-07-07T14:31:23.133Z\",\r\n  \"image\": \"\",\r\n  \"updated\": \"2015-07-07T14:31:23.342Z\",\r\n  \"date_of_birth\": \"1950-12-29\",\r\n  \"token\": \"21b18bca6eefd776227579c93b446ea637cc1384\",\r\n  \"user_id\": 47\r\n}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAll(RepositoryCallback callback) {
        UserRestService userRestService = ServiceGenerator.createService(UserRestService.class, NetworkManager.BASE_API_URL);
        userRestService.getAll(callback);
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("This method must be implemented!!!");
    }

    @Override
    public void put(UserEntity entity) {
        throw new UnsupportedOperationException("This method must be implemented!!!");
    }

    @Override
    public void get(RepositoryCallback callback) {
        throw new UnsupportedOperationException("This method must be implemented!!!");
    }

    @Override
    public boolean isFirstTimeInApp() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

}
