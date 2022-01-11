package com.smart.android.file.manager.tool;

import android.view.View;

import java.io.File;

public interface FileSelectedListner {
    void OnFileClicked(File file);
    void OnFileLongClicked(File file, int position);
    void itemclicked(View v,File file, int position);

}
