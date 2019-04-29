package com.example.sjw;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2019/4/28
 * 作者：sjw
 * 描述：
 */
public class ShowActivity extends AppCompatActivity {

  private ViewPager mViewPager;
  private String index;
  private int backId;
  private List<String> paths;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_layout);
    initView();
  }

  private void initView() {
    backId = getIntent().getIntExtra("backId",0);
    index = getIntent().getStringExtra("index");
    paths = getPath(index);
    for(int i = 0;i < paths.size(); i++){
      Log.d("sjw1",paths.get(i));
    }
    Log.d("sjw1",backId + "");
    mViewPager = (ViewPager) findViewById(R.id.viewpager);

  }

  public List<String> getPath(String index){
    SQLiteDatabase db = DbManger.getIntance(this).getWritableDatabase();
    String sql = "select * from pi where dex1 = '" + index + "'";
    List<String> list = new ArrayList<String>();
    Cursor cursor = DbManger.selectSQL(db,sql,null);
    while(cursor.moveToNext()){
      list.add(cursor.getString(2));
    }
    return list;
  }

  public static class IvAdapter extends PagerAdapter {

    private List<String> data;
    private Context context;

    public IvAdapter(Context context, List<String> data){
      this.context = context;
      this.data = data;
    }

    @Override
    public int getCount() {
      return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      View view=LayoutInflater.from(context).inflate(R.layout.activity_show_image_item,null);
      CreateGvAdpater.MyTask task = new CreateGvAdpater.MyTask();
      ImageView iv = (ImageView) view.findViewById(R.id.show_picture);
      task.setmIv(iv);
      task.execute(data.get(position));
      container.addView(view);
      return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      //container.removeView(datas.get(position));
    }
  }
}
