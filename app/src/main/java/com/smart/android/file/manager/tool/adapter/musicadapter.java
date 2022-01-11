package com.smart.android.file.manager.tool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.smart.android.file.manager.tool.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.android.file.manager.tool.items.GridViewItem;

import java.io.File;
import java.util.List;

public class musicadapter extends RecyclerView.Adapter<musicadapter.MyViewHolder> {
    List<GridViewItem> mlist;
    List<File> file;
    Context mcontext;

    public musicadapter(List<GridViewItem> mlist, List<File> file, Context mcontext) {
        this.mlist = mlist;
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.musiccontainer,parent,false);
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

        holder.tv_filename.setText(mlist.get(position).getName());

    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_filename,tv_filesize;
        ImageView more;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            more= itemView.findViewById(R.id.more);
           tv_filename=itemView.findViewById(R.id.songname);
           tv_filesize=itemView.findViewById(R.id.songsize);
        }
    }
}
