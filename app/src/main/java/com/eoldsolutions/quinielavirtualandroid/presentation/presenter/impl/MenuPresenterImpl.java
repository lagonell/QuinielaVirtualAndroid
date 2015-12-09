package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl;

import com.eoldsolutions.quinielavirtualandroid.domain.interactor.LogOutUseCase;
import com.eoldsolutions.quinielavirtualandroid.presentation.BasePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.MenuPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.MenuView;

public class MenuPresenterImpl extends BasePresenter<MenuView> implements MenuPresenter {

    // ************************************************************************************************************************************************************************
    // * View handler methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onLogOutContainerClick() {
        new LogOutUseCase().execute();
        getView().goToLogin();
    }

    @Override
    public void onMenuItemClick(int position) {
        getView().dispatchMenuItemClick(position);
    }

    // ************************************************************************************************************************************************************************
    // * Presenter methods
    // ************************************************************************************************************************************************************************

}
