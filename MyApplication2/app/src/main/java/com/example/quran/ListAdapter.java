package com.example.quran;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Arrays;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.view> {
    private String[] suratname;
    private String[] suratnamemeaning;
    Context context;
    MediaPlayer mp=new MediaPlayer();
    AdapterListner adapterListner;
    totaltime totaltime1;
    starttime starttime1;
    public ListAdapter(String[] suratname, String[] suratnamemeaning, Context context,
                       AdapterListner adapterListner,totaltime totaltime1,starttime starttime1) {
        this.suratname = suratname;
        this.suratnamemeaning = suratnamemeaning;
        this.context = context;
        this.adapterListner=adapterListner;
        this.totaltime1=totaltime1;
        this.starttime1=starttime1;
    }

    @NonNull
    @Override
    public view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View listitem= layoutInflater.inflate(R.layout.surat,parent,false);
        view viewHolder= new view(listitem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull view holder, int position) {
        holder.suratname.setText(suratname[position]);
        holder.suratnamemeaning.setText(suratnamemeaning[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.getAdapterPosition()){
                    case 0:
                    playSoundFromAssets(0);
                    break;
                    case 1:
                        playSoundFromAssets(1);
                        break;
                }
                for (int i=mp.getCurrentPosition();i<=mp.getDuration();i++) {
                    int starttime =i;
                    starttime1.starttime(starttime);
                }
                adapterListner.afterAdapterItemClicked(holder.getAdapterPosition());

            }
        });
    }


    @Override
    public int getItemCount() {
        return suratname.length;
    }

    public class view extends RecyclerView.ViewHolder{
        public TextView suratname,suratnamemeaning;
        public CardView cardView;
        public view(@NonNull View itemView) {
            super(itemView);

            this.suratname= itemView.findViewById(R.id.suratname);
            this.suratnamemeaning=itemView.findViewById(R.id.suratnamemeaning);
            this.cardView= itemView.findViewById(R.id.card);


        }
    }
    private void playSoundFromAssets(int index) {
        try {
            if (mp.isPlaying()){
                mp.stop();
                mp.reset();
            }
            AssetManager assetManager = context.getAssets();
            String[] audios = assetManager.list("Qirat");
            if (audios == null || index >= audios.length) {
                return;
            }
            String soundFilePath = new File("Qirat", audios[index]).getPath();
            AssetFileDescriptor afd = context.getAssets().openFd(soundFilePath);
            /*  For API 24+, we can just use the AssetFileDescriptor to play the sound. However,
                for API 23-, we can't use the AssetFileDescriptor directly but can retrieve a
                FileDescriptor from it that points to the beginning of our assets. The offset
                and length from the AssetFileDescriptor serve for the FileDescriptor as well.
             */

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if(mp == null){
                    mp = new MediaPlayer();
                }
                mp.setDataSource(afd);
            } else {
                FileDescriptor fd = afd.getFileDescriptor();
                Log.d("MainActivity", String.format("<<<< %s %d %d", soundFilePath, afd.getStartOffset(), afd.getLength()));
                if(mp == null){
                    mp = new MediaPlayer();
                }
                mp.setDataSource(fd, afd.getStartOffset(), afd.getLength());

                // One might think that mp.setDataSource(fd) would play the sound file we want, but
                // it actually plays all sound files one after another. It seems that fd is a
                // FileDescriptor that points to the beginning of the assets.
            }

            afd.close();
            mp.prepare();
            mp.start();
            int totaltime= mp.getDuration();
            totaltime1.totaltime(totaltime);
            //Toast.makeText(context, totaltime+"", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
