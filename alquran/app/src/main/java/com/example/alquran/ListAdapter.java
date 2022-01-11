package com.example.alquran;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.view> {
    private String[] suratname;
    private String[] suratnamemeaning;
    Context context;

    public ListAdapter(String[] suratname, String[] suratnamemeaning, Context context) {
        this.suratname = suratname;
        this.suratnamemeaning = suratnamemeaning;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View listitem= layoutInflater.inflate(R.layout.surat,parent,false);
        ListAdapter.view viewHolder= new ListAdapter.view(listitem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.view holder, int position) {
        holder.suratname.setText(suratname[position]);
        holder.suratnamemeaning.setText(suratnamemeaning[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer= MediaPlayer.create(context,R.raw.asshams);
                mediaPlayer.start();
                Toast.makeText(context, holder.suratname.getText()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return suratname.length;
    }

    public class view extends RecyclerView.ViewHolder {
        public TextView suratname,suratnamemeaning;
        public CardView cardView;
        public view(@NonNull View itemView) {
            super(itemView);

            this.suratname= itemView.findViewById(R.id.suratname);
            this.suratnamemeaning=itemView.findViewById(R.id.suratnamemeaning);
            this.cardView= itemView.findViewById(R.id.card);

        }
    }
    public void audioPlayer(String path, String fileName){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
            mp.setDataSource(path + File.separator + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
