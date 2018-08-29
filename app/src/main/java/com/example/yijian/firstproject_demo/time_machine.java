package com.example.yijian.firstproject_demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hiai.vision.common.ConnectionCallback;
import com.huawei.hiai.vision.common.VisionBase;
import com.huawei.hiai.vision.face.FaceComparator;
import com.huawei.hiai.vision.visionkit.common.Frame;
import com.huawei.hiai.vision.visionkit.face.FaceCompareResult;

import org.json.JSONObject;

public class time_machine extends AppCompatActivity implements View.OnClickListener {

    private static final int TYPE_SHOW_RESULE =12 ;
    private Button btn_select;
    private Button btn_compare;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private String TAG = "信息提示";
    private static final int TYPE_CHOOSE_PHOTO_CODE4PERSON1 = 10;
    private static final int TYPE_CHOOSE_PHOTO_CODE4PERSON2 = 11;
    private boolean isPerson1 = false;
    private Bitmap mBitmapPerson1;
    private Bitmap mBitmapPerson2;
    private Button compare_button;
    private TextView end;
    FaceComparator mFaceComparator;
    //线程锁对象
    private Object mWaitResult = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_machine);
        btn_select = ((Button) findViewById(R.id.btn_select));
        btn_compare = ((Button) findViewById(R.id.btn_compare));
        compare_button = ((Button) findViewById(R.id.compare));
        end = ((TextView) findViewById(R.id.compare_end));
        btn_select.setOnClickListener(this);
        btn_compare.setOnClickListener(this);
        compare_button.setOnClickListener(this);
        //To connect vision service
        VisionBase.init(getApplicationContext(), new ConnectionCallback() {
            @Override
            public void onServiceConnect() {
                Log.i(TAG, "onServiceConnect ");
            }

            @Override
            public void onServiceDisconnect() {
                Log.i(TAG, "onServiceDisconnect");
            }
        });
        mThread.start();
        requestPermissions();
    }


    private void requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * @date: 2018/8/21
    * @author: yijian
    * @description:读取选择文件
    * @version:1.0
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }

            Uri selectedImage = data.getData();
            int type = data.getIntExtra("type", TYPE_CHOOSE_PHOTO_CODE4PERSON1);
            getBitmap(type, selectedImage);
        }
    }


    /*
    * @date: 2018/8/21
    * @author: yijian
    * @description:保存图片文件
    * @version:1.0
    */
    private void getBitmap(int type, Uri imageUri) {
        String[] pathColumn = {MediaStore.Images.Media.DATA};

        //从系统表中查询指定Uri对应的照片
        Cursor cursor = getContentResolver().query(imageUri, pathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(pathColumn[0]);
        String picturePath = cursor.getString(columnIndex);  //获取照片路径
        cursor.close();

        if (isPerson1) {
            mBitmapPerson1 = BitmapFactory.decodeFile(picturePath);
            mhander.sendEmptyMessage(TYPE_CHOOSE_PHOTO_CODE4PERSON1);

        } else {
            mBitmapPerson2 = BitmapFactory.decodeFile(picturePath);
            mhander.sendEmptyMessage(TYPE_CHOOSE_PHOTO_CODE4PERSON2);
        }
    }

    /*
    * @date: 2018/8/21
    * @author: yijian
    * @description:创建handle方法
    * @version:1.0
    */
    @SuppressLint("HandlerLeak")
    private Handler mhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int status = msg.what;
            switch (status) {
                case TYPE_CHOOSE_PHOTO_CODE4PERSON1:
                    btn_select.setBackgroundDrawable(new BitmapDrawable(mBitmapPerson1));
                    btn_select.setText("");
                    break;
                case TYPE_CHOOSE_PHOTO_CODE4PERSON2:
                    btn_compare.setBackgroundDrawable(new BitmapDrawable(mBitmapPerson2));
                    btn_compare.setText("");
                    break;
                case TYPE_SHOW_RESULE:
                    FaceCompareResult result = (FaceCompareResult) msg.obj;
                    Log.i(TAG, "handleMessage: "+result);
                    int socre= (int) result.getSocre()*1000;
                    float scre=(float) socre/10;
                    if(result==null){
                        Toast.makeText(getApplicationContext(),"服务器连接失败",Toast.LENGTH_SHORT).show();
                    }
                    if(result.isSamePerson()){
                        Toast.makeText(getApplicationContext(),"恭喜你是同一个人",Toast.LENGTH_SHORT).show();
                        end.setText("恭喜你是同一个人，两个人的相似性为："+scre+"%");
                    }else {
                        Toast.makeText(getApplicationContext(),"不是是同一个人",Toast.LENGTH_SHORT).show();
                        end.setText("不是是同一个人，两个人的相似性为："+scre+"%");
                    }

                    break;
            }
        }
    };


    /*
        * @date: 2018/8/20
        * @author: yijian
        * @description:监听点击事件
        * @version:1.0
        */
    @Override
    public void onClick(View v) {
        int requestCode;

        switch (v.getId()) {
            case R.id.btn_select: {
                Toast.makeText(getApplicationContext(), "选择图片", Toast.LENGTH_SHORT).show();
                isPerson1 = true;
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                requestCode = PHOTO_REQUEST_GALLERY;
                startActivityForResult(intent, requestCode);
                break;
            }
            case R.id.btn_compare: {
                Toast.makeText(getApplicationContext(), "对比图片", Toast.LENGTH_SHORT).show();
                isPerson1 = false;
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                requestCode = PHOTO_REQUEST_GALLERY;
                startActivityForResult(intent, requestCode);
                break;
            }
            case R.id.compare:{
                startCompare();
            }
        }




    }

    private void startCompare() {
        end.setText("正在为你检查两张图片的相似性");
        synchronized (mWaitResult) {
            mWaitResult.notifyAll();
        }
    }


    /*
    * @date: 2018/8/21
    * @author: yijian
    * @description:创建一个线程
    * @version:1.0
    */
    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            mFaceComparator = new FaceComparator(getApplicationContext());
            FaceComparator faceComparator = mFaceComparator;
            while (true) {
                try {
                    synchronized (mWaitResult) {
                        mWaitResult.wait();
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
                if (mBitmapPerson1!=null&&mBitmapPerson2!=null) {
                    Frame frame1 = new Frame();
                    Frame frame2 = new Frame();
                    frame1.setBitmap(mBitmapPerson1);
                    frame2.setBitmap(mBitmapPerson2);
                    JSONObject jsonObject = faceComparator.faceCompare(frame1, frame2, null);
                    if (jsonObject != null)
                        Log.d(TAG, "Compare end !!!!  json: " + jsonObject.toString());
                    FaceCompareResult result = faceComparator.convertResult(jsonObject);
                    Log.d(TAG, "convertResult end !!!! ");

                    Message msg = new Message();
                    msg.what = TYPE_SHOW_RESULE;
                    msg.obj = result;
                    mhander.sendMessage(msg);
                }

            }
        }
    });

}
