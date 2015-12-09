package com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.MenuView;

public interface MenuPresenter extends MvpPresenter<MenuView> {

    void onLogOutContainerClick();

    void onMenuItemClick(int position);

}
