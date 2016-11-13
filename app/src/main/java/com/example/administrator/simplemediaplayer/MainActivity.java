package com.example.administrator.simplemediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String tag="SimpleMediaPlayer";
    private MediaPlayer mplayer;
    private  String mpath;
    private VideoView mview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=getIntent();
        Uri u=i.getData();
        if(u!=null){
            mpath=u.getPath();
            setTitle(mpath);
            if(i.getType().contains("audio")){
                setContentView(android.R.layout.simple_list_item_1);
                mplayer=new MediaPlayer();
                try {
                    mplayer.setDataSource(mpath);
                    mplayer.prepare();
                    mplayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(i.getType().contains("video")){
                    mview=new VideoView(this);
                    mview.setVideoPath(mpath);
                    mview.setMediaController(new MediaController(this));
                    mview.start();
                    setContentView(mview);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"stop");
        menu.add(0,2,0,"start");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:if(mplayer==null||!mplayer.isPlaying()){
                Toast.makeText(this,"没有音乐文件",Toast.LENGTH_SHORT).show();
            }else{
                mplayer.pause();
            }break;
            case 2:if(mplayer==null){
                Toast.makeText(this,"未选中音乐文件",Toast.LENGTH_SHORT).show();
            }else{
                mplayer.start();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mplayer!=null){
            mplayer.stop();
        }
        if(mview!=null){
            mview.stopPlayback();
        }
    }
}
