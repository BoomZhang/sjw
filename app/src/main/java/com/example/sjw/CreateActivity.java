package com.example.sjw;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ysq.album.activity.AlbumActivity;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 创建时间：2019/4/26
 * 作者：sjw
 * 描述：
 */
public class CreateActivity extends Activity implements View.OnClickListener{

  private Button mBtChoosePicture;
  private Button mBtChooseMusic;
  private Button mBtSave;
  private OtherGridView mGvPictures;
  private TextView mTvMusic;
  private ArrayList<String> list = new ArrayList<String>();
  private CreateGvAdpater adapter;
  private int position = -1;
  private EditText mEtName;
  private String dex;
  private ArrayList<Uri> uris = new ArrayList<Uri>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_layout);
    initView();

    if(savedInstanceState != null){
      mEtName.setText(savedInstanceState.getString("name",""));
      position = savedInstanceState.getInt("position",-1);
      if(position != -1){
        mTvMusic.setText("Already selected \"" + MusicList.names[position]+"\"");
      }
      dex = savedInstanceState.getString("dex");
      if(dex != null){
        SQLiteDatabase db = DbManger.getIntance(this).getWritableDatabase();
        String sql = "select * from pi where dex1 = '" + dex + "'";
        Cursor cursor = DbManger.selectSQL(db,sql,null);
        while(cursor.moveToNext()){
          list.add(cursor.getString(2));
        }
        adapter.notifyDataSetChanged();
      }
    }

    Intent intent = getIntent();
    dex = intent.getStringExtra("dex");
    if(!(dex == null || "".equals(dex))){
      SQLiteDatabase db = DbManger.getIntance(this).getWritableDatabase();
      String sql = "select * from pi where dex1 = '" + dex + "'";
      Cursor cursor = DbManger.selectSQL(db,sql,null);
      while(cursor.moveToNext()){
        list.add(cursor.getString(2));
      }
      adapter.notifyDataSetChanged();

      String sql2 = "select * from vi where dex0 = '" + dex + "'";
      Cursor cursor2 = DbManger.selectSQL(db,sql2,null);
      while(cursor2.moveToNext()){
        mEtName.setText(cursor2.getString(1));
        position = cursor2.getInt(2);
        mTvMusic.setText("Already selected \"" + MusicList.names[position]+"\"");
      }
      cursor.close();
      cursor2.close();
    }

  }

  private void initView() {
    mBtChoosePicture = (Button) findViewById(R.id.look_for_picture_bt);
    mBtChoosePicture.setOnClickListener(this);
    mBtChooseMusic = (Button) findViewById(R.id.choose_music_bt);
    mBtChooseMusic.setOnClickListener(this);
    mBtSave = (Button) findViewById(R.id.save_bt);
    mBtSave.setOnClickListener(this);
    mEtName = (EditText) findViewById(R.id.name_input_et);
    //list = new ArrayList<ImageBean>();
    mGvPictures = (OtherGridView) findViewById(R.id.pictures_gridview);
    adapter = new CreateGvAdpater(this,list);
    mGvPictures.setAdapter(adapter);
    mGvPictures.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateActivity.this);
        builder.setMessage("Do you want to delete this photo?")
            .setPositiveButton("delete", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                adapter.notifyDataSetChanged();
              }
            }).setNegativeButton("cancle", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        }).show();
        return true;
      }
    });
    mGvPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Intent intent = new Intent(CreateActivity.this,ShareActivity.class);
        intent.putExtra("path",uris.get(position).toString());
        startActivity(intent);
      }
    });

    mTvMusic = (TextView) findViewById(R.id.choosen_music);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    String name = mEtName.getText().toString();
    if(!(name == null || "".equals(name))){
      outState.putString("name",name);
    }
    if(dex != null){
      outState.putSerializable("dex",dex);
    }
    if(position != -1){
      outState.putInt("position",position);
    }

    super.onSaveInstanceState(outState);
  }

  public void picMultiSelect() {
    Intent intent = new Intent(CreateActivity.this, AlbumActivity.class);
    intent.putExtra(AlbumActivity.ARG_MODE, AlbumActivity.MODE_MULTI_SELECT);
    intent.putExtra(AlbumActivity.ARG_MAX_COUNT, 8);
    Bundle bundle = new Bundle();
    bundle.putSerializable(AlbumActivity.ARG_DATA, list);
    intent.putExtras(bundle);
    startActivityForResult(intent, 1001);
  }

  public void picSingleSelect() {

    if (Build.VERSION.SDK_INT >= 23) {
      if (ContextCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(CreateActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
      }else{
        // 打开系统相册
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        startActivityForResult(intent, 202);
        return;
      }
    }

    // 打开系统相册
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");// 相片类型
    startActivityForResult(intent, 202);

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1001 && resultCode == RESULT_OK) {
      //list = (ArrayList<ImageBean>) data.getSerializableExtra(AlbumActivity.ARG_DATA);
      //adapter = new CreateGvAdpater(this,list);
      //mGvPictures.setAdapter(adapter);
    }else if(requestCode == 102 && resultCode == RESULT_OK){
      position = data.getIntExtra("position", -1);
      mTvMusic.setText("Already selected \"" + MusicList.names[position]+"\"");
    }else if(requestCode == 202 && resultCode == RESULT_OK){
      Uri uri = data.getData();
      uris.add(uri);
      String path = getRealFilePath(this,uri);
      //ImageBean imageBean = new ImageBean();
      //imageBean.setImage_name("za");
      //imageBean.setImage_path(path);
      list.add(path);
      adapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.look_for_picture_bt){
      //picMultiSelect();
      picSingleSelect();
    }else if(v.getId() == R.id.choose_music_bt){
      Intent intent = new Intent(CreateActivity.this,MusicChooseActivity.class);
      startActivityForResult(intent, 102);
    }else if(v.getId() == R.id.save_bt){
      save();
    }
  }

  private void save(){
    String name = mEtName.getText().toString();
    if(name == null || "".equals(name) || list.isEmpty() || position == -1){
      Toast.makeText(getApplicationContext(),"Can not create!!",Toast.LENGTH_SHORT).show();
      return;
    }
    String index = null;
    String time = DateUtil.getTime();
    try {
      index = new String(Base64.encode(time.getBytes("UTF-8"),1));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String sqlVi = "INSERT INTO vi(name,backgroudID,dex0,path) VALUES('" + name + "','" + position +"','"+ index +"','"+list.get(0)+"')";
    SQLiteDatabase db = DbManger.getIntance(this).getWritableDatabase();
    DbManger.execSQL(db,sqlVi);

    for(int i = 0; i < list.size(); i++){
      String path = list.get(i);//.getImage_path();
      String sqlPi = "INSERT INTO pi(dex1,path) VALUES('"+ index +"','"+ path +"')";
      db.execSQL(sqlPi);
    }

    if(!(dex == null || "".equals(dex))){
      //SQLiteDatabase db = DbManger.getIntance(getApplicationContext()).getWritableDatabase();
      DbManger.deleteOne(db,dex);
    }

    this.finish();
  }

  /** 根据Uri获取图片文件的绝对路径 */
  public static String getRealFilePath(Context context, Uri uri) {
    if (null == uri) {
      return null;
    }

    final String scheme = uri.getScheme();
    String data = null;
    if (scheme == null) {
      data = uri.getPath();
    } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
      data = uri.getPath();
    } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
      Cursor
          cursor = context.getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
      if (null != cursor) {
        if (cursor.moveToFirst()) {
          int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
          if (index > -1) {
            data = cursor.getString(index);
          }
        }
        cursor.close();
      }
    }
    return data;
  }

}
