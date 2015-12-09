package com.eoldsolutions.quinielavirtualandroid.presentation.view.impl.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eoldsolutions.quinielavirtualandroid.presentation.R;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.impl.LogInPresenterImpl;
import com.eoldsolutions.quinielavirtualandroid.presentation.presenter.inter.LogInPresenter;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.activity.BaseMvpActivity;
import com.eoldsolutions.quinielavirtualandroid.presentation.view.inter.LogInView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.marcohc.helperoid.ScreenHelper;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.services.AccountService;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.OnClick;

public class LogInActivity extends BaseMvpActivity<LogInView, LogInPresenter> implements LogInView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    // Social Network -- Facebook
    private CallbackManager callbackManager;

    // Social Network -- Google+
    private static final int RC_SIGN_IN = 0;
    /* RequestCode for resolutions to get GET_ACCOUNTS permission on M */
    private static final int RC_PERM_GET_ACCOUNTS = 2;
    private GoogleApiClient mGoogleApiClient;
    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    // View
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.usernameEditText)
    EditText usernameEditText;

    @Bind(R.id.passwordEditText)
    EditText passwordEditText;

    @Bind(R.id.login_button_facebook)
    LoginButton loginButton;

    @Bind(R.id.login_btn_twitter)
    TwitterLoginButton loginButtonTwitter;

    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    @Override
    public LogInPresenter createPresenter() {
        return new LogInPresenterImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initializeActionBar();
        //Social Network
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button_facebook);
        scaleFacebookLogo();
        actionButtonFacebook();
        actionButtonGooglePlus();
        actionButtonTwitter();
    }

    private void initializeActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
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
        menuItem1.setVisible(false);
        menuItem1.setEnabled(false);
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
                onBackPressed();
                break;
            case R.id.item_2:
                presenter.onActionDoneClick();
                break;
            default:
                break;
        }
        return false;
    }

    @OnClick(R.id.forgotTextView)
    protected void onForgotButtonClick() {
        Intent intent = new Intent(LogInActivity.this, ForgotActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }
            mIsResolving = false;
            mGoogleApiClient.connect();
        }
        loginButtonTwitter.onActivityResult(requestCode, resultCode, data);
    }

    // ************************************************************************************************************************************************************************
    // * View interface methods
    // ************************************************************************************************************************************************************************

    @Override
    public String getUsername() {
        return usernameEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void invalidateUsername() {
        YoYo.with(Techniques.Shake).playOn(usernameEditText);
    }

    @Override
    public void invalidatePassword() {
        YoYo.with(Techniques.Shake).playOn(passwordEditText);
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // ************************************************************************************************************************************************************************
    // * UI methods
    // ************************************************************************************************************************************************************************

    private void hideKeyBoard() {
        ScreenHelper.hideKeyboard(this);
    }

    // ************************************************************************************************************************************************************************
    // * Social Newtork methods
    // ************************************************************************************************************************************************************************

    private void scaleFacebookLogo() {
        float fbIconScale = 2.05F;
        Drawable drawable = getResources().getDrawable(
                com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * fbIconScale),
                (int) (drawable.getIntrinsicHeight() * fbIconScale));
        loginButton.setCompoundDrawables(drawable, null, null, null);
        loginButton.setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
        loginButton.setPadding(getResources().getDimensionPixelSize(R.dimen.fb_margin_override_lr),
                getResources().getDimensionPixelSize(R.dimen.fb_margin_override_top), 0, getResources().getDimensionPixelSize(R.dimen.fb_margin_override_bottom));
    }

    private void actionButtonFacebook() {
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("LoginActivity", response.toString());
                                // Get facebook data from login
                                Bundle bFacebookData = getFacebookData(object);
                                //TODO: tengo que ir a la nueva pantalla para que agregue el usuario FACEBOOK.
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

                //String accessToken = loginResult.getAccessToken().getToken();
                // save accessToken to SharedPreference
                //prefUtil.saveAccessToken(accessToken);
            }

            @Override
            public void onCancel() {
                showInfo(getString(R.string.action_cancelled));
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        });
    }

    private void actionButtonGooglePlus() {
        findViewById(R.id.login_btn_google).setOnClickListener(this);
        ((SignInButton) findViewById(R.id.login_btn_google)).setSize(SignInButton.SIZE_ICON_ONLY);
        // [START create_google_api_client]
        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .build();
        // [END create_google_api_client]
        setGooglePlusButtonText(((SignInButton) findViewById(R.id.login_btn_google)), "");
    }

    private void actionButtonTwitter() {
        loginButtonTwitter = (TwitterLoginButton) findViewById(R.id.login_btn_twitter);
        loginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                // with your app's user model

                AccountService ac = Twitter.getApiClient(result.data).getAccountService();
                ac.verifyCredentials(true, true, new Callback<com.twitter.sdk.android.core.models.User>() {
                    @Override
                    public void success(Result<com.twitter.sdk.android.core.models.User> result) {
                        String imageUrl = result.data.profileImageUrl;
                        String email = result.data.email;
                        String userName = result.data.name;
//                        System.out.println(imageUrl);
//                        System.out.println(email);
//                        System.out.println(userName);
                        //TODO: tengo que ir a la nueva pantalla para que agregue el usuario TWITTER.
                    }

                    @Override
                    public void failure(TwitterException e) {

                    }
                });

                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
        //loginButtonTwitter.setText("-");
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {
            String id = object.getString("id");
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        mShouldResolve = false;
        // Show the signed-in UI
        showSignedInUI();
    }

    private void showSignedInUI() {
        updateUI(true);
    }

    private void showSignedOutUI() {
        updateUI(false);
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if (currentPerson != null) {
                // Show signed-in user's name
                String name = currentPerson.getDisplayName();
                //info.setText(getString(R.string.signed_in_fmt, name));

                // Show users' email address (which requires GET_ACCOUNTS permission)
                if (checkAccountsPermission()) {
                    String currentAccount = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    //TODO: tengo que ir a la nueva pantalla para que agregue el usuario GOOGLE+.
                    ((TextView) findViewById(R.id.info)).setText(currentAccount);
                }
            } else {
                // If getCurrentPerson returns null there is generally some error with the
                // configuration of the application (invalid Client ID, Plus API not enabled, etc).
                //info.setText(getString(R.string.signed_in_err));
            }

            // Set button visibility
            findViewById(R.id.login_btn_google).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            // Show signed-out message and clear email field
            // Set button visibility
            findViewById(R.id.login_btn_google).setEnabled(true);
            findViewById(R.id.login_btn_google).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    private boolean checkAccountsPermission() {
        final String perm = Manifest.permission.GET_ACCOUNTS;
        int permissionCheck = ContextCompat.checkSelfPermission(this, perm);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // We have the permission
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            // Need to show permission rationale, display a snackbar and then request
            // the permission again when the snackbar is dismissed.
            Snackbar.make(findViewById(R.id.login_layout),
                    R.string.contacts_permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Request the permission again.
                            ActivityCompat.requestPermissions(LogInActivity.this,
                                    new String[]{perm},
                                    RC_PERM_GET_ACCOUNTS);
                        }
                    }).show();
            return false;
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{perm},
                    RC_PERM_GET_ACCOUNTS);
            return false;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            showSignedOutUI();
        }
    }

    private void showErrorDialog(ConnectionResult connectionResult) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, RC_SIGN_IN,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                mShouldResolve = false;
                                showSignedOutUI();
                            }
                        }).show();
            } else {
                String errorString = apiAvailability.getErrorString(resultCode);
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

                mShouldResolve = false;
                showSignedOutUI();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_google:
                onSignInClicked();
                break;
//            case R.id.sign_out_button:
//                onSignOutClicked();
//                break;
//            case R.id.disconnect_button:
//                onDisconnectClicked();
//                break;
        }
    }

    private void onSignInClicked() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();

        // Show a message to the user that we are signing in.
        //info.setText(R.string.signing_in);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }
}
