package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl;

import com.eoldsolutions.quinielavirtualandroid.domain.bus.response.domain.LogInDomainResponse;
import com.eoldsolutions.quinielavirtualandroid.presentation.BasePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.ForgotPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.ForgotView;
import com.marcohc.helperoid.StringHelper;

/**
 * Created by EOLD on 14/11/2015.
 */
public class ForgotPresenterImpl extends BasePresenter<ForgotView> implements ForgotPresenter {

    // ************************************************************************************************************************************************************************
    // * View handler methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onActionDoneClick() {
        if (isFormValid()) {
            showLoadingDialog();
            //TODO: Tengo que crear el Case llamar el webservice y que envie la contraseña.
            //new LogInUseCase(getView().getUsername(), getView().getPassword()).execute();
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

        if (StringHelper.isEmpty(getView().getEmailUser())) {
            getView().invalidateEmailUser();
            return false;
        }
        return true;
    }
}
