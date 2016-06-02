package com.example.cro_germany.easywrite;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;

public class PlayAlarmAty extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.alarm_player_aty);

        mp = MediaPlayer.create(this, R.raw.music);
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mp.stop();
        mp.release();
    }

    private MediaPlayer mp;
}
