package com.smart.android.file.manager.tool.items;


public class GridViewItem extends Object{
   private String name;
   private int position;
   private long length;
    public GridViewItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public long getLength() {
        return length;
    }


}
