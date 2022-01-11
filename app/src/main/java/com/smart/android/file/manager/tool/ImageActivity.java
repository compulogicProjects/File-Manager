package com.smart.android.file.manager.tool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.adapter.MyAdapeter;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImageActivity extends AppCompatActivity implements FileSelectedListner,IonBackPressed {
    RecyclerView recyclerView;
    private List<File> fileList;
    MyAdapeter myAdapeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        recyclerView = findViewById(R.id.recyclterview);
        displayFiles();
        
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
                        singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".png")) {
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    public void displayFiles() {
        recyclerView.setHasFixedSize(true);
        fileList = new ArrayList<>();
        fileList.addAll(findFile(Environment.getExternalStorageDirectory()));
        //myAdapeter = new MyAdapeter(fileList, ImageActivity.this, this, this);
        //fileAdapter= new FileAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(myAdapeter);
        recyclerView.setLayoutManager(new GridLayoutManager(ImageActivity.this, 3));

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(ImageActivity.this,HomeActivity.class);
        startActivity(intent);
        //super.onBackPressed();
    }

    @Override
    public void OnFileClicked(File file) {

    }

    @Override
    public void OnFileLongClicked(File file, int position) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.optiondialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
        builder.setView(view);
        builder.setCancelable(true);

        LinearLayout details_linear = view.findViewById(R.id.details);
        LinearLayout rename_linear = view.findViewById(R.id.rename);
        LinearLayout delete_linear = view.findViewById(R.id.delete);
        LinearLayout share_linear = view.findViewById(R.id.share);

        details_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder detailsDialog = new AlertDialog.Builder(ImageActivity.this);
                detailsDialog.setTitle("Details");
                final TextView details = new TextView(ImageActivity.this);
                details.setPadding(30, 10, 10, 10);
                detailsDialog.setView(details);
                Date lastmodified = new Date(file.lastModified());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formatdate = formatter.format(lastmodified);
                details.setText("File Name: " + file.getName() + "\n" +
                        "Size: " + Formatter.formatShortFileSize(ImageActivity.this, file.length()) + "\n" +
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
                AlertDialog.Builder rename_builder = new AlertDialog.Builder(ImageActivity.this);
                rename_builder.setTitle("Rename File");
                final EditText name = new EditText(ImageActivity.this);
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
                            Toast.makeText(ImageActivity.this, "Renamed!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ImageActivity.this, "Failed to renamed!", Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder deletedilog = new AlertDialog.Builder(ImageActivity.this);
                deletedilog.setTitle("Delete" + file.getName() + "?");
                deletedilog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        file.delete();
                        fileList.remove(position);
                        myAdapeter.notifyDataSetChanged();
                        Toast.makeText(ImageActivity.this, "Deleted !", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void OnBackpressed() {

    }
}