package com.example.sjw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private ListView mLv;
  private Button mBtGuide;
  private Button mBtCreate;
  private List<MainLvBean> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {
    mLv = (ListView) findViewById(R.id.main_lv);
    mLv.setAdapter(createAdapter());
    mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });
    mBtGuide = (Button) findViewById(R.id.look_up_guide_bt);
    mBtGuide.setOnClickListener(this);
    mBtCreate = (Button) findViewById(R.id.create_music_ablum_bt);
    mBtCreate.setOnClickListener(this);
  }

  private BaseAdapter createAdapter(){
    data = new ArrayList<MainLvBean>();
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

}
