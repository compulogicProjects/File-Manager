package com.smart.android.file.manager.tool.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.FileViewHolder;
import com.smart.android.file.manager.tool.R;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {
    private Context context;
    private List<File> file;
    FileSelectedListner listner;
    public static int items;
    public FileAdapter(Context context, List<File> file,FileSelectedListner listner) {
        this.context = context;
        this.file = file;
        this.listner=listner;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(context).inflate(R.layout.musiccontainer
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.tvName.setText(file.get(position).getName());
        holder.tvName.setSelected(true);

        items=0;
        if (file.get(position).isDirectory()){
            holder.option.setVisibility(View.INVISIBLE);
            File[] files= file.get(position).listFiles();
            for (File singleFile:files){
                if (!singleFile.isHidden()){
                    items +=1;
                }
            }

            holder.tvSize.setText(items+"Files");
        }
        else {
            holder.tvSize.setText(Formatter.formatFileSize(context,file.get(position).length()));
        }
        if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".png")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".gif")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".bmp")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".webp")){
            Glide.with(context).asBitmap()
                    .load(file.get(position))
                    .thumbnail(0.5f)
                    .error(R.drawable.ic_baseline_camera_alt)
                    .into(holder.imgFilel);
           // holder.imgFilel.setImageResource(R.drawable.ic_image);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".pdf")){
            holder.imgFilel.setImageResource(R.drawable.ic_pdf);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".doc")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".docm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".docx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".dot")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".dotm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".dotx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".odt")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ott")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".fodt")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".rtf")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".wps")){
            holder.imgFilel.setImageResource(R.drawable.ic_docs);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".mp3") ||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ogg")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".wav")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".mid")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".m4a")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".amr")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".aac")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".opus")){
            holder.imgFilel.setImageResource(R.drawable.ic_music);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".mp4")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".3gp")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".mkv")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".webm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".flv")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".m4v")){
            holder.imgFilel.setImageResource(R.drawable.ic_play);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".apk")){
            holder.imgFilel.setImageResource(R.drawable.ic_android);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xls")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xlsx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xlsb")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xlsm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xlt")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xltx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".xlw")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ods")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ots")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".fods")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".csv")){
            holder.imgFilel.setImageResource(R.drawable.excel);
        }
        else if (file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ppt")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".pptx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ppt")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".pptm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".pps")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ppsx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".ppsm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".pot")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".potx")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".potm")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".odp")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".otp")||
                file.get(position).getName().toLowerCase(Locale.ROOT).endsWith(".fodp")){
            holder.imgFilel.setImageResource(R.drawable.ppt);
        }
        else {
            holder.imgFilel.setImageResource(R.drawable.folder);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), file.get(position)+"", Toast.LENGTH_SHORT).show();
                //listner.itemclicked(holder.container,file.get(position),holder.getAdapterPosition());
            listner.OnFileClicked(file.get(position));
            }
        });

        /*holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listner.OnFileLongClicked(file.get(position));
                return true;
            }
        });*/

        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // listner.itemclicked(holder.option,file.get(position),
                 //       holder.getAdapterPosition());
                listner.itemclicked(holder.container,file.get(position),
                        holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return file.size();
    }
}
