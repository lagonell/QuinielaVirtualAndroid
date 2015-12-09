package com.eoldsolutions.quinielavirtualandroid.presentation.view.inter;

import com.eoldsolutions.quinielavirtualandroid.presentation.view.BaseView;

/**
 * Created by EOLD on 14/11/2015.
 */
public interface ForgotView extends BaseView {

    String getEmailUser();

    void invalidateEmailUser();

    void goToMain();

}
