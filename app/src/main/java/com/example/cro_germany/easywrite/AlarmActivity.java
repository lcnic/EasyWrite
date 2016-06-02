package com.example.cro_germany.easywrite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class AlarmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.alarm);

    }

}