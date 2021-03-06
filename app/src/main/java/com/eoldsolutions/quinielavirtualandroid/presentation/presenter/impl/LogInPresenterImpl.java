package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl;

import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.domain.LogInDomainResponse;
import com.eoldsolutions.quinielavirtualandroid.domain.interactor.LogInUseCase;
import com.eoldsolutions.quinielavirtualandroid.presentation.BasePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.LogInPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.LogInView;
import com.marcohc.helperoid.StringHelper;

public class LogInPresenterImpl extends BasePresenter<LogInView> implements LogInPresenter {

    // ************************************************************************************************************************************************************************
    // * View handler methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onActionDoneClick() {
        if (isFormValid()) {
            showLoadingDialog();
            new LogInUseCase(getView().getUsername(), getView().getPassword()).execute();
        }
    }

    // ************************************************************************************************************************************************************************
    // * Interactor handler methods
    // ************************************************************************************************************************************************************************

    public void onEventMainThread(LogInDomainResponse event) {
        hideDialog();
        getView().goToMain();
    }

    // ************************************************************************************************************************************************************************
    // * Presenter methods
    // ************************************************************************************************************************************************************************

    private boolean isFormValid() {

        if (StringHelper.isEmpty(getView().getUsername())) {
            getView().invalidateUsername();
            return false;
        }

        if (StringHelper.isEmpty(getView().getPassword())) {
            getView().invalidatePassword();
            return false;
        }

        return true;
    }

}
