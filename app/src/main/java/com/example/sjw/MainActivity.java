package com.example.sjw;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private ListView mLv;
  private Button mBtGuide;
  private Button mBtCreate;
  private List<ViBean> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mLv.setAdapter(createAdapter());
    sendNotification(data.size());
  }

  private void initView() {
    mLv = (ListView) findViewById(R.id.main_lv);
    mLv.setAdapter(createAdapter());
    mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this,ShowActivity.class);
        intent.putExtra("index",data.get(position).index);
        intent.putExtra("backId",data.get(position).backMusicID);
        startActivity(intent);
      }
    });
    mLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showAction(data.get(position).index);
        return true;
      }
    });
    mBtGuide = (Button) findViewById(R.id.look_up_guide_bt);
    mBtGuide.setOnClickListener(this);
    mBtCreate = (Button) findViewById(R.id.create_music_ablum_bt);
    mBtCreate.setOnClickListener(this);

  }

  private void showAction(final String dex) {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Select the action you want to perform");
    builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        //Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = DbManger.getIntance(getApplicationContext()).getWritableDatabase();
        DbManger.deleteOne(db,dex);
        mLv.setAdapter(createAdapter());
      }
    }).setNegativeButton("edit", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        //Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,CreateActivity.class);
        intent.putExtra("dex",dex);
        startActivity(intent);
      }
    }).show();

  }

  private BaseAdapter createAdapter(){
    data = createData();
    MainLvAdapter adapter = new MainLvAdapter(this,data);
    return adapter;
  }

  @Override
  public void onClick(View v) {

    if(v.getId() == R.id.look_up_guide_bt){
      Intent intent = new Intent(MainActivity.this,GuideActivity.class);
      startActivity(intent);
    }else if(v.getId() == R.id.create_music_ablum_bt) {
      Intent intent1 = new Intent(MainActivity.this, CreateActivity.class);
      startActivity(intent1);
    }
  }

  private List<ViBean> createData(){
    Uri uri = Uri.parse("content://com.example.sjw.mycontentprovider/get");
    ContentResolver resolver = getContentResolver();
    Cursor cursor =  resolver.query(uri,null,null,null,null);
    //Cursor cursor =  DbManger.selectSQL(db,all,null);
    return DbManger.toList(cursor);
  }

  private void sendNotification(int i) {

    NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(this);
    Notification notification = notificationCompatBuilder
        .setContentTitle(getString(R.string.app_name))
        .setContentText("ListView has " + i + " items")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setSmallIcon(R.mipmap.ic_launcher)
        .build();

    NotificationManagerCompat.from(getApplicationContext()).notify(1, notification);

  }


}
