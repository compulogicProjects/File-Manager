package com.smart.android.file.manager.tool.Fragments;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.android.file.manager.tool.FileOpner;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.Permission_Class;
import com.smart.android.file.manager.tool.adapter.FileAdapter;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.ads.AppLovin;
import com.smart.android.file.manager.tool.constany;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements FileSelectedListner {
    View view;
    private FileAdapter fileAdapter;
    private RecyclerView recyclerView;
    private List<File> fileList;
    LinearLayout imagelinear,videolinear,musiclinear,doclinear,downloadlinear,apklinear;
    ProgressBar progressBarstorage,progressBarcard;
    TextView storagetext,cardtext;
    CardView sdcard,storage;
    static String secStorage;
    String lastValue;
    boolean read,write,internet,networkstate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //container.removeAllViews();
        view=inflater.inflate(R.layout.home_fragment,container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getPermission();
        }

        read= hasPermission(READ_EXTERNAL_STORAGE);
        write= hasPermission(WRITE_EXTERNAL_STORAGE);
        internet= hasPermission(READ_EXTERNAL_STORAGE);
        networkstate= hasPermission(READ_EXTERNAL_STORAGE);
/*
        LinearLayout linearLayoutad;
        linearLayoutad=view.findViewById(R.id.layad);
        Ad_Helper.loadMediationBannerAd(getActivity(),linearLayoutad);
        */


/*
        getPermission();

        if(HomeActivity.permission){
            //displayFiles();
        }
        else {
            getPermission();
        }*/

        imagelinear= view.findViewById(R.id.imagelinear);
        videolinear= view.findViewById(R.id.videolinear);
        musiclinear= view.findViewById(R.id.musiclinear);
        doclinear= view.findViewById(R.id.doclinear);
        downloadlinear= view.findViewById(R.id.downloadlinear);
        apklinear= view.findViewById(R.id.apklinear);

        progressBarstorage=view.findViewById(R.id.storage_bar);
        progressBarcard=view.findViewById(R.id.storage_barcard);
        storagetext=view.findViewById(R.id.phonestorage);
        cardtext=view.findViewById(R.id.cardstorage);
        sdcard=view.findViewById(R.id.cardsdcard);
        storage=view.findViewById(R.id.phonecard);

        imagelinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

               /* Intent intent= new Intent(getActivity().getApplication(), ImageActivity.class);
                startActivity(intent);*/
                if (read && write && internet && networkstate) {
                    constany.constant = 1;
                   // Ad_Helper.showIntersitial(getActivity());
                    AppLovin.show_interstitial(getActivity());
                }
                else {
                    getPermission();
                }


                /*Bundle args= new Bundle();
                args.putString("fileType","image");
                CatogrizedFragment catogrizedFragment= new CatogrizedFragment();
                catogrizedFragment.setArguments(args);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_contaner,
                        catogrizedFragment).addToBackStack(null).commit();*/
            }
        });
        videolinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (read && write && internet && networkstate) {
                    VideoFragment videoFragment= new VideoFragment();
                    getParentFragmentManager().beginTransaction().
                            replace(R.id.fragment_contaner,
                                    videoFragment,"video").addToBackStack(null).commit();
                    HomeActivity.toolbar.setTitle("Videos");

                }
                else {
                        getPermission();
                }

                /*Bundle args= new Bundle();
                args.putString("fileType","video");
                CatogrizedFragment catogrizedFragment= new CatogrizedFragment();
                catogrizedFragment.setArguments(args);

                getFragmentManager().beginTransaction().add(R.id.fragment_contaner,
                        catogrizedFragment).addToBackStack(null).commit();*/
            }
        });
        musiclinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
                if (read && write && internet && networkstate) {
                    constany.constant=2;
                    //Ad_Helper.showIntersitial(getActivity());
                    AppLovin.show_interstitial(getActivity());
                    HomeActivity.toolbar.setTitle("Music");
                }
                else {
                    getPermission();
                }
              /*  MusicFragment musicFragment= new MusicFragment();
                getParentFragmentManager().beginTransaction().add(R.id.fragment_contaner,
                        musicFragment).addToBackStack(null).commit();*/


            }
        });
        doclinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (read && write && internet && networkstate) {
                    DocumentFragment docFragment= new DocumentFragment();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                            docFragment,"document").addToBackStack(null).commit();
                    HomeActivity.toolbar.setTitle("Documents");


                }
                else {
                    getPermission();                }
              /*  Bundle args= new Bundle();
                args.putString("fileType","docs");
                CatogrizedFragment catogrizedFragment= new CatogrizedFragment();
                catogrizedFragment.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                        catogrizedFragment).addToBackStack(null).commit();*/
            }
        });
        downloadlinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (read && write && internet && networkstate) {
                    constany.constant=3;
                    AppLovin.show_interstitial(getActivity());
                    //Ad_Helper.showIntersitial(getActivity());
                    HomeActivity.toolbar.setTitle("Downloads");

                }
                else {
                    getPermission();                }
            }
        });
        apklinear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (read && write && internet && networkstate) {

                    Bundle args= new Bundle();
                    args.putString("fileType","apk");
                    CatogrizedFragment catogrizedFragment= new CatogrizedFragment();
                    catogrizedFragment.setArguments(args);
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                            catogrizedFragment,"apk").addToBackStack(null).commit();
                    HomeActivity.toolbar.setTitle("APKs");

                }
                else {
                    getPermission();                }
            }
        });

        storage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {

                if (read && write && internet && networkstate) {
                    constany.constant=4;
                    AppLovin.show_interstitial(getActivity());
                    //Ad_Helper.showIntersitial(getActivity());
                    HomeActivity.toolbar.setTitle("Internal Storage");

                }
                else {
                    getPermission();                }
            }
        });

        sdcard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {

                if (read && write && internet && networkstate) {
                    boolean sdcard= externalMemoryAvailable(getActivity());
                    if (sdcard) {
                        CardFragment cardFragment = new CardFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_contaner, cardFragment)
                                .addToBackStack(null).commit();
                        HomeActivity.toolbar.setTitle("SD Card");

                    }
                    else{
                        Toast.makeText(getContext(), "SD Card Not Found", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    getPermission();                }
            }
        });
        boolean sdcard= externalMemoryAvailable(getActivity());
        if (sdcard) {
            sdcardcard();
        }

            storagecard();

        // check sd card is inserted or not
        // displayFiles();
        return view;
    }

    public static boolean externalMemoryAvailable(Activity context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;
    }



    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return displaySize(availableBlocks * blockSize);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static String getExternalSdCardSize() {
        File storage = new File("/storage");
        String external_storage_path = "";
        String size = "";

        if (storage.exists()) {
            File[] files = storage.listFiles();

            for (File file : files) {
                if (file.exists()) {
                    try {
                        if (Environment.isExternalStorageRemovable(file)) {
                            // storage is removable
                            external_storage_path = file.getAbsolutePath();
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        if (!external_storage_path.isEmpty()) {
            File external_storage = new File(external_storage_path);
            if (external_storage.exists()) {
                size = totalSize(external_storage);
            }
        }
        return size;
    }

    private static String totalSize(File file) {
        StatFs stat = new StatFs(file.getPath());
        long blockSize, totalBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }

        return displaySize(totalBlocks * blockSize);
    }


    public void sdcardcard(){
        File[] externalcachedir= getContext().getExternalCacheDirs();
        for (File file:externalcachedir){
            if (Environment.isExternalStorageRemovable(file)){
                secStorage= file.getPath().split("/Android")[0];
                break;
            }
        }

        File storage= new File(secStorage);

        String sdcard = System.getenv("EXTERNAL_STORAGE");
        File file= new File("/storage");
        Long totalMemory = getTotalRAM(storage);
        Long freeMemory= getAvailableMemoryInBytes(storage);

        float usedMemory = totalMemory - freeMemory;
        int percent = (int) ((usedMemory / totalMemory) * 100);
        progressBarcard.setProgress(percent);
        progressBarcard.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        cardtext.setText(displaySize(freeMemory)+" free out of "+displaySize(totalMemory));

    }




    public void storagecard(){
        Long totalMemory = getTotalRAM(Environment.getExternalStorageDirectory());
        Long freeMemory= getAvailableMemoryInBytes(Environment.getExternalStorageDirectory());

        float usedMemory = totalMemory - freeMemory;
        int percent = (int) ((usedMemory / totalMemory) * 100);
        progressBarstorage.setProgress(percent);
        progressBarstorage.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        storagetext.setText(displaySize(freeMemory)+" free out of "+displaySize(totalMemory));

    }

    public long getAvailableMemoryInBytes(File filePath) {
        StatFs stat = new StatFs(filePath.getPath());
        return stat.getBlockSize() * (long) stat.getAvailableBlocks();
    }

    public static String displaySize(long bytes) {
        if (bytes > 1073741824) return String.format("%.02f", (float) bytes / 1073741824) + " GB";
        else if (bytes > 1048576) return String.format("%.02f", (float) bytes / 1048576) + " MB";
        else if (bytes > 1024) return String.format("%.02f", (float) bytes / 1024) + " KB";
        else return bytes + " B";
    }

    public long getTotalRAM(File filePath) {
        StatFs stat = new StatFs(filePath.getPath());
        return stat.getBlockSize() * (long) stat.getBlockCount();
    }


    static String getSDCard() {
        String sdcard = System.getenv("SECONDARY_STORAGE");
        if ((sdcard == null) || (sdcard.length() == 0)) {
            sdcard = System.getenv("EXTERNAL_SDCARD_STORAGE");
        }
        return sdcard;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void displayFiles(){
        //recyclerView= view.findViewById(R.id.recyclerrecents);
        recyclerView.setHasFixedSize(true);
        fileList= new ArrayList<>();
        fileList.addAll(findFile(Environment.getExternalStorageDirectory()));
        fileAdapter= new FileAdapter(getActivity(),fileList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fileAdapter);

    }
    public ArrayList<File> findFile(File file){
        ArrayList<File> arrayList= new ArrayList<>();
        File[] files= file.listFiles();
        for (File singleFile : files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.add(singleFile);
            }
        }
        for (File singleFile: files){
            if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".png") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".wav") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mp3") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mp4") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pdf") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".doc") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".apk")){
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }

    public static void shrinkTo(List list, int newSize) {
        int size = list.size();
        if (newSize >= size) return;
        for (int i = newSize; i < size; i++) {
            list.remove(list.size() - 1);
        }
    }


    @Override
    public void OnFileClicked(File file) {
        if (file.isDirectory()){
            Bundle bundle= new Bundle();
            bundle.putString("path",file.getAbsolutePath());
            InternallFragment internallFragment= new InternallFragment();
            internallFragment.setArguments(bundle);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_contaner,
                    internallFragment).addToBackStack(null).commit();
        }
        else {
            try {
                FileOpner.openfile(getContext(),file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnFileLongClicked(File file,int position) {
        LayoutInflater inflater= getLayoutInflater();
        View view= inflater.inflate(R.layout.optiondialog,null);
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setCancelable(true);

        LinearLayout details_linear= view.findViewById(R.id.details);
        LinearLayout rename_linear= view.findViewById(R.id.rename);
        LinearLayout delete_linear= view.findViewById(R.id.delete);
        LinearLayout share_linear= view.findViewById(R.id.share);

        details_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder detailsDialog= new AlertDialog.Builder(getContext());
                detailsDialog.setTitle("Details");
                final TextView details= new TextView(getContext());
                details.setPadding(30,10,10,10);
                detailsDialog.setView(details);
                Date lastmodified= new Date(file.lastModified());
                SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formatdate= formatter.format(lastmodified);
                details.setText("File Name: "+file.getName()+"\n"+
                        "Size: "+ Formatter.formatShortFileSize(getContext(),file.length())+"\n"+
                        "Path:"+file.getAbsolutePath()+"\n"+
                        "Last Modified: "+formatdate);
                detailsDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        detailsDialog.setCancelable(true);
                    }
                });
                AlertDialog dilog= detailsDialog.create();
                dilog.show();
            }
        });

        rename_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder rename_builder= new AlertDialog.Builder(getContext());
                rename_builder.setTitle("Rename File");
                final EditText name= new EditText(getContext());
                rename_builder.setView(name);

                rename_builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String new_name= name.getEditableText().toString();
                        String extention= file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
                        File current= new File(file.getAbsolutePath());
                        File destination= new File(file.getAbsolutePath().replace(file.getName(),new_name)+extention);

                        if (current.renameTo(destination)){
                            fileList.set(position,destination);
                            fileAdapter.notifyItemChanged(position);
                            Toast.makeText(getContext(),"Renamed!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"Failed to renamed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                rename_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog rename= rename_builder.create();
                rename.show();
            }
        });

        delete_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deletedilog= new AlertDialog.Builder(getContext());
                deletedilog.setTitle("Delete"+file.getName()+"?");
                deletedilog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        file.delete();
                        fileList.remove(position);
                        fileAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Deleted !", Toast.LENGTH_SHORT).show();
                    }
                });

                deletedilog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog= deletedilog.create();
                dialog.show();
            }
        });

        share_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharefile= file.getName();
                Intent share= new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("Image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(Intent.createChooser(share,"share"+sharefile
                ));
            }
        });

        builder.show();
        /*final Dialog dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.optiondialog);
        dialog.setTitle("Select");
        dialog.show();*/

    }

    @Override
    public void itemclicked(View v, File file, int position) {

        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
                popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.details:
                        AlertDialog.Builder detailsDialog = new AlertDialog.Builder(getContext());
                        detailsDialog.setTitle("Details");
                        final TextView details = new TextView(getContext());
                        details.setPadding(30, 10, 10, 10);
                        detailsDialog.setView(details);
                        Date lastmodified = new Date(file.lastModified());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String formatdate = formatter.format(lastmodified);
                        details.setText("File Name: " + file.getName() + "\n" +
                                "Size: " + Formatter.formatShortFileSize(getContext(), file.length()) + "\n" +
                                "Path:" + file.getAbsolutePath() + "\n" +
                                "Last Modified: " + formatdate);
                        detailsDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                detailsDialog.setCancelable(true);
                            }
                        });
                        AlertDialog dilog = detailsDialog.create();
                        dilog.show();
                        break;
                    case R.id.delete:
                        AlertDialog.Builder deletedilog = new AlertDialog.Builder(getContext());
                        deletedilog.setTitle("Delete  " + file.getName() + "?");
                        deletedilog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(getContext(), file+"", Toast.LENGTH_SHORT).show();
                                file.delete();
                                fileList.remove(position);
                                fileAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Deleted !", Toast.LENGTH_SHORT).show();
                            }
                        });

                        deletedilog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog dialog = deletedilog.create();
                        dialog.show();
                        break;
                    case R.id.share:
                        Uri uri = Uri.parse(file.getAbsolutePath());
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share images..."));
                        break;
                        /*AlertDialog.Builder rename_builder = new AlertDialog.Builder(getContext());
                        rename_builder.setTitle("Rename File");
                        final EditText name = new EditText(getContext());
                        rename_builder.setView(name);

                        rename_builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String new_name = name.getEditableText().toString();
                                String extention = file.getAbsolutePath().substring(file.getAbsolutePath().
                                        lastIndexOf("."));
                                File current = new File(file.getAbsolutePath());
                                File destination = new File(file.getAbsolutePath()
                                        .replace(file.getName(), new_name) + extention);
                                if (current.renameTo(destination)) {
                                    fileList.set(position, destination);
                                    fileAdapter.notifyItemChanged(position);
                                    Toast.makeText(getContext(), "Renamed!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to renamed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        rename_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog rename = rename_builder.create();
                        rename.show();*/

                    default:
                        break;
                }
                return false;

            }
        });

        popupMenu.show();

    }

    public boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }// must be granted after installed.
        return ActivityCompat.checkSelfPermission(getContext(),permission) == PackageManager.PERMISSION_GRANTED;
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    public void getPermission() {
        Dexter.withContext(getContext())
                .withPermissions(READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        if(multiplePermissionsReport.areAllPermissionsGranted()){
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_contaner, new HomeFragment(), "home")
                                    .commit();
                        }

                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
                            Permission_Class.display_dialog(getActivity());
                        }
                        //displayFiles();
                        //permission = true;
                    }



                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


}
