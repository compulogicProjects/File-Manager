package com.smart.android.file.manager.tool.Fragments;

import static com.smart.android.file.manager.tool.HomeActivity.drawerLayout;
import static com.smart.android.file.manager.tool.ads.AppLovin.load_bannerAd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.adapter.FragmentAdapter;
import com.smart.android.file.manager.tool.items.GridViewItem;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.List;

public class DocumentFragment extends Fragment {
    List<GridViewItem> mlist;
    RecyclerView recyclerView;
    List<File> file;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable android.os.Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.document_layout
                ,container,false);

        load_bannerAd(getActivity());

        /*

    LinearLayout linearLayoutad;
            linearLayoutad = view.findViewById(R.id.layad);
            Ad_Helper.loadMediationBannerAd(getActivity(), linearLayoutad);
*/

            //You may not want to open the drawer on swipe from the left in this case
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            HomeActivity.toggle.setDrawerIndicatorEnabled(false);
            // Show back button
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            HomeActivity.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // unlock drawer
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    // home button disable
                    ((AppCompatActivity) getActivity()).getSupportActionBar()
                            .setDisplayHomeAsUpEnabled(false);
                    //show hamburger
                    HomeActivity.toggle.setDrawerIndicatorEnabled(true);
                    getActivity().onBackPressed();
                }
            });


            tabLayout = view.findViewById(R.id.tab_layout);
            viewPager2 = view.findViewById(R.id.viewpager2);

            FragmentManager fm = getParentFragmentManager();
            adapter = new FragmentAdapter(fm, getLifecycle());
            viewPager2.setAdapter(adapter);


            tabLayout.addTab(tabLayout.newTab().setText("All"));
            tabLayout.addTab(tabLayout.newTab().setText("DOC"));
            tabLayout.addTab(tabLayout.newTab().setText("XLS"));
            tabLayout.addTab(tabLayout.newTab().setText("PPT"));
            tabLayout.addTab(tabLayout.newTab().setText("PDF"));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    tabLayout.selectTab(tabLayout.getTabAt(position));
                }
            });
        /*recyclerView= view.findViewById(R.id.recyclterview);
        mlist= new ArrayList<>();
        display();*/



        return view;

    }

}
