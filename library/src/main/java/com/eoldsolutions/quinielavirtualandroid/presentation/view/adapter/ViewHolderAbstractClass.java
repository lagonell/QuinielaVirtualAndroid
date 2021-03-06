package com.eoldsolutions.quinielavirtualandroid.presentation.view.adapter;

import android.content.Context;
import android.view.View;

import com.eoldsolutions.quinielavirtualandroid.domain.model.BaseModel;

public abstract class ViewHolderAbstractClass<T extends BaseModel> {

    abstract public int getLayout();

    abstract public void findViewsById(View view);

    abstract public void setUpView(Context context, T item, int position);

}
