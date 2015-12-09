package com.eoldsolutions.quinielavirtualandroid.presentation.view.inter;

import com.eoldsolutions.quinielavirtualandroid.presentation.view.BaseView;

public interface MenuView extends BaseView {

    void goToLogin();

    void dispatchMenuItemClick(int position);
}
