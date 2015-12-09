package com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eoldsolutions.quinielavirtualandroid.presentation.R;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl.ForgotPresenterImpl;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.activity.BaseMvpActivity;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.ForgotView;
import com.marcohc.helperoid.ScreenHelper;

import butterknife.Bind;

/**
 * Created by EOLD on 14/11/2015.
 */
public class ForgotActivity extends BaseMvpActivity<ForgotView, ForgotPresenterImpl> implements ForgotView {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    // View
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.emailEditText)
    EditText emailEditText;

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************
    @NonNull
    @Override
    public ForgotPresenterImpl createPresenter() {
        return new ForgotPresenterImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_activity);
        initializeActionBar();
    }

    private void initializeActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem1 = menu.findItem(R.id.item_1);
        menuItem1.setVisible(true);
        menuItem1.setEnabled(true);
        MenuItem menuItem2 = menu.findItem(R.id.item_2);
        menuItem2.setVisible(true);
        menuItem2.setEnabled(true);
        menuItem2.setIcon(R.drawable.abc_ic_go_search_api_mtrl_alpha);
        menuItem2.setTitle(R.string.accept);
        return true;
    }
    // ************************************************************************************************************************************************************************
    // * Event handler methods
    // ************************************************************************************************************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyBoard();
        switch (item.getItemId()) {
            case android.R.id.home:
//                onBackPressed();
                goToMain();
                break;
            case R.id.item_2:
                presenter.onActionDoneClick();
                break;
            default:
                break;
        }
        return false;
    }

    // ************************************************************************************************************************************************************************
    // * View interface methods
    // ************************************************************************************************************************************************************************


    @Override
    public String getEmailUser() {
        return emailEditText.getText().toString();
    }

    @Override
    public void invalidateEmailUser() {
        YoYo.with(Techniques.Shake).playOn(emailEditText);
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    // ************************************************************************************************************************************************************************
    // * UI methods
    // ************************************************************************************************************************************************************************

    private void hideKeyBoard() {
        ScreenHelper.hideKeyboard(this);
    }


}
