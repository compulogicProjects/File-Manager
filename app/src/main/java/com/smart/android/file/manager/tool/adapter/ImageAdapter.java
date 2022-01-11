package com.smart.android.file.manager.tool.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageAdapter extends PagerAdapter {
    private Context mcontext;
    List<File> items;
    int imagepath;

    public ImageAdapter(Context mcontext, List<File> items, int imagepath) {
        this.mcontext = mcontext;
        this.items=items;
        this.imagepath=imagepath;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView= new ImageView(mcontext);
        imageView.setImageURI(Uri.parse(String.valueOf(items.get(position))));
        container.addView(imageView,0);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imageView);
        pAttacher.update();
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }


}
