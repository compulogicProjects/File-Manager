package com.smart.android.file.manager.tool;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.android.file.manager.tool.Fragments.CardFragment;
import com.smart.android.file.manager.tool.Fragments.CatogrizedFragment;
import com.smart.android.file.manager.tool.Fragments.DocumentFragment;
import com.smart.android.file.manager.tool.Fragments.HomeFragment;
import com.smart.android.file.manager.tool.Fragments.ImageFragment;
import com.smart.android.file.manager.tool.Fragments.VideoFragment;
import com.google.android.material.navigation.NavigationView;
import com.smart.android.file.manager.tool.ads.AppLovin;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 001;
    public static DrawerLayout drawerLayout;
    public static Toolbar toolbar;
    File files[],file;
    public static ActionBarDrawerToggle toggle;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = HomeActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this,
                R.color.colorPrimaryDark));


        drawerLayout = findViewById(R.id.drawerlayout);
        AppLovin.load_bannerAd(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Open_Drawer, R.string.Close_Drawer);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_contaner, new HomeFragment(), "home")
                .commit();
        navigationView.setCheckedItem(R.id.nav_home);
        toolbar.setTitle("Home");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_contaner, homeFragment, "home")
                        .addToBackStack(null).commit();
                toolbar.setTitle("Home");
                break;
            case R.id.nav_internal:
                constany.constant=4;
                AppLovin.show_interstitial(HomeActivity.this);
                toolbar.setTitle("Internal Storage");

                break;
            case R.id.nav_sdcard:
                boolean sdcard= externalMemoryAvailable(HomeActivity.this);
                if (sdcard) {
                    CardFragment cardFragment = new CardFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_contaner, cardFragment)
                            .addToBackStack(null).commit();
                    HomeActivity.toolbar.setTitle("SD Card");                }
                else{
                    Toast.makeText(HomeActivity.this, "SD Card Not Found", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean externalMemoryAvailable(Activity context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Toast.makeText(this, "permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(HomeActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(HomeActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }


    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }






    @Override
    public void onBackPressed() {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentByTag("home");
        if (homeFragment != null && homeFragment.isVisible()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            toolbar.setTitle("Home");
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View view = LayoutInflater.from(this).inflate(R.layout.backscreen, viewGroup, false);
                builder.setView(view);
                Button btncancel = view.findViewById(R.id.btncancel);
                Button btnexit = view.findViewById(R.id.btnexit);
                RatingBar ratingBar = view.findViewById(R.id.ratingbar);
                AlertDialog alertDialog = builder.create();

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                btnexit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        if (v == 5) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.smart.android.file.manager.tool"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else {
                            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.exitscreen, viewGroup, false);
                            builder.setCancelable(false);
                            builder.setView(view);

                            Button btncancel = view.findViewById(R.id.btncancel);
                            Button btnsubmit = view.findViewById(R.id.btnsubmit);
                            TextView feedbacktext = view.findViewById(R.id.feedbacktext);

                            AlertDialog alertDialog1 = builder.create();

                            btncancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog1.dismiss();
                                }
                            });
                            btnsubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (feedbacktext.getText().toString().isEmpty()) {
                                        Toast.makeText(HomeActivity.this, "Please Enter Feedback!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(HomeActivity.this, "Thanks For Your Feedback!", Toast.LENGTH_SHORT).show();
                                        alertDialog1.dismiss();
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                            alertDialog1.show();
                        }
                    }
                });


                alertDialog.show();

            }
        } else {
            ImageFragment imageFragment = (ImageFragment) getSupportFragmentManager()
                    .findFragmentByTag("image");
            if (imageFragment != null && imageFragment.isVisible()) {
                getFragmentManager().popBackStack();
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toolbar.setTitle("Home");
            }

            VideoFragment myFragment = (VideoFragment) getSupportFragmentManager()
                    .findFragmentByTag("video");
            if (myFragment != null && myFragment.isVisible()) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toolbar.setTitle("Home");
            }

            CatogrizedFragment music = (CatogrizedFragment) getSupportFragmentManager()
                    .findFragmentByTag("music");
            if (music != null && music.isVisible()) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toolbar.setTitle("Home");
            }

            DocumentFragment documentFragment = (DocumentFragment) getSupportFragmentManager()
                    .findFragmentByTag("document");
            if (documentFragment != null && documentFragment.isVisible()) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toolbar.setTitle("Home");
            }

            CatogrizedFragment catogrizedFragment = (CatogrizedFragment) getSupportFragmentManager()
                    .findFragmentByTag("download");
            if (catogrizedFragment != null && catogrizedFragment.isVisible()) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toolbar.setTitle("Home");
            }

            CatogrizedFragment catogrizedFragment1 = (CatogrizedFragment) getSupportFragmentManager()
                    .findFragmentByTag("apk");
            if (catogrizedFragment1 != null && catogrizedFragment1.isVisible()) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toolbar.setTitle("Home");
            }

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            super.onBackPressed();
        }

    }



    public void reload() {
        Bundle args= new Bundle();
        args.putString("fileType","music");
        CatogrizedFragment videoFragment= new CatogrizedFragment();
        videoFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_contaner,
                        videoFragment,"music").addToBackStack(null).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean neverAskAgainSelected(final Activity activity, final String permission) {
        final boolean prevShouldShowStatus = getRatinaleDisplayStatus(activity,permission);
        final boolean currShouldShowStatus = activity.shouldShowRequestPermissionRationale(permission);
        return prevShouldShowStatus != currShouldShowStatus;
    }
    public static boolean getRatinaleDisplayStatus(final Context context, final String permission) {
        SharedPreferences genPrefs =     context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE);
        return genPrefs.getBoolean(permission, false);
    }

}