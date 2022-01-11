package com.smart.android.file.manager.tool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smart.android.file.manager.tool.FileSelectedListner;
import com.smart.android.file.manager.tool.Music_Model;
import com.smart.android.file.manager.tool.R;

import java.util.ArrayList;

public class Videos_New_Adapter extends RecyclerView.Adapter<Videos_New_Adapter.Video_ViewHolder> {
    ArrayList<Music_Model> video_list;
     Context context;
    FileSelectedListner fileSelectedListner;

    public Videos_New_Adapter(ArrayList<Music_Model> video_list,
                              Context context, FileSelectedListner litener) {
        this.video_list = video_list;
        this.context = context;
        this.fileSelectedListner = litener;
    }

    @NonNull
    public Video_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.video_item,
                parent, false);
        return new Video_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Video_ViewHolder holder, int position) {
        Music_Model videosData = video_list.get(position);
        holder.filename.setText(videosData.getTitle());
        holder.filename.setSelected(true);


        double millisec = Double.parseDouble(video_list.get(position).getDuration());
        //holder.duration.setText(convert_Time((long) millisec ));
        Glide.with(context).load(videosData.getPath()).into(holder.
                imageView);

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fileSelectedListner.itemclicked(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_Music(position);
            }
        });
    }

    public void play_Music(int position) {
  /*      //MediaPlayer_Class.getMy_Player().reset();
        //MediaPlayer_Class.currentIndex = position;
        Intent intent = new Intent(context,Video_Player_Activity.class);
        intent.putExtra("videos", video_list);
        intent.putExtra("position", position);
        intent.putExtra("title", video_list.get(position).getTitle());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("video_list", video_list);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/
    }

    @Override
    public int getItemCount() {
        return video_list.size();
    }

    public static class Video_ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,options;
        LinearLayout linearLayout;
        TextView filename,filesize;
        public Video_ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linear);
            imageView=itemView.findViewById(R.id.iamgeview);
            filesize=itemView.findViewById(R.id.tv_filesize);
            filename=itemView.findViewById(R.id.tv_filename);
            options=itemView.findViewById(R.id.more);
        }
    }

    @SuppressLint("DefaultLocale")
    public String convert_Time(long value){
        String video_time;
        int duration = (int) value;
        int hrs = (duration/3600000);
        int mins = (duration/60000) % 60000;
        int sec = duration%60000/1000;

        if (hrs >0){
            video_time = String.format("%02d:%02d:%02d",hrs,mins,sec); }
        else {
            video_time = String.format("%02d:%02d",mins,sec); }
        return video_time;
    }

    void update_Files(ArrayList<Music_Model> files) {
        video_list = new ArrayList<>();
        video_list.addAll(files);
        notifyDataSetChanged();
    }
}
