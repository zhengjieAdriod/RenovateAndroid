package com.baiducloud.dawnoct.pictruetest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.gridview_01)
    MyGridView gridview_01;
    @BindView(R.id.start_in_tv)
    TextView start_in_tv;
    private List<Imagepxh> Imagepxhlist;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_process_layout);
        //绑定activity
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        Imagepxhlist = new ArrayList<Imagepxh>();//图片的uri
        myAdapter = new MyAdapter(MainActivity.this, Imagepxhlist);
        gridview_01.setAdapter(myAdapter);// gridview适配。
//        start_in_tv.setOnClickListener();
    }

    private void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                myAdapter.addAllPath(pathList);// list.add(); //记录了uri
                myAdapter.notifyDataSetChanged();
                Log.e("zj", pathList.get(0));

            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
        if (requestCode == 13 && resultCode == 13) {//简图获取图片
            if (data == null) {
                return;
            }
            String path = (String) data.getCharSequenceExtra("path");
            if (!StringUtils.isNullOrEmpty(path)) {
                Uri uri1 = Uri.fromFile(new File(path));
                myAdapter.clear();
                myAdapter.addList(uri1);// list.add(); //记录了uri
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Imagepxh> list;// this image file uri
        private ImageView image;
        private TextView tv_delete;

        public MyAdapter(Context context, List<Imagepxh> list) {
            this.context = context;
            this.list = list;
        }

        public void clear() {
            list.clear();
        }

        @Override
        public int getCount() {
            return list.size() + 1;
        }

        public void addAllPath(List<String> paths) {
            for (String path : paths) {
                Uri uri1 = Uri.fromFile(new File(path));
                galleryAddPic(uri1);
                Imagepxh image = new Imagepxh(uri1, path);
                if (!list.contains(image)) {
                    list.add(image);
                } else {
                    Toast.makeText(MainActivity.this, "图片已选过", Toast.LENGTH_LONG).show();
                }
            }

        }

        public List<Imagepxh> getList() {
            return this.list;
        }

        public void addList(Uri uri) {
            String imagePathFromUri = BitmapUtils.getImagePathFromUri(MainActivity.this, uri);
            Imagepxh image = new Imagepxh(uri, imagePathFromUri);
            if (!list.contains(image)) {
                list.add(image);
            } else {
                Toast.makeText(MainActivity.this, "图片已选过", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.gridview_item, null);
//            image = (ImageView) convertView.findViewById(R.id.image);
            image = (ImageView) convertView.findViewById(R.id.image);
            tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            if (position < list.size()) {
//                image.setImageURI(list.get(position).getImage_uri());
                Uri uri = list.get(position).getImage_uri();
                if (uri != null) {
                    String imagePathFromUri = BitmapUtils.getImagePathFromUri(context, uri);//由uri获得图片路径
                    //压缩图片
                    Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(imagePathFromUri, 80, 80);
                    image.setImageBitmap(bitmap);
                    image.setTag(position);//实现再次点击时查看大图
                }
            } else if (position == list.size()) {
                image.setImageDrawable(getResources().getDrawable(
                        R.mipmap.add_photo));
                tv_delete.setVisibility(View.INVISIBLE);
            }

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        if (v.getTag().equals(position)) {
                            Uri uri = list.get(position).getImage_uri();
                            String path = list.get(position).getPath();
                            if (uri != null) {
//                                Log.e("zj", position + "看大图" + uri.toString());
                                Intent intent = new Intent(MainActivity.this, AddPaintActivity.class);
                                intent.putExtra("from", "add_picture");
                                intent.putExtra("uri", uri.toString());
                                intent.putExtra("path", path);
                                startActivityForResult(intent, 13);
                                return;
                            }
                        }
                    }
                    Album.startAlbum(MainActivity.this, 100
                            , 9                                                         // 指定选择数量。
                            , ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                            , ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
                }
            });
            return convertView;
        }

    }

    //将图片添加进手机相册
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        this.sendBroadcast(mediaScanIntent);
    }
}
