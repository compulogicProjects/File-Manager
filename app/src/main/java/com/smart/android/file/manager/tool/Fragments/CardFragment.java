package com.smart.android.file.manager.tool.Fragments;

import static com.smart.android.file.manager.tool.HomeActivity.drawerLayout;
import static com.smart.android.file.manager.tool.ads.AppLovin.load_bannerAd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.android.file.manager.tool.FileOpner;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.adapter.FileAdapter;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.R;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardFragment extends Fragment implements FileSelectedListner {
    View view;
    private FileAdapter fileAdapter;
    private RecyclerView recyclerView;
    private List<File> fileList;
    private ImageView img_back;
    File storage;
    String data;
    boolean permission=false;
    String secStorage;
    TextView empty;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.card_fragment,container,false);
        empty=view.findViewById(R.id.empty);

        load_bannerAd(getActivity());


        /*

        LinearLayout linearLayoutad;
        linearLayoutad=view.findViewById(R.id.layad);
        Ad_Helper.loadMediationBannerAd(getActivity(),linearLayoutad);
*/


        //You may not want to open the drawer on swipe from the left in this case
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // Remove hamburger
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





        File[] externalcachedir= getContext().getExternalCacheDirs();
        for (File file:externalcachedir){
            if (Environment.isExternalStorageRemovable(file)){
                secStorage= file.getPath().split("/Android")[0];
                break;
            }
        }


        storage= new File(secStorage);

        try {
            data=getArguments().getString("path");
            File file= new File(data);
            storage=file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //getPermission();
        displayFiles();

        int count=fileAdapter.getItemCount();
        if (count==0){
            empty.setVisibility(View.VISIBLE);
        }
        return view;
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
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".doc") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pdf") ||
                    singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".apk")){
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }
    public void displayFiles(){
        recyclerView= view.findViewById(R.id.recyclterview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fileList= new ArrayList<>();
        fileList.addAll(findFile(storage));
        fileAdapter= new FileAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    public void OnFileClicked(File file) {
        if (file.isDirectory()){
            Bundle bundle= new Bundle();
            bundle.putString("path",file.getAbsolutePath());
            CardFragment internallFragment= new CardFragment();
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


}
