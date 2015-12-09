package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl;

import com.eoldsolutions.quinielavirtualandroid.domain.interactor.IsFirstTimeInTheAppUseCase;
import com.eoldsolutions.quinielavirtualandroid.domain.interactor.IsUserLoggedInUseCase;
import com.eoldsolutions.quinielavirtualandroid.presentation.BasePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.StartPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.StartView;

public class StartPresenterImpl extends BasePresenter<StartView> implements StartPresenter {

    @Override
    public boolean isUserLoggedIn() {
        return new IsUserLoggedInUseCase().execute();
    }

    @Override
    public boolean isFirstTimeInTheApp() {
        return new IsFirstTimeInTheAppUseCase().execute();
    }
}
