package com.example.sjw;

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
  private IvAdapter adapter;


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
    mViewPager = (ViewPager) findViewById(R.id.viewpager);
    adapter = new IvAdapter(createViews(paths));
    mViewPager.setAdapter(adapter);
    mViewPager.setPageTransformer(true,new DepthPageTransformer());
    mViewPager.setCurrentItem(0);

    new Thread(new Runnable() {
      @Override
      public void run() {
        while(true) {
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % paths.size());
            }
          });
        }
      }
    }).start();

    BackgroundMusic.getInstance(this).playBackgroundMusic(MusicList.ids[backId],true);
  }

  @Override
  protected void onStop() {
    super.onStop();
    BackgroundMusic.getInstance(this).end();
  }

  private List<View> createViews(List<String> paths){
    List<View> views = new ArrayList<View>();
    for(int i=0;i<paths.size();i++){
      View view = LayoutInflater.from(this).inflate(R.layout.activity_show_image_item,null);
      ImageView im = (ImageView) view.findViewById(R.id.show_picture);
      //im.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
      CreateGvAdpater.MyTask task = new CreateGvAdpater.MyTask();
      task.setmIv(im);
      task.execute(paths.get(i));
      views.add(view);
    }
    return views;
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

    private List<View> data;

    public IvAdapter(List<View> data){
      this.data = data;
    }

    @Override
    public int getCount() {
      return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      container.addView(data.get(position));
      return data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(data.get(position));
    }
  }
}
