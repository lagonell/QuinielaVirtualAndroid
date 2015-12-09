package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.MainView;

public interface MainPresenter extends MvpPresenter<MainView> {

    void onViewCreated();

}
