package com.smart.android.file.manager.tool.Fragments;

import static com.smart.android.file.manager.tool.HomeActivity.drawerLayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.facebook.ads.NativeAdListener;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.adapter.ImageAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smart.android.file.manager.tool.ads.AppLovin;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link showImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class showImageFragment extends Fragment {
    ViewPager viewPager;
    //private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    private ScrollView        nativeAdContainerView;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd             loadedNativeAd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public showImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment showImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static showImageFragment newInstance(String param1, String param2) {
        showImageFragment fragment = new showImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_image,container,false);
        nativeAdContainerView= view.findViewById(R.id.frameLayout);
        //AppLovin.show_NativeAd2(getActivity(),frameLayout);

        createNativeAdLoader();
        loadNativeAd();




     /*   nativeAdLoader= new MaxNativeAdLoader(getString(R.string.max_native),getActivity());
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                super.onNativeAdLoaded(maxNativeAdView, maxAd);
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = maxAd;

                // Add ad view to view.
                frameLayout.removeAllViews();
                frameLayout.addView( maxNativeAdView );
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
*/

        //AppLovin.show_NativeAd(getActivity());

        /*LinearLayout relativeLayout= view.findViewById(R.id.main_layout);
        Ad_Helper.showNativeAd(getActivity(),relativeLayout);
*/
        HomeActivity.toggle.setDrawerIndicatorEnabled(false);
        // Show back button
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        HomeActivity.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // unlock drawer
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                // home button disable
                ((AppCompatActivity)getActivity()).getSupportActionBar()
                        .setDisplayHomeAsUpEnabled(false);
                //show hamburger
                HomeActivity.toggle.setDrawerIndicatorEnabled(true);
                getActivity().onBackPressed();
            }
        });

        Bundle bundle= this.getArguments();

        int imagepath = bundle.getInt("imagepath",0);
        //String imagepath= newIntent.getStringExtra("imagepath");

        /*for (GridViewItem gridViewItem : List){
            Log.i("Grid Data", gridViewItem.getName());
        }*/
        //Toast.makeText(Image.this, List+"", Toast.LENGTH_SHORT).show();

        String list=bundle.getString("list");
        Gson gson = new Gson();
        Type type = new TypeToken<List<File>>(){}.getType();
        List<File> List = gson.fromJson(list, type);

        viewPager=view.findViewById(R.id.viewpage);
        ImageAdapter imageAdapter= new ImageAdapter(getContext(),List,imagepath);

        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(imagepath);

        return view;
    }
    private void createNativeAdLoader()
    {
        nativeAdLoader = new MaxNativeAdLoader( getString(R.string.max_native), getActivity()
        );
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
                loadedNativeAd = nativeAd;

                nativeAdContainerView.removeAllViews();
                nativeAdContainerView.addView( maxNativeAdView );

            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                super.onNativeAdLoadFailed(s, maxError);
                Toast.makeText(getActivity(), maxError.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.e("applovin_native error:",maxError.getMessage());
                Log.e("applovin_native code:", String.valueOf(maxError.getCode()));

            }

            @Override
            public void onNativeAdClicked(MaxAd maxAd) {
                super.onNativeAdClicked(maxAd);
            }
        });
    }

    private void loadNativeAd()
    {
        nativeAdLoader.loadAd( createNativeAdView() );
    }

    private MaxNativeAdView createNativeAdView()
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


        return new MaxNativeAdView( binder, getActivity() );
    }

}