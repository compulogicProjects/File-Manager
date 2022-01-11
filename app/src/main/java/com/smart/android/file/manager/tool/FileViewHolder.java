package com.smart.android.file.manager.tool;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class FileViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName,tvSize,empty;
    public LinearLayout container;
    public ImageView imgFilel,option;
    public FileViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName=itemView.findViewById(R.id.songname);
        tvSize=itemView.findViewById(R.id.songsize);
        container= itemView.findViewById(R.id.container);
        imgFilel=itemView.findViewById(R.id.srcimg);
        option= itemView.findViewById(R.id.more);
        //empty=itemView.findViewById(R.id.empty);
    }
}
