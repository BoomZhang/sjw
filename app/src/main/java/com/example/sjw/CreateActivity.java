package com.example.sjw;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ysq.album.activity.AlbumActivity;
import com.ysq.album.bean.ImageBean;
import java.util.ArrayList;

/**
 * 创建时间：2019/4/26
 * 作者：sjw
 * 描述：
 */
public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

  private static final int INTENT_CODE = 1;
  private Button mBtChoosePicture;
  private Button mBtChooseMusic;
  private Button mBtSave;
  ArrayList<ImageBean> list;
  private OtherGridView mGvPictures;
  private TextView mTvMusic;
  private ArrayList<ImageBean> datas;
  private CreateGvAdpater adapter;
  private int position;
  private EditText mEtName;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_layout);
    initView();
  }

  private void initView() {
    mBtChoosePicture = (Button) findViewById(R.id.look_for_picture_bt);
    mBtChoosePicture.setOnClickListener(this);
    mBtChooseMusic = (Button) findViewById(R.id.choose_music_bt);
    mBtChooseMusic.setOnClickListener(this);
    mBtSave = (Button) findViewById(R.id.save_bt);
    mBtSave.setOnClickListener(this);
    mEtName = (EditText) findViewById(R.id.name_input_et);
    datas = new ArrayList<ImageBean>();
    mGvPictures = (OtherGridView) findViewById(R.id.pictures_gridview);
    //adapter = new CreateGvAdpater(this,datas);
    //mGvPictures.setAdapter(adapter);

    mTvMusic = (TextView) findViewById(R.id.choosen_music);
  }

  public void picMultiSelect() {
    Intent intent = new Intent(CreateActivity.this, AlbumActivity.class);
    intent.putExtra(AlbumActivity.ARG_MODE, AlbumActivity.MODE_MULTI_SELECT);
    intent.putExtra(AlbumActivity.ARG_MAX_COUNT, 8);
    Bundle bundle = new Bundle();
    bundle.putSerializable(AlbumActivity.ARG_DATA, datas);
    intent.putExtras(bundle);
    startActivityForResult(intent, 1001);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1001 && resultCode == RESULT_OK) {
      list = (ArrayList<ImageBean>) data.getSerializableExtra(AlbumActivity.ARG_DATA);
      adapter = new CreateGvAdpater(this,list);
      mGvPictures.setAdapter(adapter);

    }else if(requestCode == 102 && resultCode == RESULT_OK){
      position = data.getIntExtra("position", -1);
      mTvMusic.setText("已选择《" + MusicList.names[position]+"》作为背景音乐");
    }
  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.look_for_picture_bt){
      picMultiSelect();
    }else if(v.getId() == R.id.choose_music_bt){
      Intent intent = new Intent(CreateActivity.this,MusicChooseActivity.class);
      startActivityForResult(intent, 102);
    }else if(v.getId() == R.id.save_bt){
      save();
    }
  }

  private void save(){
    String name = mEtName.getText().toString();
    if(name == null || "".equals(name)){
      return;
    }
    String index = DateUtil.getTime();
    String sqlVi = "INSERT INTO vi(name,backgroudID,dex) VALUES('" + name + "','" + position +"','"+ index +"')";
    SQLiteDatabase db = DbManger.getIntance(this).getWritableDatabase();
    DbManger.execSQL(db,sqlVi);

    for(int i = 0; i < datas.size(); i++){
      String path = datas.get(i).getImage_path();
      String sqlPi = "INSERT INTO pi VALUES ('"+ index +"','"+ path +"')";
      db.execSQL(sqlPi);
    }
    this.finish();
  }


}
