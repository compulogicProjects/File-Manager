package com.smart.android.file.manager.tool;

import static com.smart.android.file.manager.tool.ads.AppLovin.initialize_sdk;
import static com.smart.android.file.manager.tool.ads.AppLovin.load_bannerAd;
import static com.smart.android.file.manager.tool.ads.AppLovin.show_NativeAd;
import static com.smart.android.file.manager.tool.ads.AppLovin.show_interstitial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.ads.AudienceNetworkAds;

public class AddActivity extends AppCompatActivity{
    Button btn;
    //....     activity class code
    MaxInterstitialAd interstitialAd;
    //  objects
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // Intiilize sdk
        initialize_sdk(AddActivity.this);
        // Load Banner
        load_bannerAd(AddActivity.this);
        // Native Ad Load
        //show_NativeAd(AddActivity.this);
        // show Intersitial Add

        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_interstitial(AddActivity.this);
            }
        });


    }

}
/*
        AudienceNetworkAds.initialize(this); //...... facebook sdk
        //......... appp lovin installation
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
                MaxAdView adView;
                adView = findViewById(R.id.bannerId);
                // set listner
                adView.setListener( AddActivity.this );
                // Load the ad
                adView.loadAd();


                //... initialize ad
                interstitialAd = new MaxInterstitialAd( "2ca42671acae7d51", AddActivity.this );
                interstitialAd.setListener( AddActivity.this );

                // Load the first ad
                interstitialAd.loadAd();


                FrameLayout nativeAdContainer = findViewById( R.id.nativeLayout );

                nativeAdLoader = new MaxNativeAdLoader( "85537f42b53b0b0b", AddActivity.this );

                nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                    @Override
                    public void onNativeAdLoaded(MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                        // Clean up any pre-existing native ad to prevent memory leaks.
                        if ( nativeAd != null )
                        {
                            nativeAdLoader.destroy( nativeAd );
                        }

                        // Save ad for cleanup.
                        nativeAd = maxAd;

                        // Add ad view to view.
                        nativeAdContainer.removeAllViews();
                        nativeAdContainer.addView( maxNativeAdView );
                    }

                    @Override
                    public void onNativeAdLoadFailed(String s, MaxError maxError) {
                        super.onNativeAdLoadFailed(s, maxError);
                    }

                    @Override
                    public void onNativeAdClicked(MaxAd maxAd) {
                        super.onNativeAdClicked(maxAd);
                    }
                });

                nativeAdLoader.loadAd();
            }
        } );

        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isReady()){
                    interstitialAd.showAd();

                }
		            else {
                    interstitialAd = new MaxInterstitialAd( "2ca42671acae7d51", AddActivity.this );
                    interstitialAd.setListener( AddActivity.this );

                    // Load ad again
                    interstitialAd.loadAd();
                }

            }
        });


    }

    @Override
    public void onAdExpanded(MaxAd ad) {

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {

    }

    @Override
    public void onAdLoaded(MaxAd ad) {

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

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }*/
