package com.example.sjw;

import android.content.Context;
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
import android.widget.Toast;

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
  private static boolean STARTING = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music_choose_layout);
    initView();
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
        mTvHasChoose.setText("已选择《" + MusicList.names[position]+"》");
        ID = MusicList.ids[position];
        mBtStart.setText(R.string.start);
        STARTING = false;
      }
    });
  }

  @Override
  public void onClick(View v) {

    if(v.getId() == R.id.start_and_stop_bt){
      if(!STARTING){
        Log.i("sjw","sss");
        BackgroundMusic.getInstance(this).playBackgroundMusic(ID,true);
        mBtStart.setText(R.string.stop);
        STARTING = true;
      }else{
        Log.i("sjw","ttt");
        BackgroundMusic.getInstance(this).end();
        mBtStart.setText(R.string.start);
        STARTING = false;
      }
    }
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
