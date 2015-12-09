package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.ProfileView;

public interface ProfilePresenter extends MvpPresenter<ProfileView> {

    void onViewCreated();

    UserModel getUser();

}
