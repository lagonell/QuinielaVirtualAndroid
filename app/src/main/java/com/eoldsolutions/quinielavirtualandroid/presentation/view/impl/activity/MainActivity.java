package com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eoldsolutions.quinielavirtualandroid.presentation.R;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl.MainPresenterImpl;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.MainPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.util.NavigationManager;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.activity.BaseMvpActivity;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.fragment.BaseMvpFragment;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.fragment.MenuFragment;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.fragment.ProfileFragment;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.MainView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MenuFragment.MenuFragmentListener, MainView {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    // View
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Bind(R.id.leftDrawerContainer)
    ViewGroup leftDrawerContainer;

    // Class
    @SuppressLint("UseSparseArrays")
    private Map<Integer, BaseMvpFragment> fragmentsMap;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private int positionToGo;
    private ActionBarDrawerToggle mDrawerToggle;
    private MenuFragment menuFragment;
    private BaseMvpFragment currentFragment;
    private BaseMvpFragment lastFragment;
    private final int INITIAL_POSITION = NavigationManager.SCREENS.POSITION_1.ordinal();
    private ActionBar actionBar;

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        findFragmentsById();

        initializeComponentBehavior();
    }

    private void findFragmentsById() {
        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menuFragment);
        menuFragment.setListener(this);
    }

    public void initializeComponentBehavior() {

        initializeActionBar();

        initializeNavigationDrawer();

        menuClick(INITIAL_POSITION);

        presenter.onViewCreated();
    }

    private void initializeNavigationDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {

            public void onDrawerClosed(View view) {

                super.onDrawerClosed(view);

                setMenuItemsVisible(true);

                if (view.getId() == R.id.leftDrawerContainer) {
                    // Wait for the drawer is closed to realize action
                    if (positionToGo != -1) {
                        menuClick(positionToGo);
                        positionToGo = -1;
                    }
                }
                mDrawerToggle.syncState();
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                setMenuItemsVisible(false);
                mDrawerToggle.syncState();
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    // ************************************************************************************************************************************************************************
    // * Event handler methods
    // ************************************************************************************************************************************************************************

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // MenuMvpFragment events

    @Override
    public void onMenuItemClick(int position) {
        toggleLeftDrawer();
        positionToGo = position;
    }

    // ************************************************************************************************************************************************************************
    // * Auxiliary UI methods
    // ************************************************************************************************************************************************************************

    // Fragment loading methods

    private void menuClick(int position) {

        // Update the main content by replacing fragments
        currentFragment = getFragmentByPosition(position);

        setTitleByPosition(NavigationManager.SCREENS.values()[position]);

        NavigationManager.lastViewPosition = NavigationManager.currentViewPosition;
        NavigationManager.currentViewPosition = position;

        Log.v(NavigationManager.LOG_TAG, "Position to go: " + position);
        Log.v(NavigationManager.LOG_TAG, "NavigationManager.lastViewPosition: " + NavigationManager.lastViewPosition);
        Log.v(NavigationManager.LOG_TAG, "NavigationManager.currentViewPosition: " + NavigationManager.currentViewPosition);

        if (currentFragment != null) {

            if (lastFragment != null && lastFragment == currentFragment) {
                currentFragment = refreshFragment(position);
            } else {
                loadFragment(currentFragment);
            }

            lastFragment = currentFragment;

            setTitle(currentFragment.getTag());

            // Select menu item
            menuFragment.setSelectedMenuPosition(position);

        } else {
            currentFragment = lastFragment;
            switch (NavigationManager.SCREENS.values()[position]) {
                case POSITION_2:
                    Intent intent = new Intent(this, TutorialActivity.class);
                    startActivityForResult(intent, TutorialActivity.REQUEST_CODE);
                    break;
            }
        }
    }

    private void setTitleByPosition(NavigationManager.SCREENS screens) {
        switch (screens) {
            case POSITION_1:
                actionBar.setTitle(getString(R.string.my_profile));
                break;
        }
    }

    private void loadFragment(Fragment currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrameLayout, currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private BaseMvpFragment refreshFragment(int position) {
        fragmentsMap.remove(position);
        BaseMvpFragment currentFragment = getFragmentByPosition(position);
        loadFragment(currentFragment);
        return currentFragment;
    }

    private BaseMvpFragment getFragmentByPosition(int position) {

        if (fragmentsMap == null || fragmentsMap.isEmpty()) {
            fragmentsMap = new HashMap<>();
            fragmentsMap.put(NavigationManager.SCREENS.POSITION_1.ordinal(), new ProfileFragment());
        }
        return fragmentsMap.get(position);
    }

    // ************************************************************************************************************************************************************************
    // * Action bar methods
    // ************************************************************************************************************************************************************************

    private void initializeActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuItem1 = menu.findItem(R.id.item_1);
        menuItem1.setVisible(false);
        menuItem1.setEnabled(false);
        menuItem2 = menu.findItem(R.id.item_2);
        menuItem2.setVisible(false);
        menuItem2.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                toggleLeftDrawer();
                break;
            default:
                break;
        }

        return false;
    }

    // ************************************************************************************************************************************************************************
    // * UI auxiliary methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onBackPressed() {

        // If the menu is opened, close it
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawers();
            setMenuItemsVisible(true);
        }
        // If the back button comes from AddRecordActivity
        else {
            finish();
        }
    }

    // **********************************************************************************************************************************************************************
    // * UI management methods
    // **********************************************************************************************************************************************************************

    public void toggleLeftDrawer() {
        try {
            if (drawerLayout.isDrawerOpen(leftDrawerContainer)) {
                drawerLayout.closeDrawers();
                setMenuItemsVisible(true);
            } else {
                drawerLayout.openDrawer(leftDrawerContainer);
                setMenuItemsVisible(false);
            }
        } catch (Exception e) {
            Log.wtf(NavigationManager.LOG_TAG, "Exception in toggleLeftDrawer: " + e.getMessage());
        }
    }

    private void setMenuItemsVisible(boolean flagVisible) {
        if (menuItem1.isEnabled()) {
            menuItem1.setVisible(flagVisible);
        }
        if (menuItem2.isEnabled()) {
            menuItem2.setVisible(flagVisible);
        }
    }

}