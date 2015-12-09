package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter;

import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.ForgotView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by EOLD on 14/11/2015.
 */
public interface ForgotPresenter extends MvpPresenter<ForgotView> {
    void onActionDoneClick();
}
