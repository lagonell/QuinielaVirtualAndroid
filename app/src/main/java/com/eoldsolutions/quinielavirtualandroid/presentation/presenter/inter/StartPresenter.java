package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.StartView;

public interface StartPresenter extends MvpPresenter<StartView> {

    boolean isUserLoggedIn();

    boolean isFirstTimeInTheApp();
}
