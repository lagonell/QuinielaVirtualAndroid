package com.eoldsolutions.quinielavirtualandroid.presentation;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.eoldsolutions.quinielavirtualandroid.common.exception.DataError;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.BaseView;

@SuppressWarnings("ConstantConditions")
public abstract class BasePresenter<V extends BaseView> extends MvpBasePresenter<V> {

    protected static final String LOG_TAG = "BasePresenter";

    /**
     * Shows or hides dialog with "Loading..." text by default
     */
    public void showLoadingDialog() {
        if (isViewAttached()) {
            getView().showLoadingDialog();
        }
    }

    /**
     * Shows dialog with message
     *
     * @param message dialog message
     */
    public void showDialog(String message) {
        if (isViewAttached()) {
            getView().showDialog(message);
        }
    }

    /**
     * Shows dialog with message
     *
     * @param message dialog message
     */
    public void showDialog(String title, String message) {
        if (isViewAttached()) {
            getView().showDialog(title, message);
        }
    }

    /**
     * Shows dialog with message
     *
     * @param message dialog message
     */
    public void showDialog(String title, String message, boolean isCancelable) {
        if (isViewAttached()) {
            getView().showDialog(title, message, isCancelable);
        }
    }

    /**
     * Shows or hides dialog with "Loading..." text by default
     */
    public void hideDialog() {
        if (isViewAttached()) {
            getView().hideDialog();
        }
    }

    /**
     * Shows error message
     *
     * @param errorMessage the error message to be shown
     */
    public void showError(String errorMessage) {
        if (isViewAttached()) {
            getView().showError(errorMessage);
        }
    }

    /**
     * Shows warning message
     *
     * @param warningMessage the error message to be shown
     */
    public void showWarning(String warningMessage) {
        if (isViewAttached()) {
            getView().showWarning(warningMessage);
        }
    }

    /**
     * Shows message
     *
     * @param message the message to be shown
     */
    public void showInfo(String message) {
        if (isViewAttached()) {
            getView().showInfo(message);
        }
    }

    /**
     * Handles the error showing a fancy Toasteroid and logging the error
     *
     * @param error the data exception error
     */
    public void handleException(DataError error) {
        hideDialog();
        if (isViewAttached()) {
            showError(error.getMessage());
        }
        Log.e(LOG_TAG, "Exception: " + error.getMessage());
    }

    public void onEventMainThread(DataError exception) {
        handleException(exception);
    }
}
