package com.smart.android.file.manager.tool.ads;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
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
import com.smart.android.file.manager.tool.AddActivity;
import com.smart.android.file.manager.tool.R;
import com.sofit.adshelper.helper.MyMoPub;

import java.util.EnumSet;

public class Max_Ad_Helper {
    public static MaxInterstitialAd interstitialAd;
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd nativeAd;
    public static String max_TAG = "MAX";
    static boolean connected = false;
    public static MaxAdView maxAdView1;




    //................interstitial ad
    //.................................................

    public static void LoadIntersitial_Ad(Activity activity){
        interstitialAd = new MaxInterstitialAd( activity.getString(R.string.max_interstitial), activity );
        interstitialAdd(activity);
    }

    private static void interstitialAdd(Activity activity) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                interstitialAd= new MaxInterstitialAd(activity.getString(R.string.max_interstitial),activity);
                interstitialAd.setListener(new MaxAdViewAdListener() {
                    @Override
                    public void onAdExpanded(MaxAd ad) {


                    }

                    @Override
                    public void onAdCollapsed(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        Log.e(max_TAG, "intersitial ad: loaded success");
                        interstitialAd= (MaxInterstitialAd) ad;
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {
                        Log.e(max_TAG, "intersitial ad: shown");

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {
                        Log.e(max_TAG, "intersitial ad: Clicked");

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                        Log.e(max_TAG, "intersitial ad: loading failed: ");
                        Log.e(max_TAG, "intersitial ad: loading failed: " + error.getMessage());
                        interstitialAd = null;

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                });
                interstitialAd.loadAd();

            }
        }, 1000);

    }

    private static void interstitialAdAfterInternetConnected(Activity activity) {

        interstitialAd = new MaxInterstitialAd(activity.getString(R.string.max_interstitial),activity);
        //if you want to add listener//
        interstitialAd.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {


            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.e(max_TAG, "intersitial ad: loaded success");
                interstitialAd= (MaxInterstitialAd) ad;
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.e(max_TAG, "intersitial ad: shown");

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.e(max_TAG, "intersitial ad: Clicked");

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

                Log.e(max_TAG, "intersitial ad: loading failed: ");
                Log.e(max_TAG, "intersitial ad: loading failed: " + error.getMessage());
                interstitialAd = null;

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        interstitialAd.loadAd();
    }
    public static void showIntersitial(FragmentActivity activity) {
        new MyMoPub().init(activity, activity.getString(R.string.mop_ub_interstitial));
        if (interstitialAd != null && interstitialAd.isReady()) {
            interstitialAd.showAd();
            interstitialAdd(activity);
            Log.e(max_TAG, "interstitial already loaded: ad shown successfully");
        } else if (isOnline(activity)) {
            interstitialAdAfterInternetConnected(activity);
            Log.e(max_TAG, "connected to internet: loading ad");
        } else {
            //getSwitch(activity);
            Log.e(max_TAG, "intersitial: no internet connection or intersitial is null/not ready");
        }
    }

    //........................banner ad
    //.................................................
    public static void loadMediationBannerAd(Activity activity, MaxAdView view) {

        new MyMoPub().init(activity, activity.getString(R.string.mop_ub_banner));

        loadBannerAd(activity, view);
    }

    private static void loadBannerAd(Activity activity, MaxAdView view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  maxAdView1 = view.findViewById(R.id.adview);
                maxAdView1.setListener(new MaxAdViewAdListener() {
                    @Override
                    public void onAdExpanded(MaxAd ad) {
                        Log.e(max_TAG, "banner ad: expanded");
                        maxAdView1 = (MaxAdView) ad;
                    }

                    @Override
                    public void onAdCollapsed(MaxAd ad) {
                        Log.e(max_TAG, "banner ad: Collapsed");
                        maxAdView1 = (MaxAdView) ad;
                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        Log.e(max_TAG, "banner ad: loaded success");
                        maxAdView1 = (MaxAdView) ad;
                        if (maxAdView1 != null){
                            ViewGroup tempVg = (ViewGroup) maxAdView1.getParent();
                            tempVg.removeView(maxAdView1);
                            view.addView(maxAdView1);
                        }
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {
                        Log.e(max_TAG, "banner ad: clicked");
                        maxAdView1 = (MaxAdView) ad;
                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        Log.e(max_TAG, "banner ad: loading failed: ");
                        Log.e(max_TAG, "banner ad: loading failed: " + error.getMessage());
                        Log.e(max_TAG, "banner ad: loading failed: " + error.getCode());
                        maxAdView1 = null;
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                });

                maxAdView1.loadAd();
            }
        }, 1000);

    }


    public static void showBannerAd(Activity activity, MaxAdView layAd) {
        if (maxAdView1 != null) {
            ViewGroup tempVg = (ViewGroup) maxAdView1.getParent();
            tempVg.removeView(maxAdView1);
            layAd.addView(maxAdView1);
            Log.e(max_TAG, "banner already loaded");
        } else if (isOnline(activity)) {
            loadMediationBannerAd(activity, layAd); // here load banner ad again and show
            Log.e(max_TAG, "Banner Ad: connected to internet : loading...");
        } else {
            Log.e(max_TAG, "Banner: no internet connection or Banner is null/not ready");
        }
    }


    //....................



    //.............................native ad
    //.................................................
    public static void loadNativeAd(Activity activity) {
        nativeAdLoader = new MaxNativeAdLoader( activity.getString(R.string.max_native), activity );
      //  nativeAd(activity);
    }


/*
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
*/

/*
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
    }*/
    //.......................................





// check connected to internet or not

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

}
