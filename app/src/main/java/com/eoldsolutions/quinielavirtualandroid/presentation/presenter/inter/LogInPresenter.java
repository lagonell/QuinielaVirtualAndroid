package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter;


import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.LogInView;

public interface LogInPresenter extends MvpPresenter<LogInView> {

    void onActionDoneClick();

}
