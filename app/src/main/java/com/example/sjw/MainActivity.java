package com.example.sjw;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
  }

  private void initView() {
    mLv = (ListView) findViewById(R.id.main_lv);
    mLv.setAdapter(createAdapter());
    mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("sjw",data.get(position).name);
        Log.i("sjw",data.get(position).backMusicID+"");
      }
    });
    mBtGuide = (Button) findViewById(R.id.look_up_guide_bt);
    mBtGuide.setOnClickListener(this);
    mBtCreate = (Button) findViewById(R.id.create_music_ablum_bt);
    mBtCreate.setOnClickListener(this);
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
    SQLiteDatabase db = DbManger.getIntance(this).getWritableDatabase();
    String all = "select * from vi";
    Cursor cursor =  DbManger.selectSQL(db,all,null);
    return DbManger.toList(cursor);
  }


}
