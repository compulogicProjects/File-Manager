package com.smart.android.file.manager.tool.Fragments;

import static com.smart.android.file.manager.tool.HomeActivity.drawerLayout;
import static com.smart.android.file.manager.tool.ads.AppLovin.load_bannerAd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.HomeActivity;
import com.smart.android.file.manager.tool.IonBackPressed;
import com.smart.android.file.manager.tool.Music_Model;
import com.smart.android.file.manager.tool.adapter.Videos_New_Adapter;
import com.smart.android.file.manager.tool.adapter.MyAdapeter;
import com.smart.android.file.manager.tool.R;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImageFragment extends Fragment implements FileSelectedListner,IonBackPressed {
    RecyclerView recyclerView;
    private List<File> fileList;
    MyAdapeter myAdapeter;
    ArrayList<Music_Model> video_list;
    com.smart.android.file.manager.tool.adapter.showVideoAdapter showVideoAdapter;
    Videos_New_Adapter adapter;
    File files[], file;
    private
    static ViewHolder holder;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.catogrized_fragment, container,
                false);
        load_bannerAd(getActivity());

        /* LinearLayout linearLayoutad;
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


            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.

            recyclerView = view.findViewById(R.id.recyclterview);
            video_list = new ArrayList<>();
            all_images();
            //displayFiles();
            //enableViews(true);

            return view;

    }




    private void all_images() {
        fileList= new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

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

                myAdapeter = new MyAdapeter(fileList, getContext(), this);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                recyclerView.setAdapter(myAdapeter);
            } else {
                Toast.makeText(getContext(), "File is Null", Toast.LENGTH_SHORT).show();
            }

        }
    }
/*
    public void updateList() {
        if (adap != null) {
            adap.notifyDataSetChanged();
        }
    }*/

    private static class ViewHolder {
        private TextView name;
        private TextView date;
        private TextView details;
        private ImageView icon;
    }


        public ArrayList<File> findFile(File file) {
            ArrayList<File> arrayList = new ArrayList<>();
            File[] files = file.listFiles();
            for (File singleFile : files) {
                if (singleFile.isDirectory() && !singleFile.isHidden()) {
                    arrayList.addAll(findFile(singleFile));
                } else {
                    if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                            singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                            singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".png") ||
                            singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".gig") ||
                            singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".bmp") ||
                            singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".webp")) {
                        arrayList.add(singleFile);
                    }
                }
            }

            return arrayList;
        }

        public void displayFiles() {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            fileList = new ArrayList<>();
            fileList.addAll(findFile(Environment.getExternalStorageDirectory()));
            //myAdapeter = new MyAdapeter(fileList, getContext(), this, this);
            //fileAdapter= new FileAdapter(getContext(),fileList,this);
            //recyclerView.setAdapter(myAdapeter);
        }

        @Override
        public void OnFileClicked(File file) {

        }

        @Override
        public void OnFileLongClicked(File file, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.optiondialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(view);
            builder.setCancelable(true);

            LinearLayout details_linear = view.findViewById(R.id.details);
            LinearLayout rename_linear = view.findViewById(R.id.rename);
            LinearLayout delete_linear = view.findViewById(R.id.delete);
            LinearLayout share_linear = view.findViewById(R.id.share);

            details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                }
            });

            rename_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder rename_builder = new AlertDialog.Builder(getContext());
                    rename_builder.setTitle("Rename File");
                    final EditText name = new EditText(getContext());
                    rename_builder.setView(name);

                    rename_builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String new_name = name.getEditableText().toString();
                            String extention = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
                            File current = new File(file.getAbsolutePath());
                            File destination = new File(file.getAbsolutePath().replace(file.getName(), new_name) + extention);

                            if (current.renameTo(destination)) {
                                fileList.set(position, destination);
                                myAdapeter.notifyItemChanged(position);
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
                    rename.show();
                }
            });

            delete_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder deletedilog = new AlertDialog.Builder(getContext());
                    deletedilog.setTitle("Delete" + file.getName() + "?");
                    deletedilog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            file.delete();
                            fileList.remove(position);
                            myAdapeter.notifyDataSetChanged();
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
                }
            });

            share_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sharefile = file.getName();
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    //share.setType("*/*");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    startActivity(Intent.createChooser(share, "share" + sharefile
                    ));
                }
            });

            builder.show();
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
                                myAdapeter.notifyDataSetChanged();
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
        @Override
        public void OnBackpressed() {

        }

        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            menu.clear();
            inflater.inflate(R.menu.nav_menu, menu);
        }



    }

