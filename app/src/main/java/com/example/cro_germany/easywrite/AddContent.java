package com.example.cro_germany.easywrite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener {

    private Intent i;
    private Button alarm;
    private String val;//创建对象来接收标识的键值
    private Button savebtn, deletebtn;//声明该界面的几个控件
    private EditText ettext;
    private ImageView c_img;
    private VideoView v_video;
    private NotesDB notesDB;//创建数据库对象
    private SQLiteDatabase dbWriter;//创建可写的权限
    private File phoneFile, videoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.addcontent);
        val = getIntent().getStringExtra("flag");//接收键值
        savebtn = (Button) findViewById(R.id.save);//寻找ID
        deletebtn = (Button) findViewById(R.id.delete);
        ettext = (EditText) findViewById(R.id.ettext);
        c_img = (ImageView) findViewById(R.id.c_img);
        v_video = (VideoView) findViewById(R.id.c_video);
        alarm = (Button) findViewById(R.id.alarm);
        savebtn.setOnClickListener(this);//添加监听事件
        deletebtn.setOnClickListener(this);
        alarm.setOnClickListener(this);
        notesDB = new NotesDB(this);//数据库实例化
        dbWriter = notesDB.getWritableDatabase();//获取写入数据的权限
        initView();//调用
    }

    public void initView() {
        if (val.equals("1")) { // 添加的为文字
            c_img.setVisibility(View.GONE);//图片，视频隐藏
            v_video.setVisibility(View.GONE);
        }
        if (val.equals("2")) {
            c_img.setVisibility(View.VISIBLE);//图片显示
            v_video.setVisibility(View.GONE);//视频隐藏
            //跳转到系统相机直接拍照
            Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //获取图片的绝对路径，用时间命名
            phoneFile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() + "/" + getTime() + ".jpg");
            iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));//存给iimg
            startActivityForResult(iimg, 1);//带返回值的跳转
        }
        if (val.equals("3")) {
            c_img.setVisibility(View.GONE);//图片隐藏
            v_video.setVisibility(View.VISIBLE);//视频显示
            Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoFile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() + "/" + getTime() + ".mp4");
            video.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
            startActivityForResult(video, 2);
        }
    }

    //继承OnClickListener后复写onClick方法获取ID
    @Override
    public void onClick(View v) {
        i = new Intent(this, AlarmActivity.class);
        switch (v.getId()) {
            case R.id.save:
                addDB();//调用添加数据的方法
                finish();//关闭掉当前界面
                break;

            case R.id.delete:
                finish();
                break;

            case R.id.alarm:
                i.setClass(AddContent.this, AlarmActivity.class);
                startActivity(i);
                break;
        }
    }

    //添加数据的方法
    public void addDB() {
        ContentValues cv = new ContentValues();//创建ContentValues对象
        cv.put(NotesDB.CONTENT, ettext.getText().toString());//（获取的内容，获取的途径：输入框输入）
        cv.put(NotesDB.TIME, getTime());//获取时间，通过getTime方法
        cv.put(NotesDB.PATH, phoneFile + "");
        cv.put(NotesDB.VIDEO, videoFile + "");
        dbWriter.insert(NotesDB.TABLE_NAME, null, cv);//插入到数据库中（表名，约束，ContentValues对象）
    }

    //获取系统时间的方法
    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//指定时间所显示的格式
        Date curDate = new Date();//获取时间并按指定格式返回值
        String str = format.format(curDate);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//传递的为一张图片
            Bitmap bitmap = BitmapFactory.decodeFile(phoneFile
                    .getAbsolutePath());//获取路径展示内容
            c_img.setImageBitmap(bitmap);//通过控件展示图片
        }
        if (requestCode == 2) {
            v_video.setVideoURI(Uri.fromFile(videoFile));//获取路径
            v_video.start();//播放
        }
    }
}