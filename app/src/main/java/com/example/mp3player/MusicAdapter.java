package com.example.mp3player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MusicData> mp3Arraylist;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<MusicData> getMp3Arraylist() {
        return mp3Arraylist;
    }

    public void setMp3Arraylist(ArrayList<MusicData> mp3Arraylist) {
        this.mp3Arraylist = mp3Arraylist;
    }

    public MusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mp3Arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mp3Arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.music_layout_list, null);
        }
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvName = view.findViewById(R.id.tvName);

        MusicData musicData = mp3Arraylist.get(position);

        imageView.setImageBitmap(musicData.getBitmap());
        tvName.setText(musicData.getMtname());
        tvTitle.setText(musicData.getMttitle());


        return view;
    }
}
