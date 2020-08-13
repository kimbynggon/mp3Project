package com.example.mp3player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MusicData {
    private int album;
    private String name;
    private String title;
    private Bitmap bitmap;
    private String mtname;
    private String mttitle;
    private String filename;

    public MusicData(int album, String name) {
        this.album = album;
        this.name = name;
    }

    public MusicData(int album, String name, String title) {
        this.album = album;
        this.name = name;
        this.title = title;
    }

    public MusicData(Bitmap bitmap, String mtname, String mttitle, String filename) {
        this.bitmap = bitmap;
        this.mtname = mtname;
        this.mttitle = mttitle;
        this.filename = filename;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getMtname() {
        return mtname;
    }

    public void setMtname(String mtname) {
        this.mtname = mtname;
    }

    public String getMttitle() {
        return mttitle;
    }

    public void setMttitle(String mttitle) {
        this.mttitle = mttitle;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
