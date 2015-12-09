package com.eoldsolutions.quinielavirtualandroid.presentation.view.inter;

import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.BaseView;

import java.util.List;

public interface ProfileView extends BaseView {

    void loadData(List<UserModel> modelList);
}
