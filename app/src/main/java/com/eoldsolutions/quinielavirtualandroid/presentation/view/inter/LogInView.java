package com.eoldsolutions.quinielavirtualandroid.presentation.view.inter;

import com.eoldsolutions.quinielavirtualandroid.presentation.view.BaseView;

public interface LogInView extends BaseView {

    String getUsername();

    String getPassword();

    void invalidateUsername();

    void invalidatePassword();

    void goToMain();

}
