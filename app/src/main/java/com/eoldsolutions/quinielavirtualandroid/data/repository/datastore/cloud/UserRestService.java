
package com.eoldsolutions.quinielavirtualandroid.data.repository.datastore.cloud;

import com.eoldsolutions.quinielavirtualandroid.data.repository.net.RepositoryCallback;

import retrofit.http.GET;
import retrofit.http.POST;

public interface UserRestService {

    @POST("/login")
    void logIn(RepositoryCallback callback);

    @GET("/?results=25")
    void getAll(RepositoryCallback callback);

}
