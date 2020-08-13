package com.example.mp3player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyPlayList extends AppCompatActivity {

    private LinearLayout linearSlidingPag;
    private ImageButton ibPlay;
    private ImageButton ibAdd;
    private ImageButton ibnext;
    private ImageButton ibAgo;
    private TextView tvName;
    private TextView tvTitle;
    private SeekBar seekBar2;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ArrayList<MusicData> mp3list = new ArrayList<MusicData>();
    private MusicData selectMP3;
    private String sdcardpath;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int index;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_play_list);

        findViewByIdFunc();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        DrawerAdapter adapter = new DrawerAdapter(getApplicationContext());
        sdcardpath = Environment.getExternalStorageDirectory().getPath() + "/PlayList/";

        adapter.setMp3Arraylist(mp3list);
        listView.setAdapter(adapter);
        LoadMP3SDCardFile();

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ibPlay.isActivated()) {
                    ibPlay.setActivated(false);
                    mediaPlayer.start();
                } else {
                    ibPlay.setActivated(true);
                    mediaPlayer.pause();
                }
                seekBar2.setMax(mediaPlayer.getDuration());

                seekBar2.setProgress(mediaPlayer.getCurrentPosition());
                seekBar2.setProgress(0);

            }
        });
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ibAdd.isActivated()) {
                    ibAdd.setActivated(false);
                } else {
                    ibAdd.setActivated(true);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectMP3 = mp3list.get(position);
                index = position;
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(sdcardpath + selectMP3.getFilename());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    tvName.setText(selectMP3.getMtname());
                    tvTitle.setText(selectMP3.getMttitle());
                    imageView.setImageBitmap(selectMP3.getBitmap());
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            while (mediaPlayer.isPlaying()) {

                                seekBar2.setMax(mediaPlayer.getDuration());
                                seekBar2.setProgress(mediaPlayer.getCurrentPosition());
                                SystemClock.sleep(100);
                            }
                        }
                    };
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ibnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.reset();
                nextSong();
            }
        });
        ibAgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.reset();
                agoSong();
            }
        });
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ibAdd.isActivated()) {
                    ibAdd.setActivated(true);

                } else {
                    ibAdd.setActivated(false);
                }
            }
        });
    }

    private void agoSong() {
        index--;

        selectMP3 = mp3list.get(index);
        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(sdcardpath + selectMP3.getFilename());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (mediaPlayer.isPlaying()) {

                        seekBar2.setMax(mediaPlayer.getDuration());
                        seekBar2.setProgress(mediaPlayer.getCurrentPosition());
                        SystemClock.sleep(100);
                    }
                }
            };
            thread.start();
            tvName.setText(selectMP3.getMtname());
            tvTitle.setText(selectMP3.getMttitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nextSong() {
        index++;

        selectMP3 = mp3list.get(index);
        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(sdcardpath + selectMP3.getFilename());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (mediaPlayer.isPlaying()) {

                        seekBar2.setMax(mediaPlayer.getDuration());
                        seekBar2.setProgress(mediaPlayer.getCurrentPosition());
                        SystemClock.sleep(100);
                    }
                }
            };
            thread.start();
            tvName.setText(selectMP3.getMtname());
            tvTitle.setText(selectMP3.getMttitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //이벤트 등록 핸들러 함수
    private void findViewByIdFunc() {
        linearSlidingPag = (LinearLayout) findViewById(R.id.linearSlidingPag);
        ibPlay = findViewById(R.id.ibPlay);
        ibAdd = findViewById(R.id.ibAdd);
        ibAgo = findViewById(R.id.ibAgo);
        ibnext = findViewById(R.id.ibNext);
        tvName = (TextView) findViewById(R.id.tvName);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        listView = (ListView) findViewById(R.id.listView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    //sd 카드에 저장된 mp3 데이터 불러오기
    private void LoadMP3SDCardFile() {
        File[] files = new File(sdcardpath).listFiles();
        for (File f : files) {
            String filename = f.getName();
            String extendsname = filename.substring(filename.length() - 3);
            if (extendsname.equals("mp3")) {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(sdcardpath + filename);
                byte[] data = mmr.getEmbeddedPicture();
                Bitmap bitmap = null;
                if (data != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                }

                String mtMusicDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                String mtMusicName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String mtName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

                MusicData musicData = new MusicData(bitmap, mtName, mtMusicName, filename);

                mp3list.add(musicData);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.myPlaylist:
                Intent intent1 = new Intent(getApplicationContext(), MusicPlayMain.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
