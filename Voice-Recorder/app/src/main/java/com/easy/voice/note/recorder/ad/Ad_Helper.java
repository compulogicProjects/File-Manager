package com.easy.voice.note.recorder.ad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

import com.easy.voice.note.recorder.R;
import com.easy.voice.note.recorder.SavedRecordingList;
import com.easy.voice.note.recorder.constany;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
import com.mopub.nativeads.FacebookAdRenderer;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;


import java.util.EnumSet;

public class Ad_Helper extends Activity {

    public static MoPubView moPubView1;
    public static String mopub_TAG = "MOP_UB";
    static MoPubNative moPubNative;
    static NativeAd nativeAd1;
    static boolean connected = false;

    static MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    static NativeAd.MoPubNativeEventListener moPubNativeEventListener;

    public static MoPubInterstitial interstitial;


    //................interstitial ad
    //.................................................
    public static void loadIntersitialAd(Activity activity) {
        new MyMoPub().init(activity, activity.getString(R.string.mop_ub_interstitial));
        if (isOnline(activity)){
            interstitialAd(activity);
        }
        else {
            Log.e(mopub_TAG, "no internet , cannot load interstitial ");
        }

    }


    private static void interstitialAd(Activity activity) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                interstitial = new MoPubInterstitial(activity, activity.getString(R.string.mop_ub_interstitial));
                //if you want to add listener//
                interstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
                    @Override
                    public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: loaded success");
                        interstitial = moPubInterstitial;
                    }

                    @Override
                    public void onInterstitialFailed(MoPubInterstitial moPubInterstitial,
                                                     MoPubErrorCode moPubErrorCode) {
                        Log.e(mopub_TAG, "intersitial ad: loading failed: ");
                        Log.e(mopub_TAG, "intersitial ad: loading failed: " + moPubErrorCode.name());
                        interstitial = null;
                    }

                    @Override
                    public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: shown");
                    }

                    @Override
                    public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: clicked");
                    }

                    @Override
                    public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: closed");
                        getSwitch(activity);
                    }
                });
                interstitial.load();
            }
        }, 1000);

    }

    private static void interstitialAdAfterInternetConnected(Activity activity) {

                interstitial = new MoPubInterstitial(activity, activity.getString(R.string.mop_ub_interstitial));
                //if you want to add listener//
                interstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
                    @Override
                    public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: loaded success");
                        interstitial = moPubInterstitial;
                        if (interstitial != null && interstitial.isReady()){
                            interstitial.show();
                        }
                        else {
                            getSwitch(activity);
                        }
                    }

                    @Override
                    public void onInterstitialFailed(MoPubInterstitial moPubInterstitial,
                                                     MoPubErrorCode moPubErrorCode) {
                        Log.e(mopub_TAG, "intersitial ad: loading failed: ");
                        Log.e(mopub_TAG, "intersitial ad: loading failed: " + moPubErrorCode.name());
                        interstitial = null;
                        if (interstitial == null){
                            getSwitch(activity);
                        }
                    }

                    @Override
                    public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: shown");
                    }

                    @Override
                    public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: clicked");
                    }

                    @Override
                    public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {
                        Log.e(mopub_TAG, "intersitial ad: closed");
                        getSwitch(activity);
                    }
                });
                interstitial.load();
    }

    public static void showIntersitial(Activity activity) {
        new MyMoPub().init(activity, activity.getString(R.string.mop_ub_interstitial));
        if (interstitial != null && interstitial.isReady()) {
            interstitial.show();
            interstitialAd(activity);
            Log.e(mopub_TAG, "interstitial already loaded: ad shown successfully");
        } else if (isOnline(activity)) {
            interstitialAdAfterInternetConnected(activity);
            Log.e(mopub_TAG, "connected to internet: loading ad");
        } else {
            getSwitch(activity);
            Log.e(mopub_TAG, "intersitial: no internet connection or intersitial is null/not ready");
        }
    }
    //...................


    //........................banner ad
    //.................................................
    public static void loadMediationBannerAd(Activity activity, LinearLayout view) {

        new MyMoPub().init(activity, activity.getString(R.string.mop_ub_banner));

        if (isOnline(activity)){
            loadBannerAd(activity, view);
        }
        else {
            Log.e(mopub_TAG, "no internet , cannot load interstitial ");
        }
    }

    private static void loadBannerAd(Activity activity, LinearLayout view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moPubView1 = view.findViewById(R.id.adview);
                moPubView1.setAdUnitId(activity.getString(R.string.mop_ub_banner));
                //moPubView1.setAutorefreshEnabled(true);
                moPubView1.setBannerAdListener(new MoPubView.BannerAdListener() {
                    @Override
                    public void onBannerLoaded(@NonNull MoPubView moPubView) {
                        Log.e(mopub_TAG, "banner ad: loaded success");
                        moPubView1 = moPubView;
                        if (moPubView1 != null){
                            ViewGroup tempVg = (ViewGroup) moPubView1.getParent();
                            tempVg.removeView(moPubView1);
                            view.addView(moPubView1);
                        }
                    }

                    @Override
                    public void onBannerFailed(MoPubView moPubView, MoPubErrorCode moPubErrorCode) {
                        Log.e(mopub_TAG, "banner ad: loading failed: ");
                        Log.e(mopub_TAG, "banner ad: loading failed: " + moPubErrorCode.name());
                        Log.e(mopub_TAG, "banner ad: loading failed: " + moPubErrorCode.getIntCode());
                        moPubView1 = null;
                    }

                    @Override
                    public void onBannerClicked(MoPubView moPubView) {
                        Log.e(mopub_TAG, "banner ad: clicked");
                        moPubView1 = moPubView;
                    }

                    @Override
                    public void onBannerExpanded(MoPubView moPubView) {
                        Log.e(mopub_TAG, "banner ad: expanded");
                        moPubView1 = moPubView;
                    }

                    @Override
                    public void onBannerCollapsed(MoPubView moPubView) {
                        Log.e(mopub_TAG, "banner ad: collapsed");
                        moPubView1 = moPubView;
                    }
                });
                moPubView1.loadAd();
            }
        }, 1000);

    }

    public static void showBannerAd(Activity activity, LinearLayout layAd) {
        if (Ad_Helper.moPubView1 != null) {
            ViewGroup tempVg = (ViewGroup) moPubView1.getParent();
            tempVg.removeView(moPubView1);
            layAd.addView(moPubView1);
            Log.e(mopub_TAG, "banner already loaded");
        } else if (isOnline(activity)) {
            loadMediationBannerAd(activity, layAd); // here load banner ad again and show
            Log.e(mopub_TAG, "Banner Ad: connected to internet : loading...");
        } else {
            Log.e(mopub_TAG, "Banner: no internet connection or Banner is null/not ready");
        }
    }
    //....................



    //.............................native ad
    //.................................................

    public static void loadNativeAd(Activity activity) {
        new MyMoPub().init(activity, activity.getString(R.string.mop_ub_native));
        if (isOnline(activity)){
            nativeAd(activity);
        }
        else {
            Log.e(mopub_TAG, "no internet , cannot load interstitial ");
        }
    }
        private static void nativeAd(Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
                    @Override
                    public void onNativeLoad(NativeAd nativeAd) {
                        nativeAd1 = nativeAd;
                        Log.e(mopub_TAG, "Native ad: loaded success");
                        //showNativeAd(activity, view);
                    }

                    @Override
                    public void onNativeFail(NativeErrorCode nativeErrorCode) {
                        Log.e(mopub_TAG, "Native ad: loading failed: " + nativeErrorCode.getIntCode());
                        Log.e(mopub_TAG, "Native ad: loading failed: " + nativeErrorCode);
                        Log.e(mopub_TAG, "Native ad: loading failed: " + nativeErrorCode.name());
                        nativeAd1 = null;
                    }
                    // We will be populating this below
                };

                moPubNativeEventListener = new NativeAd.MoPubNativeEventListener() {
                    @Override
                    public void onImpression(View view) {
                        // Impress is recorded - do what is needed AFTER the ad is visibly shown here
                    }

                    @Override
                    public void onClick(View view) {
                    }
                };

                moPubNative = new MoPubNative(activity, activity.getString(R.string.mop_ub_native),
                        moPubNativeNetworkListener);

                ViewBinder viewBinder = new ViewBinder.Builder(R.layout.mopubnative)
                        .titleId(R.id.mopub_native_ad_title)
                        .textId(R.id.mopub_native_ad_text)
                        .mainImageId(R.id.mopub_native_ad_main_imageview)
                        .iconImageId(R.id.mopub_native_ad_icon)
                        .callToActionId(R.id.mopub_native_ad_cta)
                        .privacyInformationIconImageId(R.id.mopub_native_ad_privacy)
                        .build();

                FacebookAdRenderer facebookAdRenderer = new FacebookAdRenderer(
                        new FacebookAdRenderer.FacebookViewBinder.Builder(R.layout.facebooknative)
                                .titleId(R.id.fb_native_ad_title)
                                .textId(R.id.fb_native_ad_body)
                                .mediaViewId(R.id.fb_native_ad_media)
                                .adIconViewId(R.id.fb_nativeIcon)
                                .adChoicesRelativeLayoutId(R.id.fb_ad_choices_container)
                                .advertiserNameId(R.id.fb_native_ad_title) // Bind either the titleId or advertiserNameId depending on the FB SDK version
                                // End of binding to new layouts
                                .callToActionId(R.id.fb_native_ad_call_to_action)
                                .build());



                moPubNative.registerAdRenderer(facebookAdRenderer);

                MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(viewBinder);
                moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);

                EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                        RequestParameters.NativeAdAsset.TITLE,
                        RequestParameters.NativeAdAsset.TEXT,
                        RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT,
                        RequestParameters.NativeAdAsset.MAIN_IMAGE,
                        RequestParameters.NativeAdAsset.ICON_IMAGE,
                        RequestParameters.NativeAdAsset.STAR_RATING
                );

                RequestParameters mRequestParameters = new RequestParameters.Builder()
                        .desiredAssets(desiredAssets)
                        .build();
                moPubNative.makeRequest(mRequestParameters);
            }
        }, 1000);
    }

    private static void nativeAdAfterInternetConnected(Activity activity , View view){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
                    @Override
                    public void onNativeLoad(NativeAd nativeAd) {
                        nativeAd1 = nativeAd;
                        Log.e(mopub_TAG, "Native ad: loaded success");
                        if (nativeAd1 != null){
                            ScrollView adParent = view.findViewById(R.id.nativeLayout);
                            View adView = nativeAd1.createAdView(activity, adParent);
                            nativeAd1.prepare(adView);
                            nativeAd1.renderAdView(adView);
                            adParent.removeAllViews();
                            adParent.addView(adView);
                        }
                    }

                    @Override
                    public void onNativeFail(NativeErrorCode nativeErrorCode) {
                        Log.e(mopub_TAG, "Native ad: loading failed: " + nativeErrorCode.getIntCode());
                        Log.e(mopub_TAG, "Native ad: loading failed: " + nativeErrorCode);
                        Log.e(mopub_TAG, "Native ad: loading failed: " + nativeErrorCode.name());
                        nativeAd1 = null;
                    }
                    // We will be populating this below
                };

                moPubNativeEventListener = new NativeAd.MoPubNativeEventListener() {
                    @Override
                    public void onImpression(View view) {
                        // Impress is recorded - do what is needed AFTER the ad is visibly shown here
                    }

                    @Override
                    public void onClick(View view) {
                    }
                };

                moPubNative = new MoPubNative(activity, activity.getString(R.string.mop_ub_native),
                        moPubNativeNetworkListener);

                ViewBinder viewBinder = new ViewBinder.Builder(R.layout.mopubnative)
                        .titleId(R.id.mopub_native_ad_title)
                        .textId(R.id.mopub_native_ad_text)
                        .mainImageId(R.id.mopub_native_ad_main_imageview)
                        .iconImageId(R.id.mopub_native_ad_icon)
                        .callToActionId(R.id.mopub_native_ad_cta)
                        .privacyInformationIconImageId(R.id.mopub_native_ad_privacy)
                        .build();

                FacebookAdRenderer facebookAdRenderer = new FacebookAdRenderer(
                        new FacebookAdRenderer.FacebookViewBinder.Builder(R.layout.facebooknative)
                                .titleId(R.id.fb_native_ad_title)
                                .textId(R.id.fb_native_ad_body)
                                .mediaViewId(R.id.fb_native_ad_media)
                                .adIconViewId(R.id.fb_nativeIcon)
                                .adChoicesRelativeLayoutId(R.id.fb_ad_choices_container)
                                .advertiserNameId(R.id.fb_native_ad_title) // Bind either the titleId or advertiserNameId depending on the FB SDK version
                                // End of binding to new layouts
                                .callToActionId(R.id.fb_native_ad_call_to_action)
                                .build());


                moPubNative.registerAdRenderer(facebookAdRenderer);

                MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(viewBinder);
                moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);

                EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                        RequestParameters.NativeAdAsset.TITLE,
                        RequestParameters.NativeAdAsset.TEXT,
                        RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT,
                        RequestParameters.NativeAdAsset.MAIN_IMAGE,
                        RequestParameters.NativeAdAsset.ICON_IMAGE,
                        RequestParameters.NativeAdAsset.STAR_RATING
                );

                RequestParameters mRequestParameters = new RequestParameters.Builder()
                        .desiredAssets(desiredAssets)
                        .build();
                moPubNative.makeRequest(mRequestParameters);
            }
        }, 1000);
    }


    public static void showNativeAd(Activity activity, View view) {

        if (nativeAd1 != null) {
            ScrollView adParent = view.findViewById(R.id.nativeLayout);
            View adView = nativeAd1.createAdView(activity, adParent);
            nativeAd1.prepare(adView);
            nativeAd1.renderAdView(adView);
            adParent.removeAllViews();
            adParent.addView(adView);
            Log.e(mopub_TAG, "native already loaded");
        } else if (isOnline(activity)) {
            nativeAdAfterInternetConnected(activity, view); //.... load native ad again and show
            Log.e(mopub_TAG, "Native: connected to internet,, loading native");
        } else {
            Log.e(mopub_TAG, "Native: no internet connection or Native is null/not ready");
        }
    }
    //.......................................




    //....... create method to check either mobile network (Sim data) or wifi is connected or not
    public static boolean isOnline(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }



    //.............................................................
    private static void SavedIntent(Activity activity){
        Intent intent= new Intent(activity, SavedRecordingList.class);
        activity.startActivity(intent);
    }

    /*private static void competitionIntent(Activity activity){
        Intent intent= new Intent(activity, Completemunu.class);
        activity.startActivity(intent);
    }

    private static void countingintent(Activity activity){
        Intent intent= new Intent(activity, CountingActivity.class);
        activity.startActivity(intent);
    }

    private static void nextintent(Activity activity){
        Intent intent1= activity.getIntent();
        int activity1=intent1.getIntExtra("Activity",0);
        if (activity1 == 1) {
            Intent intent = new Intent(activity, Addition.class);
            activity.startActivity(intent);
            activity.finish();
        }
        if (activity1 == 2) {
            Intent intent = new Intent(activity, Subtraction.class);
            activity.startActivity(intent);
            activity.finish();
        }
        if (activity1 == 3) {
            Intent intent = new Intent(activity, Division.class);
            activity.startActivity(intent);
            activity.finish();
        }
        if (activity1 == 4) {
            Intent intent = new Intent(activity, Multiplication.class);
            activity.startActivity(intent);
            activity.finish();
        }
        if (activity1 == 5) {
            Intent intent = new Intent(activity, Competition.class);
            Intent intent2= activity.getIntent();
            int complete=intent2.getIntExtra("complete",0);
            if (complete==1){
                intent.putExtra("compete",1);
            }
            if (complete==2){
                intent.putExtra("compete",2);
            }
            if (complete==3){
                intent.putExtra("compete",3);
            }
            if (complete==4){
                intent.putExtra("compete",4);
            }

            activity.startActivity(intent);
        }

    }

*/

    private static void getSwitch(Activity activity){
        switch (constany.constant){
            case 1:
                SavedIntent(activity);
                break;

            /*case 2:
                competitionIntent(activity);
                break;

            case 3:
                countingintent(activity);
                break;

            case 4:
                nextintent(activity);
                break;
*/
        }
    }


    //.................................................
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitial != null) {
            interstitial.destroy();
        }
        if (moPubView1 != null) {
            moPubView1.destroy();
        }

        nativeAd1.destroy();
        moPubNative.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (moPubView1 != null) {
            moPubView1.pauseAutoRefresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (moPubView1 != null) {
            moPubView1.resumeAutoRefresh();
        }
    }
}
