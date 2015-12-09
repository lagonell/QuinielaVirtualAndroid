package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl;

import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.domain.GetUsersDomainResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.interactor.GetUserUseCase;
import com.eoldsolutions.quinielavirtualandroid.domain.interactor.GetUsersUseCase;
import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;
import com.eoldsolutions.quinielavirtualandroid.presentation.BasePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.ProfilePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.ProfileView;

@SuppressWarnings("ConstantConditions")
public class ProfilePresenterImpl extends BasePresenter<ProfileView> implements ProfilePresenter {

    // ************************************************************************************************************************************************************************
    // * View handler methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onViewCreated() {
        showLoadingDialog();
        new GetUsersUseCase().execute();
    }

    @Override
    public UserModel getUser() {
        return new GetUserUseCase().execute();
    }

    // ************************************************************************************************************************************************************************
    // * Interactor handler methods
    // ************************************************************************************************************************************************************************

    public void onEventMainThread(GetUsersDomainResponse event) {
        hideDialog();
        if (isViewAttached()) {
            getView().loadData(event.getUsersList());
        }
    }
}
