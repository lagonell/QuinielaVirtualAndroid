package com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoldsolutions.quinielavirtualandroid.domain.model.MenuItemModel;
import com.eoldsolutions.quinielavirtualandroid.presentation.R;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.adapter.ViewHolderAbstractClass;

import butterknife.ButterKnife;

public class MenuViewHolder extends ViewHolderAbstractClass<MenuItemModel> {

    ImageView menuImage;
    TextView menuText;
    ViewGroup menuItemContainer;

    @Override
    public int getLayout() {
        return R.layout.menu_list_item;
    }

    @Override
    public void findViewsById(View view) {
        menuItemContainer = ButterKnife.findById(view, R.id.menuItemContainer);
        menuImage = ButterKnife.findById(view, R.id.menuImage);
        menuText = ButterKnife.findById(view, R.id.menuText);
    }

    @Override
    public void setUpView(Context context, MenuItemModel model, int position) {
        menuText.setText(model.getText());
        menuImage.setImageResource(model.getIconId());
    }

}