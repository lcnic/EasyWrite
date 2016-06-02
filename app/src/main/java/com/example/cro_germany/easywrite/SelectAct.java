package com.example.cro_germany.easywrite;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectAct extends Activity implements OnClickListener {

    private Button s_delete, s_back;
    private ImageView s_img;
    private VideoView s_video;
    private TextView s_tv;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.select);

        s_delete = (Button) findViewById(R.id.s_delete);
        s_back = (Button) findViewById(R.id.s_back);
        s_img = (ImageView) findViewById(R.id.s_img);
        s_video = (VideoView) findViewById(R.id.s_video);
        s_tv = (TextView) findViewById(R.id.s_tv);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        s_back.setOnClickListener(this);
        s_delete.setOnClickListener(this);

        //判断携带过来的数据的类型，采取不同的操作
        if (getIntent().getStringExtra(NotesDB.PATH).equals("null")) {
            s_img.setVisibility(View.GONE);
        } else {
            s_img.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")) {
            s_video.setVisibility(View.GONE);
        } else {
            s_video.setVisibility(View.VISIBLE);
        }
        s_tv.setText(getIntent().getStringExtra(NotesDB.CONTENT));//加载textview的内容
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
                NotesDB.PATH));//获取图片的缩略图
        s_img.setImageBitmap(bitmap);//加载imageview的内容
        s_video.setVideoURI(Uri
                .parse(getIntent().getStringExtra(NotesDB.VIDEO)));//获取视频的地址
        s_video.start();//启动视频播放
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteDate();
                finish();
                break;

            case R.id.s_back:
                finish();
                break;
        }
    }

    //从数据库删除数据
    public void deleteDate() {
        dbWriter.delete(NotesDB.TABLE_NAME,
                "_id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);
    }
}