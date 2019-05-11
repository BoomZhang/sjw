package com.example.sjw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 创建时间：2019/4/28
 * 作者：sjw
 * 描述：
 */
public class MusicChooseActivity extends AppCompatActivity implements View.OnClickListener {

  private Button mBtStart;
  private Button mBtSave;
  private ListView mLvMusic;
  private MusicListAdapter adapter;
  private TextView mTvHasChoose;
  private int ID = -1;
  private int pos = -1;
  private static boolean STARTING = false;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music_choose_layout);
    initView();
    if(savedInstanceState != null){
      int position = savedInstanceState.getInt("position");
      mTvHasChoose.setVisibility(View.VISIBLE);
      mTvHasChoose.setText("Already selected \"" + MusicList.names[position]+"\"");
      pos = position;
      ID = MusicList.ids[position];
      mBtStart.setText(R.string.start);
      STARTING = false;
    }
  }

  private void initView() {
    mBtSave = (Button) findViewById(R.id.save_bt);
    mBtSave.setOnClickListener(this);
    mBtStart = (Button) findViewById(R.id.start_and_stop_bt);
    mBtStart.setOnClickListener(this);
    mTvHasChoose = (TextView) findViewById(R.id.music_choose_name_tv);
    mLvMusic = (ListView) findViewById(R.id.music_list);
    adapter = new MusicListAdapter(this);
    mLvMusic.setAdapter(adapter);
    mLvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTvHasChoose.setVisibility(View.VISIBLE);
        mTvHasChoose.setText("Already selected \"" + MusicList.names[position]+"\"");
        pos = position;
        ID = MusicList.ids[position];
        mBtStart.setText(R.string.start);
        STARTING = false;
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putInt("position",pos);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onClick(View v) {

    if(v.getId() == R.id.start_and_stop_bt){
      if(!STARTING){
        BackgroundMusic.getInstance(this).playBackgroundMusic(ID,true);
        mBtStart.setText(R.string.stop);
        STARTING = true;
      }else{
        BackgroundMusic.getInstance(this).end();
        mBtStart.setText(R.string.start);
        STARTING = false;
      }
    }else if(v.getId() == R.id.save_bt){
      Log.i("sjw","kkk");
      if(ID == -1){
        return;
      }
      Intent intent = new Intent(MusicChooseActivity.this,CreateActivity.class);
      intent.putExtra("position",pos);
      setResult(RESULT_OK,intent);
      this.finish();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    BackgroundMusic.getInstance(this).end();
  }

  public static class MusicListAdapter extends BaseAdapter {

    private Context context;

    public MusicListAdapter(Context context){
      this.context = context;
    }

    @Override
    public int getCount() {
      return MusicList.names.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
      if(convertView == null){
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_music_list_item,null);
      }
      ((TextView)(convertView.findViewById(R.id.music_name_tv))).setText(MusicList.names[position]);
      return convertView;
    }
  }
}
