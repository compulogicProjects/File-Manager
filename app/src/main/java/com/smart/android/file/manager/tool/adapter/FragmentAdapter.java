package com.smart.android.file.manager.tool.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.smart.android.file.manager.tool.Fragments.AllDocument;
import com.smart.android.file.manager.tool.Fragments.DocFragment;
import com.smart.android.file.manager.tool.Fragments.PDFFragment;
import com.smart.android.file.manager.tool.Fragments.PPTFragment;
import com.smart.android.file.manager.tool.Fragments.XLSFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                Bundle argss= new Bundle();
                argss.putString("fileType","docdocument");
                DocFragment docFragment= new DocFragment();
                docFragment.setArguments(argss);
                return docFragment;
            case 2:
                Bundle argus= new Bundle();
                argus.putString("fileType","xlsdocument");
                XLSFragment xlsFragment= new XLSFragment();
                xlsFragment.setArguments(argus);
                return xlsFragment;
            case 3:
                Bundle args= new Bundle();
                args.putString("fileType","pptdocument");
                PPTFragment pptFragment= new PPTFragment();
                pptFragment.setArguments(args);
                return pptFragment;
            case 4:
                Bundle argis= new Bundle();
                argis.putString("fileType","pdfdocument");
                PDFFragment pdfFragment= new PDFFragment();
                pdfFragment.setArguments(argis);
                return pdfFragment;
            }
        Bundle args= new Bundle();
        args.putString("fileType","alldocument");
        AllDocument allDocument= new AllDocument();
        allDocument.setArguments(args);
        return allDocument;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
