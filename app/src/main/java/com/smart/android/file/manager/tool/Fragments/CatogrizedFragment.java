package com.smart.android.file.manager.tool.Fragments;

import static com.smart.android.file.manager.tool.HomeActivity.drawerLayout;
import static com.smart.android.file.manager.tool.ads.AppLovin.load_bannerAd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.android.file.manager.tool.FLUtil;
import com.smart.android.file.manager.tool.FileOpner;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.Music_Model;
import com.smart.android.file.manager.tool.adapter.FileAdapter;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.R;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CatogrizedFragment extends Fragment implements FileSelectedListner {
    View view;
    private FileAdapter fileAdapter;
    private RecyclerView recyclerView;
    private List<File> fileList;
    File files[], file;
    File path;
    File storage;
    String data;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //container.removeAllViews();
        view=inflater.inflate(R.layout.catogrized_fragment,container,false);

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

            Bundle bundle = this.getArguments();
            if (bundle.getString("fileType").equals("download")) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                displayFiles();
            }
            else if (bundle.getString("fileType").equals("apk")){
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
                    path = Environment.getExternalStorageDirectory();
                    displayFiles();
                }
                else {
                    path = Environment.getExternalStorageDirectory();
                    all_images();
                }
            }
            else {
                path = Environment.getExternalStorageDirectory();
                all_images();
            }


        //getPermission();
        //getPermission();
        //displayFiles();
        //display();

        return view;
    }

    public ArrayList<File> findFile(File file){
        ArrayList<File> arrayList= new ArrayList<>();
        File[] files= file.listFiles();
        for (File singleFile : files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findFile(singleFile));
            }
            else {
                switch (getArguments().getString("fileType")){
                    case "image":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".png")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".gif")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".bmp")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".webp")){
                            arrayList.add(singleFile);
                        }
                        break;

                    case "video":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mp4")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mp4")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".3gp")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mkv")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".flv")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".m4v")){
                            arrayList.add(singleFile);
                        }
                        break;
                    case "music":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mp3") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".wav")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".oog")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mid")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".m4a")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".amr")){
                            arrayList.add(singleFile);
                        }
                        break;
                    case "download":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".png")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".wav") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pdf") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".apk")){
                            arrayList.add(singleFile);
                        }
                        break;
                    case "docs":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".doc") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".docm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".docx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".dot") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".dotm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".dotx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".ott") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".fodt") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".rtf") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".wps") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xls") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xlsb") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xlsm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xlt") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xlsx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xltx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xltm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".xlw") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".ods") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".ots") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".fods") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".ppt") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pptx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pptm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pps") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".ppsx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".ppsm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pot") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".potx") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".potm") ||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".odp")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".otp")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".fodp")||
                                singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pdf")){
                            arrayList.add(singleFile);
                        }
                        break;
                    case "apk":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".apk")){
                            arrayList.add(singleFile);
                        }
                        break;

                }
            }
        }
        return arrayList;
    }

    public void displayFiles(){
        recyclerView= view.findViewById(R.id.recyclterview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fileList= new ArrayList<>();
        fileList.addAll(findFile(path));
       // Toast.makeText(getContext(), fileList.size()+"", Toast.LENGTH_SHORT).show();
        fileAdapter= new FileAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(fileAdapter);
    }

    private boolean updateFiles(File f) {
        if (f != null && f.exists() && f.listFiles() != null) {
            f.setReadable(true);
            f.setWritable(true);
            file = f;
            files = f.listFiles();
            if (fileAdapter!=null){
                fileAdapter.notifyDataSetChanged();
            }
            return true;
        }
        return false;
    }


    private void all_images() {
        Uri uri = null;
        String toolbarTitle = "";
        String selectionQuery = null;
        String[] selectionArgs = null;
        recyclerView=view.findViewById(R.id.recyclterview);

            /*Uri uri = null;
            String toolbarTitle = "";
            String selectionQuery = null;
            String[] selectionArgs = null;*/
            switch (getArguments().getString("fileType")) {
                case "image": //images
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    toolbarTitle = "Pictures";
                    break;
                case "music": //audio
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    toolbarTitle = "Music";
                    break;
                case "video": //video
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    toolbarTitle = "Videos";
                    break;
                case "docs": //documents
                    uri = MediaStore.Files.getContentUri("external");
                    toolbarTitle = "Documents";
                    List<String> extList = new ArrayList<>(FLUtil.doc_ext);
                    extList.addAll(FLUtil.xl_ext);
                    extList.addAll(FLUtil.ppt_ext);
                    extList.addAll(FLUtil.opendoc_ext);
                    extList.add("pdf");
                    selectionArgs = FLUtil.getMimeTypeQueryArgs(extList);
                    selectionQuery = FLUtil.getMimeTypeQuery(selectionArgs);
                    break;
                /*case "download":
                    displayFiles();
                    break;*/
                case "apk": //apk files
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                        uri = MediaStore.Files.getContentUri("external");
                        toolbarTitle = "Apps";
                        selectionArgs = FLUtil.getMimeTypeQueryArgs(Arrays.asList("apk"));
                        selectionQuery = FLUtil.getMimeTypeQuery(selectionArgs);
                    }
                    break;


            }
            fileList = new ArrayList<>();

            @SuppressLint("Recycle")
            Cursor cursor = getActivity().getContentResolver()
                    .query(uri, null, selectionQuery,
                            selectionArgs, null);

            while (cursor.moveToNext()) {
                Music_Model videoData = new Music_Model(cursor.getString(0), cursor.
                        getString(1), cursor.getString(2),
                        cursor.getString(3));

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String data = cursor.getString(cursor.
                                getColumnIndex(MediaStore.Files.FileColumns.
                                        DATA));
                        File f = new File(data);
                        if (f != null && f.exists()) {
                            fileList.add(f);
                        }
                    }
                }
                //cursor.close();
                files = new File[fileList.size()];
                files = fileList.toArray(files);
                if (files != null) {
                    file = null;
                    fileAdapter = new FileAdapter(getContext(), fileList, this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(fileAdapter);
                } else {
                    Toast.makeText(getContext(), "File is Null", Toast.LENGTH_SHORT).show();
                }

            }
        }




    @Override
    public void OnFileClicked(File file) {
        if (file.isDirectory()){
            Bundle bundle= new Bundle();
            bundle.putString("path",file.getAbsolutePath());
            CatogrizedFragment internallFragment= new CatogrizedFragment();
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
                //share.setType("*/*");
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
    public void rename_file(int position){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.rename);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button rename = dialog.findViewById(R.id.rename);
        String path =fileList.get(position).getPath();
        final File file = new File(path);
        String videoname = file.getName();
        videoname = videoname.substring(0, videoname.lastIndexOf("."));
        EditText editText = (EditText) dialog.findViewById(R.id.edit_txt);
        editText.setTextColor(getResources().getColor(R.color.black));
        editText.setText(videoname);
        editText.requestFocus();

        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                   // Toast_Utills.showToast(VideosNew_Activity.this,
                      //      "Can't rename empty file");
                    return;
                }
                String new_name=editText.getEditableText().toString();
                String v_path = file.getPath();
                String v_ext = file.getAbsolutePath();
                v_ext = v_ext.substring(v_ext.lastIndexOf("."));

                String new_path = file.getParentFile().getPath() + "/" + editText.getText().toString()
                +v_ext;
                File new_file = new File(new_path);

                //Toast.makeText(getContext(), file+"", Toast.LENGTH_SHORT).show();

                File destination = new File(v_path
                        .replace(file.getName(), new_name) + v_ext);
               // Toast.makeText(getContext(), destination+"", Toast.LENGTH_SHORT).show();
               boolean rename = file.renameTo(new_file);
               if (rename){
                    ContentResolver resolver = getActivity().getApplicationContext()
                            .getContentResolver();
                    resolver.delete(MediaStore.Files.getContentUri("external"),
                            MediaStore.MediaColumns.DATA + "=?",new String[]
                                    {file.getPath()});
                    Intent new_intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    new_intent.setData(Uri.fromFile(new_file));
                    getActivity().getApplicationContext().sendBroadcast(new_intent);

                    fileAdapter.notifyItemChanged(position);
                    Toast.makeText(getContext(), "Renamed !", Toast.LENGTH_SHORT).show();
                    ((HomeActivity)getActivity()).reload();

                    //Toast_Utills.showToast(VideosNew_Activity.this,"Video Renamed");
                    //finish();
                   // startActivity(getIntent());overridePendingTransition(0, 0);
                }else {
                    //Toast_Utills.showToast(VideosNew_Activity.this,"Process Failed");
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }





}
