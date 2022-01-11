package com.smart.android.file.manager.tool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.Fragments.showImageFragment;
import com.smart.android.file.manager.tool.Music_Model;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.items.GridViewItem;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAdapeter extends RecyclerView.Adapter<MyAdapeter.MyViewHolder> {
    //List<File> files;
    List<GridViewItem> mlist;
    List<File> file;
    ArrayList<Music_Model> files;
    Context mcontext;
    FileSelectedListner listner;
   /* public MyAdapeter(ArrayList<Music_Model> mlist, Context mcontext,
                      FileSelectedListner listner, IonBackPressed backPressed) {
        this.files = mlist;
        this.mcontext = mcontext;
        this.listner=listner;
        this.backPressed= backPressed;
    }*/

    public MyAdapeter(List<File> mlist, Context mcontext,
                      FileSelectedListner listner) {
        this.file = mlist;
        this.mcontext = mcontext;
        this.listner=listner;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.image_container,parent,false);
        view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                            ContextMenu.ContextMenuInfo contextMenuInfo) {

            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint(
            "RecyclerView") int position) {
        Glide.with(mcontext).asBitmap()
                .load(file.get(position))
                .thumbnail(0.5f)
                .into(holder.imageView);
        String filepath = file.get(position).getName();
        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);

        holder.tv_filename.setText(filename);
        //Toast.makeText(mcontext, split[5]+"", Toast.LENGTH_SHORT).show();
        holder.tv_filename.setSelected(true);

        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args= new Bundle();
                args.putInt("imagepath",position);
                Gson gson= new Gson();
                String listitems= gson.toJson(file);
                args.putString("list",listitems);
                showImageFragment fragment = new showImageFragment();
                fragment.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_contaner,fragment).addToBackStack(null).commit();



            }
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mcontext, "Clicked", Toast.LENGTH_SHORT).show();
                /*PopupMenu popupMenu= new PopupMenu(mcontext,holder.options);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
                        popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.details:

                        }
                        return false;

                    }
                });*/

                //listner.OnFileLongClicked(currentfile,position);

                listner.itemclicked(holder.options,file.get(position),holder.getAdapterPosition());

                /*Dialog dialog = new Dialog(mcontext);
                dialog.setContentView(R.layout.optiondialog);

                LinearLayout details_linear = dialog.findViewById(R.id.details);
                LinearLayout rename_linear = dialog.findViewById(R.id.rename);
                LinearLayout delete_linear = dialog.findViewById(R.id.delete);
                LinearLayout share_linear = dialog.findViewById(R.id.share);

                details_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog detailsDialog= new Dialog(mcontext);
                        detailsDialog.setTitle("Details");
                        final TextView details= new TextView(mcontext);
                        details.setPadding(30,10,10,10);
                        detailsDialog.setContentView(details);
                        File currentfile= new File(mlist.get(position).getName());
                        Date lastmodified= new Date(currentfile.lastModified());
                        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String formatdate= formatter.format(lastmodified);
                        details.setText("File Name: "+currentfile.getName()+"\n"+
                                "Size: "+ Formatter.formatShortFileSize(mcontext,currentfile.length())+"\n"+
                                "Path:"+currentfile.getAbsolutePath()+"\n"+
                                "Last Modified: "+formatdate);

                        detailsDialog.show();
                    }


                });

                rename_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //holder.imageView.setImageURI(Uri.parse(mlist.get(position).getName()));
                dialog.show();*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return file.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,options;
        TextView tv_filename,tv_filesize;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView=itemView.findViewById(R.id.img_filetype);
           tv_filename=itemView.findViewById(R.id.tv_filename);
           tv_filesize=itemView.findViewById(R.id.tvfilesize);
           options= itemView.findViewById(R.id.more);
        }
    }
}
