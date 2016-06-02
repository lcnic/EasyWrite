package com.example.cro_germany.easywrite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {


    private ImageButton drawbtn;//声明涂鸦按钮
    private Button textbtn, imgbtn, videobtn;//声明三个按钮
    private ListView lv;//声明列表
    private Intent i;//创建标识
    private MyAdapter adapter;//创建MyAdapter对象
    private NotesDB notesDB;//创建数据库对象
    private SQLiteDatabase dbReader;//创建可读的权限
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);

        initView();//调用初始化方法
    }


    //进行初始化操作的方法
    public void initView() {
        lv = (ListView) findViewById(R.id.list);//找ID操作
        textbtn = (Button) findViewById(R.id.text);
        imgbtn = (Button) findViewById(R.id.img);
        videobtn = (Button) findViewById(R.id.video);
        drawbtn = (ImageButton) findViewById(R.id.drawbtn);
        textbtn.setOnClickListener(this);//添加监听事件
        imgbtn.setOnClickListener(this);
        videobtn.setOnClickListener(this);
        drawbtn.setOnClickListener(this);
        notesDB = new NotesDB(this);//实例化
        dbReader = notesDB.getReadableDatabase();//获取到可读的权限
        //给列表添加监听事件
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cursor.moveToPosition(position);//获取目前游标指示的位置
                Intent i = new Intent(MainActivity.this, SelectAct.class);//界面跳转
                //【putExtra("A",B)中，AB为键值对，第一个参数为键名，第二个参数为键对应的值。顺便提一下，
                // 如果想取出Intent对象中的这些值，需要在你的另一个Activity中用getXXXXXExtra方法，
                // 注意需要使用对应类型的方法，参数为键名】
                i.putExtra(NotesDB.ID,
                        cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
                        .getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,
                        cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,
                        cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,
                        cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);//跳转到详情页
            }
        });
    }

    //继承OnClickListener后复写onClick方法获取ID
    @Override
    public void onClick(View v) {
        i = new Intent(this, AddContent.class);//实例化标识，跳转到编辑类
        switch (v.getId()) {
            case R.id.text://文字
                i.putExtra("flag", "1");
                startActivity(i);//设置键值进行跳转
                break;

            case R.id.img://图片
                i.putExtra("flag", "2");
                startActivity(i);
                break;

            case R.id.video://视频
                i.putExtra("flag", "3");
                startActivity(i);
                break;

            case R.id.drawbtn://涂鸦
                i.setClass(MainActivity.this, DrawActivity.class);
                startActivity(i);
                break;
        }
    }

    //获取数据的方法
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,//返回的为cursor对象
                null, null);
        adapter = new MyAdapter(this, cursor);//实例化，cursor绑定到adapter
        lv.setAdapter(adapter);//adapter绑定到列表控件
    }

    //activity生命周期
    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

}
