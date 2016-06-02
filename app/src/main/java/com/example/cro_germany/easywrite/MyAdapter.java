package com.example.cro_germany.easywrite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//显示数据类，复写BaseAdapter的四个方法getCount,getItem,getItemId,getView,创建一个构造方法MyAdapter
public class MyAdapter extends BaseAdapter {

    private Context context;//承接上下文的参数
    private Cursor cursor;//数据库中查询出来的为cursor类型
    private LinearLayout layout;//创建视图对象

    public MyAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }//返回游标cursor的长度

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }//返回游标位置

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);//加载视图的权限
        layout = (LinearLayout) inflater.inflate(R.layout.cell, null);//实例化视图对象
        TextView contenttv = (TextView) layout.findViewById(R.id.list_content);//获取cell布局文件中的每一个内容
        TextView timetv = (TextView) layout.findViewById(R.id.list_time);
        ImageView imgiv = (ImageView) layout.findViewById(R.id.list_img);
        ImageView videoiv = (ImageView) layout.findViewById(R.id.list_video);//截取视频中的一张图片加载
        cursor.moveToPosition(position);//移动光标到一个绝对的位置
        String content = cursor.getString(cursor.getColumnIndex("content"));//承载查询的具体对象
        String time = cursor.getString(cursor.getColumnIndex("time"));
        String url = cursor.getString(cursor.getColumnIndex("path"));
        String urlvideo = cursor.getString(cursor.getColumnIndex("video"));
        contenttv.setText(content);//添加
        timetv.setText(time);
        videoiv.setImageBitmap(getVideoThumbnail(urlvideo, 200, 200,
                MediaStore.Images.Thumbnails.MICRO_KIND));
        imgiv.setImageBitmap(getImageThumbnail(url, 200, 200));
        return layout;
    }

    //获取图片缩略图
    public Bitmap getImageThumbnail(String uri, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri, options);//获取图片
        options.inJustDecodeBounds = false;
        //加工图片
        int beWidth = options.outWidth / width;
        int beHeight = options.outHeight / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(uri, options);//获取加工后的图片
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    //获取视频缩略图
    private Bitmap getVideoThumbnail(String uri, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;
    }

}
