package com.example.mp3player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayMain extends AppCompatActivity {

    private ImageButton ibPlay;
    private ImageButton ibAdd;
    private ImageButton ibNext;
    private ImageButton ibAgo;
    private ListView listView;
    private SeekBar seekBar;
    private TextView tvName;
    private TextView tvTitle;


    private MusicData selectMP3;
    private String sdcardpath;
    private ArrayList<MusicData> mp3list = new ArrayList<MusicData>();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play_main);

        findViewByIdFunc();

        //액티비티에 권한설정
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        //변수에 디렉토리 환경설정하고 music경로에 연결하기
        sdcardpath = Environment.getExternalStorageDirectory().getPath() + "/Music/";

        MusicAdapter adapter = new MusicAdapter(getApplicationContext());
        //리스트뷰에 mp3리스트 세팅해주기
        adapter.setMp3Arraylist(mp3list);
        listView.setAdapter(adapter);
        //sd카드에 저장된 mp3데이터 불러오기
        LoadMP3SDCardFile();

        //미디어 플레이어에
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextSong();
            }
        });
        //재생돼는 seebar 를 원하는곳을 움직이게하는 이벤트
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
                seekBar.setMax(mediaPlayer.getDuration());

                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                seekBar.setProgress(0);

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

                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            while (mediaPlayer.isPlaying()) {

                                seekBar.setMax(mediaPlayer.getDuration());
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
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

        ibNext.setOnClickListener(new View.OnClickListener() {
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
                    ibAdd.setActivated(false);

                } else {
                    ibAdd.setActivated(true);
                }
            }
        });
    }

    //이전곡으로 재생돼게하는 핸들러 함수
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

                        seekBar.setMax(mediaPlayer.getDuration());
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
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

    //다음곡으로 재생되게하는 함수
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

                        seekBar.setMax(mediaPlayer.getDuration());
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
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

    //sd카드에서 mp3 이미지 노래 모아오기 (메타 데이터)


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
                    options.inSampleSize = 8;
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
                Intent intent1 = new Intent(getApplicationContext(), MyPlayList.class);
                startActivity(intent1);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //이벤트 등록 함수등록
    private void findViewByIdFunc() {
        ibPlay = (ImageButton) findViewById(R.id.ibPlay);
        ibAdd = (ImageButton) findViewById(R.id.ibAdd);
        listView = (ListView) findViewById(R.id.listView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tvName = (TextView) findViewById(R.id.tvName);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ibAgo = (ImageButton) findViewById(R.id.ibAgo);
        ibNext = (ImageButton) findViewById(R.id.ibNext);
    }
}