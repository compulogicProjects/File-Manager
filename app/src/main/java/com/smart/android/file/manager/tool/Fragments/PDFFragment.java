package com.smart.android.file.manager.tool.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.android.file.manager.tool.FLUtil;
import com.smart.android.file.manager.tool.FileOpner;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.Music_Model;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.adapter.FileAdapter;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PDFFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PDFFragment extends Fragment implements FileSelectedListner {
    private RecyclerView recyclerView;
    private List<File> fileList;
    private FileAdapter fileAdapter;
    File file,files[];
    View view;
    File path;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PDFFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PDFFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PDFFragment newInstance(String param1, String param2) {
        PDFFragment fragment = new PDFFragment();
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
        view= inflater.inflate(R.layout.fragment_first,container,false);
        path= Environment.getExternalStorageDirectory();
        //displayFiles();
        pdfdocuments();
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
                    case "pdfdocument":
                        if (singleFile.getName().toLowerCase(Locale.ROOT).endsWith(".pdf")){
                            arrayList.add(singleFile);
                        }
                        break;


                }
            }
        }
        return arrayList;
    }

    private void pdfdocuments() {
        String[] selectionArgs;
        String selectionQuery;
        fileList= new ArrayList<>();
        Uri uri = MediaStore.Files.getContentUri("external");
        List<String> extList = new ArrayList<>(FLUtil.doc_ext);
        extList.addAll(FLUtil.xl_ext);
        extList.addAll(FLUtil.ppt_ext);
        extList.addAll(FLUtil.opendoc_ext);
        extList.add("pdf");
        selectionArgs = FLUtil.getMimeTypeQueryArgs(Collections.singletonList("pdf"));
        //selectionArgs = FLUtil.getMimeTypeQueryArgs(extList);
        selectionQuery = FLUtil.getMimeTypeQuery(selectionArgs);

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
                recyclerView= view.findViewById(R.id.allrecycler);
                fileAdapter = new FileAdapter(getContext(),fileList, this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(fileAdapter);
            } else {
                Toast.makeText(getContext(), "File is Null", Toast.LENGTH_SHORT).show();
            }

        }
    }



    public void displayFiles(){
        recyclerView= view.findViewById(R.id.allrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        fileList= new ArrayList<>();
        fileList.addAll(findFile(path));
        fileAdapter= new FileAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(fileAdapter);
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
                Uri uri= Uri.parse(file.getAbsolutePath());
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("pdf/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share pdf files..."));
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
                                Toast.makeText(getContext(), file + "", Toast.LENGTH_SHORT).show();
                                //file.delete();
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
                        Uri uri= Uri.parse(file.getAbsolutePath());
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("pdf/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share pdf files..."));
                        break;
                    case R.id.rename:
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