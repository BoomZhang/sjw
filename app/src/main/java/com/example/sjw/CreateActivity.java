package com.example.sjw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
  private OtherGridView mGvPictures;
  private TextView mTvMusic;
  private ArrayList<ImageBean> datas;
  private CreateGvAdpater adapter;

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
      ArrayList<ImageBean> list = (ArrayList<ImageBean>) data.getSerializableExtra(AlbumActivity.ARG_DATA);
      adapter = new CreateGvAdpater(this,list);
      mGvPictures.setAdapter(adapter);
      //datas.addAll(list);
      //adapter.setData(list);
      //adapter.notify();
      //Toast.makeText(this, getString(R.string.picture_select_num, mImageBeen.size()), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.look_for_picture_bt){
      picMultiSelect();
    }else if(v.getId() == R.id.choose_music_bt){
      Intent intent = new Intent(CreateActivity.this,MusicChooseActivity.class);
      startActivity(intent);
    }

  }
}
