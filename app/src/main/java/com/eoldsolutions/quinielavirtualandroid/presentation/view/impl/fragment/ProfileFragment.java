package com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eoldsolutions.quinielavirtualandroid.domain.model.UserModel;
import com.eoldsolutions.quinielavirtualandroid.presentation.R;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl.ProfilePresenterImpl;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.ProfilePresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.adapter.BaseListAdapter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.fragment.BaseMvpFragment;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.adapter.viewholder.UserViewHolder;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.ProfileView;
import com.marcohc.helperoid.StringHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ProfileFragment extends BaseMvpFragment<ProfileView, ProfilePresenter> implements ProfileView, AdapterView.OnItemClickListener {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************
    @Bind(R.id.userImage)
    ImageView userImage;

    @Bind(R.id.usernameText)
    TextView usernameText;

    @Bind(R.id.addressText)
    TextView addressText;

    @Bind(R.id.dateOfBirthAndEmailText)
    TextView dateOfBirthAndEmailText;

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.noDataText)
    TextView noDataText;

    private BaseListAdapter<UserModel> listViewAdapter;
    private UserModel user;

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    @Override
    protected int getLayoutRes() {
        return R.layout.profile_fragment;
    }

    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenterImpl();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        initializeListView();
        presenter.onViewCreated();
    }

    private void initializeListView() {
        listViewAdapter = new BaseListAdapter<>(getActivity(), R.layout.user_list_item, new ArrayList<UserModel>(), UserViewHolder.class);
        listView.setOnItemClickListener(this);
        listView.setAdapter(listViewAdapter);
    }

    // ************************************************************************************************************************************************************************
    // * Event handler methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        user = (UserModel) listViewAdapter.getItem(position);
        setUserData(user);
    }

    // ************************************************************************************************************************************************************************
    // * View interface methods
    // ************************************************************************************************************************************************************************

    @Override
    public void loadData(List<UserModel> modelList) {
        if (modelList.isEmpty()) {
            noDataText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            noDataText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listViewAdapter.clear();
            listViewAdapter.addThemAll(modelList);
            listViewAdapter.notifyDataSetChanged();
        }
    }

    // ************************************************************************************************************************************************************************
    // * UI methods
    // ************************************************************************************************************************************************************************

    private void setUserData(UserModel user) {
        if (!StringHelper.isEmpty(user.getPicture().getThumbnail())) {
            Picasso.with(getActivity()).load(user.getPicture().getThumbnail()).into(userImage);
        }
        usernameText.setText(user.getUsername());
        addressText.setText(user.getLocation().getStreet());
        dateOfBirthAndEmailText.setText(user.getEmail());
    }
}
