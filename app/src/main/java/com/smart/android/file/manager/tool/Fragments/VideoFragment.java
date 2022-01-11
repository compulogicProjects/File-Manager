package com.smart.android.file.manager.tool.Fragments;

import static com.smart.android.file.manager.tool.HomeActivity.drawerLayout;
import static com.smart.android.file.manager.tool.ads.AppLovin.load_bannerAd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.Music_Model;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.adapter.Videos_New_Adapter;
import com.smart.android.file.manager.tool.adapter.showVideoAdapter;
import com.smart.android.file.manager.tool.items.VideoItems;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoFragment extends Fragment implements FileSelectedListner {
    List<VideoItems> mlist;
     static List<File> fileList;
    RecyclerView recyclerView;
    List<File> video_list;
    File file,files[];
    //ArrayList<Music_Model> video_list;
    showVideoAdapter showVideoAdapter;
    Videos_New_Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.catogrized_fragment,
                container,false);
        load_bannerAd(getActivity());
        /*   LinearLayout linearLayoutad;
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

            recyclerView = view.findViewById(R.id.recyclterview);
            mlist = new ArrayList<>();
            video_list = new ArrayList();
            //display();
            //displayFiles();
            all_Videos();

        return view;
    }


    private void all_Videos() {
        fileList= new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
        };
        String selection = null;
        @SuppressLint("Recycle")
        Cursor cursor = getActivity().getContentResolver()
                .query(uri, null, selection,
                        null, null);

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

                showVideoAdapter = new showVideoAdapter(fileList, getContext(), this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(showVideoAdapter);
            } else {
                Toast.makeText(getContext(), "File is Null", Toast.LENGTH_SHORT).show();
            }

        }
    }



/*

    private  void all_Videos(){
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION
        };
        String selection = MediaStore.Video.Media.DATA +" !=0";
        @SuppressLint("Recycle")
        Cursor cursor = getActivity().getContentResolver()
                .query(uri, projection, selection,
                null, null);

        while (cursor.moveToNext()){
            Music_Model videoData = new Music_Model(cursor.getString(0),cursor.
                    getString(1), cursor.getString(2),
                    cursor.getString(3));
            String path = cursor.getString(0);
            if (new File(videoData.getPath()).exists())
                video_list.add(videoData);
        }
        if (video_list.size()==0){
            //textView.setVisibility(View.VISIBLE);
        } else {
            showVideoAdapter = new showVideoAdapter(video_list, getContext(), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }
    }
*/


    public ArrayList<File> findFile(File file){
        ArrayList<File> arrayList= new ArrayList<>();
        File[] files= file.listFiles();
        for (File singleFile : files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findFile(singleFile));
            }
            else{
                if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mp4")||
                        singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".3gp") ||
                        singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".mkv") ||
                        singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".webm")||
                        singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".flv")||
                        singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".m4v")){
                    arrayList.add(singleFile);
                }
            }
        }
        /*for (File singleFile: files){
            if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".png")){
                arrayList.add(singleFile);
            }
        }*/
        return arrayList;
    }
    public void displayFiles(){
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        fileList= new ArrayList<>();
        fileList.addAll(findFile(Environment.getExternalStorageDirectory()));
        showVideoAdapter= new showVideoAdapter(fileList,getContext(),this);
        //fileAdapter= new FileAdapter(getContext(),fileList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(showVideoAdapter);
    }


    private void display() {
        int coloumnindexdata,thum;
        String absolutepathimage;
        String[] mProjection = {MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Thumbnails.DATA};
        Cursor cursorImagesExternal = getContext().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mProjection, null, null,
                MediaStore.Images.Media.DATE_TAKEN);
        List<VideoItems> allItems = new ArrayList<VideoItems>();
        coloumnindexdata=cursorImagesExternal.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        thum=cursorImagesExternal.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        while (cursorImagesExternal.moveToNext()) {
            absolutepathimage=cursorImagesExternal.getString(coloumnindexdata);
           VideoItems videoItems=new VideoItems();
           videoItems.setSelected(false);
           videoItems.setStr_path(absolutepathimage);
           videoItems.setStr_thumb(cursorImagesExternal.getString(thum));
           allItems.add(videoItems);
        }

        //Log.v("Image Count: ", "" + countImages);

        List<VideoItems> rowListItem = allItems;
        //lLayout = new GridLayoutManager(MainActivity.this, 4);


        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      //  showVideoAdapter mpAdapter= new showVideoAdapter(rowListItem,getContext(),this);
       // recyclerView.setAdapter(mpAdapter);

    }

    @Override
    public void OnFileClicked(File file) {

    }

    @Override
    public void OnFileLongClicked(File file, int position) {

    }

    @Override
    public void itemclicked(View v, File file, int position) {

        PopupMenu popupMenu= new PopupMenu(getContext(),v);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
                popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.details:
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
                        break;
                    case R.id.delete:
                        AlertDialog.Builder deletedilog= new AlertDialog.Builder(getContext());
                        deletedilog.setTitle("Delete  "+file.getName()+"?");
                        deletedilog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                file.delete();
                                fileList.remove(position);
                                showVideoAdapter.notifyDataSetChanged();
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
                        break;
                    case R.id.share:
                        Uri uri= Uri.parse(file.getAbsolutePath());
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("video/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share videos..."));
                        break;
                    case R.id.rename:
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
                                    showVideoAdapter.notifyItemChanged(position);
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
                        break;
                    default:
                        break;
                }
                return false;

            }
        });

        popupMenu.show();

    }


}
