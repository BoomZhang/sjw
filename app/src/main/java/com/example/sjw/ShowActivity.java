package com.example.sjw;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.List;

/**
 * 创建时间：2019/4/28
 * 作者：sjw
 * 描述：
 */
public class ShowActivity extends AppCompatActivity {

  private ViewPager mViewPager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_layout);
    initView();
  }

  private void initView() {
    mViewPager = (ViewPager) findViewById(R.id.viewpager);
  }

  public static class IvAdapter extends BaseAdapter {

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
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_show_image_item,null);
      }
      ImageView iv = (ImageView)convertView.findViewById(R.id.show_picture);
      CreateGvAdpater.MyTask task = new CreateGvAdpater.MyTask();
      task.setmIv(iv);
      task.execute(data.get(position));
      return null;
    }
  }
}
