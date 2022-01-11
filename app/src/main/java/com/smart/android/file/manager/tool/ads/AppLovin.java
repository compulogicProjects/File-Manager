package com.smart.android.file.manager.tool.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.ads.AudienceNetworkAds;
import com.smart.android.file.manager.tool.Fragments.CatogrizedFragment;
import com.smart.android.file.manager.tool.Fragments.ImageFragment;
import com.smart.android.file.manager.tool.Fragments.InternallFragment;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.constany;

public class AppLovin extends AppCompatActivity {


    static MaxInterstitialAd interstitialAd;
   // static MaxNativeAdLoader nativeAdLoader;
    static MaxAd nativeAd;
    static MaxNativeAdView nativeAdView1;
    static String TAG = "appLovin_Ads";
    static boolean connected = false;
    static MaxNativeAdLoader nativeAdLoader;
    static MaxAd             loadedNativeAd;


    public static void initialize_sdk(FragmentActivity activity) {
        AudienceNetworkAds.initialize(activity); //...... facebook sdk
        //......... appp lovin installation
        AppLovinSdk.getInstance(activity).setMediationProvider("max");
        Log.e(TAG, "get sdk initialize");
        AppLovinSdk.initializeSdk(activity, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                Log.e(TAG, "show sdk initialized");
                // AppLovin SDK is initialized, start loading ads
                load_interstitial(activity);
                createNativeAdLoader(activity);
                loadNativeAd(activity);
                //load_Native(activity);
                 //AppLovinSdk.getInstance( activity ).showMediationDebugger();
            }
        });
    }


    public static void load_bannerAd(Activity activity) {
        if (isOnline(activity)) {
            MaxAdView adView;
            adView = activity.findViewById(R.id.bannerId);

            adView.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {

                    Log.e(TAG, "banner load success");
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    Log.e(TAG, "banner load failed: " + error.getMessage());
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                }
            });
            adView.loadAd();
        }

    }


    public static void load_interstitial(FragmentActivity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                interstitialAd = new MaxInterstitialAd
                        (activity.getString(R.string.max_interstitial), activity);
                interstitialAd.setListener(new MaxAdViewAdListener() {
                    @Override
                    public void onAdExpanded(MaxAd ad) {

                    }

                    @Override
                    public void onAdCollapsed(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        getSwitch(activity);
                        Log.e(TAG, "interstitialAd load success");
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {
                    getSwitch(activity);
                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        Log.e(TAG, "Activity Start");
                        getSwitch(activity);
                        //activity.startActivity(new Intent(activity, AudiosNew_Activity.class));

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        Log.e(TAG, "interstitialAd load failed: " + error.getMessage());
                        interstitialAd = null;
                        getSwitch(activity);
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                        Log.e(TAG, "interstitialAd display failed: " + error.getMessage());
                        getSwitch(activity);
                    }
                });
                interstitialAd.loadAd();
            }
        }, 1000);

    }

    public static void load_interstitialAgain(FragmentActivity activity) {

        interstitialAd = new MaxInterstitialAd
                (activity.getString(R.string.max_interstitial), activity);
        interstitialAd.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {

                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                   // getSwitch(activity);
                    Log.e(TAG, "interstitialAd load success");
                } else {
                    getSwitch(activity);
                    //  activity.startActivity(new Intent(activity, AudiosNew_Activity.class));
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                getSwitch(activity);
                //activity.startActivity(new Intent(activity,
                ///      AudiosNew_Activity.class));
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e(TAG, "interstitialAd load failed: " + error.getMessage());
                interstitialAd = null;
                getSwitch(activity);
                //activity.startActivity(new Intent(activity,
                //   AudiosNew_Activity.class));
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                getSwitch(activity);
                Log.e(TAG, "interstitialAd display failed: " + error.getMessage());
            }
        });
        interstitialAd.loadAd();
    }

    public static void show_interstitial(FragmentActivity activity) {
        if (interstitialAd != null && interstitialAd.isReady()) {
            interstitialAd.showAd();
            getSwitch(activity);
        } else {
            if (isOnline(activity)) {
                load_interstitialAgain(activity);
            } else {
                getSwitch(activity);
            }
        }
    }


    public static void load_Native(FragmentActivity activity) {
        //....... initialization
        if (isOnline(activity)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nativeAdLoader = new MaxNativeAdLoader(activity.getString(R.string.max_native), activity);
                    nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                        @Override
                        public void onNativeAdLoaded(MaxNativeAdView nativeAdView, final MaxAd ad) {
                            Log.e(TAG, "native ad loaded success");
                            // Clean up any pre-existing native ad to prevent memory leaks.
                            if (nativeAd != null) {
                                nativeAdLoader.destroy(nativeAd);
                            }
                            // Save ad for cleanup.
                            nativeAd = ad;
                            nativeAdView1 = nativeAdView;
                        }

                        @Override
                        public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                            Log.e(TAG, "native ad loaded fail:" + error.getMessage());
                            // We recommend retrying with exponentially higher delays up to a maximum delay
                        }

                        @Override
                        public void onNativeAdClicked(final MaxAd ad) {
                            // Optional click callback
                        }
                    });
                    nativeAdLoader.loadAd();
                }
            }, 1000);
        }
    }

    public static void load_NativeAdAgain(FragmentActivity activity,
                                          ScrollView f) {
        //....... initialization
        if (isOnline(activity)) {
            // FrameLayout nativeAdContainer = activity.findViewById(R.id.frameLayout);

            nativeAdLoader = new MaxNativeAdLoader(activity.getString(R.string.max_native), activity);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(MaxNativeAdView nativeAdView, final MaxAd ad) {
                    // Clean up any pre-existing native ad to prevent memory leaks.
                    if (nativeAd != null) {
                        nativeAdLoader.destroy(nativeAd);
                    }
                    // Save ad for cleanup.
                    nativeAd = ad;
                    nativeAdView1 = nativeAdView;
                    // Add ad view to view.
                    f.removeAllViews();
                    f.addView(nativeAdView1);
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Log.e(TAG, "Native ad load failed:" + error.getMessage());
                    // We recommend retrying with exponentially higher delays up to a maximum delay
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                    // Optional click callback
                }
            });
            nativeAdLoader.loadAd();
        } else {
            Log.e(TAG, "No Internet Connected");

        }
    }

    public static void show_NativeAd(FragmentActivity activity,
                                     ScrollView f) {
        if (isOnline(activity)) {
            if (nativeAd != null) {
                ScrollView nativeAdContainer = activity.findViewById(R.id.frameLayout);
                //  FrameLayout nativeAdContainer = activity.findViewById(R.id.frameLayout);
// Add          ad view to view.
                // nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(nativeAdView1);
            } else {
                load_NativeAdAgain(activity, f);
            }
        }

    }

    public static void show_NativeAd2(FragmentActivity activity,
                                      ScrollView f) {
        if (nativeAd != null) {
            //ScrollView nativeAdContainer=activity.findViewById(R.id.frameLayout);
            //  FrameLayout nativeAdContainer = activity.findViewById(R.id.frameLayout);
// Add          ad view to view.
            //nativeAdContainer.removeAllViews();
            //nativeAdContainer.addView(nativeAdView1);
            //f.removeAllViewsInLayout();
            f.removeAllViews();
            f.addView(nativeAdView1);
            Log.e(TAG, "Native: ad Shown Already");


        } else {
            if (isOnline(activity)) {
                Log.e(TAG, "Native ad Connected to Internet and Load Again");
                load_NativeAdAgain(activity, f);
            } else {
                Log.e(TAG, "Native:No Internet Connected");
            }
        }

    }

    // switch

    //.............................................................
    private static void ImageFragment(FragmentActivity activity) {
        ImageFragment imageFragment = new ImageFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                imageFragment, "image").addToBackStack(null)
                .commitAllowingStateLoss();
        HomeActivity.toolbar.setTitle("Images");
    }

    private static void MusicFragment(FragmentActivity activity) {
        Bundle args = new Bundle();
        args.putString("fileType", "music");
        CatogrizedFragment catogrizedFragment = new CatogrizedFragment();
        catogrizedFragment.setArguments(args);

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                catogrizedFragment, "music").addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private static void DownlodFragment(FragmentActivity activity) {
        Bundle args = new Bundle();
        args.putString("fileType", "download");
        CatogrizedFragment catogrizedFragment = new CatogrizedFragment();
        catogrizedFragment.setArguments(args);

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                catogrizedFragment, "download").addToBackStack(null)
                .commitAllowingStateLoss();

    }


    private static void InternalFragment(FragmentActivity activity) {
        InternallFragment internallFragment = new InternallFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_contaner, internallFragment)
                .addToBackStack(null).commitAllowingStateLoss();

    }

    private static void getSwitch(FragmentActivity activity) {
        switch (constany.constant) {
            case 1:
                ImageFragment(activity);
                break;

            case 2:
                MusicFragment(activity);
                break;

            case 3:
                DownlodFragment(activity);
                break;
            case 4:
                InternalFragment(activity);
                break;

        }
    }

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


    private static  void createNativeAdLoader(Activity activity)
    {
        nativeAdLoader = new MaxNativeAdLoader( activity.getString(R.string.max_native), activity);
        nativeAdLoader.setRevenueListener(new MaxAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(MaxAd ad) {

            }

        });
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(MaxNativeAdView maxNativeAdView,
                                         MaxAd maxAd) {
                super.onNativeAdLoaded(maxNativeAdView, maxAd);
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( loadedNativeAd != null )
                {
                    nativeAdLoader.destroy( loadedNativeAd );
                }

                // Save ad for cleanup.
                loadedNativeAd = maxAd;
                nativeAdView1=maxNativeAdView;

            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                super.onNativeAdLoadFailed(s, maxError);
                //Toast.makeText(getActivity(), maxError.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.e("applovin_native error:",maxError.getMessage());
                Log.e("applovin_native code:", String.valueOf(maxError.getCode()));

            }

            @Override
            public void onNativeAdClicked(MaxAd maxAd) {
                super.onNativeAdClicked(maxAd);
            }
        });
    }

    private static void loadNativeAd(Activity activity)
    {
        nativeAdLoader.loadAd( createNativeAdView(activity) );
    }

    private static MaxNativeAdView createNativeAdView(Activity activity)
    {
        MaxNativeAdViewBinder binder = new
                MaxNativeAdViewBinder.Builder( R.layout.applovin_native_layout)
                .setTitleTextViewId( R.id.title_text_view )
                .setBodyTextViewId( R.id.body_text_view )
                .setAdvertiserTextViewId( R.id.advertiser_textView )
                .setIconImageViewId( R.id.icon_image_view )
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId( R.id.options_view )
                .setCallToActionButtonId( R.id.cta_button )
                .build();


        return new MaxNativeAdView( binder, activity );
    }
public  static  void show(ViewGroup nativeAdContainerView){
if (loadedNativeAd!=null) {
    nativeAdContainerView.removeAllViews();
    nativeAdContainerView.addView(nativeAdView1);
}
else {
    Log.e(TAG,"Native Null");
}
}


    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAdLoader.destroy(nativeAd);
            super.onDestroy();
        }
    }
}
