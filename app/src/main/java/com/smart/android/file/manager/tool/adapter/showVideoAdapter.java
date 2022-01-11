package com.smart.android.file.manager.tool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.Fragments.videoView;
import com.smart.android.file.manager.tool.R;
import com.smart.android.file.manager.tool.items.VideoItems;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

public class showVideoAdapter extends RecyclerView.Adapter<showVideoAdapter.MyViewHolder> {
    List<VideoItems> mlist;
    Context mcontext;
    List<File> file;
    FileSelectedListner fileSelectedListner;

    public showVideoAdapter(List<File> file, Context mcontext,FileSelectedListner fileSelectedListner) {
        this.file = file;
        this.mcontext = mcontext;
        this.fileSelectedListner=fileSelectedListner;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.video_item,parent,false);
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
        Glide.with(mcontext)
                .load(file.get(position))
                .skipMemoryCache(false)
                .into(holder.imageView);

        String filepath=file.get(position).getPath();
        String filename=filepath.substring(filepath.lastIndexOf("/")+1);
        holder.filename.setText(filename);

        int items=0;
        if (file.get(position).isDirectory()){
            File[] files= file.get(position).listFiles();
            for (File singleFile:files){
                if (!singleFile.isHidden()){
                    items +=1;
                }
            }
            holder.filesize.setText(items+"Files");
        }
        else {
            holder.filesize.setText(Formatter.formatFileSize(mcontext,file
                    .get(position).length()));
        }


        /*holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.relativeLayout.setAlpha(0);*/
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args= new Bundle();
                args.putInt("imagepath",position);
                Gson gson= new Gson();
                String listitems= gson.toJson(file);
                args.putString("list",listitems);
                videoView fragment = new videoView();
                fragment.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                activity.getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_contaner,fragment).addToBackStack(null).commit();

            }
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileSelectedListner.itemclicked(holder.options,file.get(position),
                        holder.getAdapterPosition());
            }
        });

        //holder.imageView.setImageURI(Uri.parse(mlist.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return file.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,options;
        LinearLayout linearLayout;
        TextView filename,filesize;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linear);
            imageView=itemView.findViewById(R.id.iamgeview);
            filesize=itemView.findViewById(R.id.tv_filesize);
            filename=itemView.findViewById(R.id.tv_filename);
            options=itemView.findViewById(R.id.more);
        }
    }
}
