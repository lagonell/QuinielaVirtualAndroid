package com.eoldsolutions.quinielavirtualandroid;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.eoldsolutions.quinielavirtualandroid.common.util.Constants;
import com.eoldsolutions.quinielavirtualandroid.data.repository.UserRepository;
import com.eoldsolutions.quinielavirtualandroid.presentation.BuildConfig;
import com.eoldsolutions.quinielavirtualandroid.presentation.R;
import com.eoldsolutions.quinielavirtualandroid.presentation.notification.NotificationManager;
import com.eoldsolutions.quinielavirtualandroid.presentation.util.AnalyticsManager;
import com.eoldsolutions.quinielavirtualandroid.presentation.util.PreferencesConstants;
import com.facebook.FacebookSdk;
import com.marcohc.helperoid.PreferencesHelper;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheContextUtils;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheLogUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Semaphore;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class MainApplication extends MultiDexApplication {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    public final static Semaphore SEMAPHORE_1 = new Semaphore(0);
    private static Boolean isAlreadyInitialized = false;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "uyKsng9bXF3lx2sjQuyZcZpA4";
    private static final String TWITTER_SECRET = "qmt0Gv6Oibbv8xg9FdIwj8vllpc6lQVX6mu1RI4pcevHJjxOaj";
    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDevelopment()) {
            initializeStrictMode();
            initializeLeakCanary();
        }
        //Social Network
        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        printKeyHash();
        // Loading task. Other classes must wait until the app is initialized
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                // Load all data
                Log.d(Constants.LOG_TAG, "1 - MainApplication - Start loading data");
                initializeCrashlytics();
                initializeNotificationManager();
                initializePreferences();
                initializeCalligraphy();
                initializePicasso();
                initializeAnalytics();
                initializeRepositories();
                initializeCache();
                initializeCustomCrash();
                // Notify load finished
                Log.d(Constants.LOG_TAG, "2 - MainApplication - Finish loading data");
                isAlreadyInitialized = true;
                MainApplication.SEMAPHORE_1.release();
                return null;
            }
        };
        task.execute();
    }

    private void initializeCustomCrash() {
        CustomActivityOnCrash.install(this);
    }

    private void initializeCache() {
        if (isDevelopment()) {
            DualCacheLogUtils.enableLog();
        }
        DualCacheContextUtils.setContext(getApplicationContext());
    }

    private void initializeLeakCanary() {
        LeakCanary.install(this);
    }

    private void initializeStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void initializeRepositories() {
        // TODO: Get all repositories by reflection and initialize them in order to connect to the bus
        UserRepository.initialize();
    }

    private void initializeNotificationManager() {
        NotificationManager.initialize(getApplicationContext());
    }

    private void initializeCrashlytics() {
//        Fabric.with(this, new Crashlytics());
    }

    private void initializeAnalytics() {
        AnalyticsManager.initialize(this);
    }

    private void initializePreferences() {
        PreferencesHelper.initialize(this, PreferencesConstants.SHARED_PREFERENCES_NAME);
    }

    private void initializePicasso() {
        Picasso picasso = Picasso.with(getApplicationContext());
        picasso.setIndicatorsEnabled(isDevelopment());
    }

    private void initializeCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    // ************************************************************************************************************************************************************************
    // * Other methods
    // ************************************************************************************************************************************************************************

    public static boolean isDevelopment() {
        return BuildConfig.BUILD_TYPE.equals("debug");
    }

    public static boolean isAcceptance() {
        return BuildConfig.BUILD_TYPE.equals("acceptance");
    }

    /**
     * Used from other
     */
    public static void waitUntilMainApplicationIsInitialized() {
        try {
            if (!isAlreadyInitialized) {
                Log.d(Constants.LOG_TAG, "Waiting for MainApplication to finish loading data...");
                MainApplication.SEMAPHORE_1.acquire();
            } else {
                Log.d(Constants.LOG_TAG, "MainApplication already initialized");
            }
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Call this method inside onCreate once to get your hash key
     */
    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.eoldsolutions.quinielavirtualandroid", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("SHA: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

//    private static long timer;
//    private static int stepNumber;
//
//    public static void startTimer() {
//        Log.d(NavigationHelper.LOG_TAG, String.format("Starting timer..."));
//        timer = System.currentTimeMillis();
//        stepNumber = 0;
//    }
//
//    public static void logTimer() {
//        Long difference = System.currentTimeMillis() - timer;
//        Log.d(NavigationHelper.LOG_TAG, String.format("%d - Time: %d", stepNumber++, difference));
//        timer = System.currentTimeMillis();
//    }
}
