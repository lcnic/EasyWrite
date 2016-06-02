package com.example.cro_germany.easywrite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class DrawActivity extends Activity {

    private Button btn;//声明对应布局中的控件对象
    private MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.draw);
        btn = (Button) findViewById(R.id.btn);//找ID
        view = (MyView) findViewById(R.id.draw);
        btn.setOnClickListener(new OnClickListener() {//设置监听事件

            @Override
            public void onClick(View v) {//执行onClick方法
                view.clear();//调用清除画布的方法
            }
        });
    }

}
