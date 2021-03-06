package com.example.sjw;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 创建时间：2019/4/26
 * 作者：sjw
 * 描述：主界面的ListView的Adapter
 */
public class MainLvAdapter extends BaseAdapter {

  private List<ViBean> data;
  private Context context;

  public MainLvAdapter(Context context, List<ViBean> data){
    this.data = data;
    this.context = context;
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
    ViewHolder viewHolder;
    ViBean bean = data.get(position);

    if(convertView == null){
      convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_lv_item,null);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    }else{
      viewHolder = (ViewHolder)convertView.getTag();
    }

    viewHolder.mTvTitle.setText(bean.name);
    try {
      viewHolder.mTvDescrip.setText(new String(Base64.decode(bean.index.getBytes("UTF-8"),1)));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    CreateGvAdpater.MyTask task = new CreateGvAdpater.MyTask();
    task.setmIv(viewHolder.mImView);
    task.execute(bean.path);

    return convertView;
  }

  public final class ViewHolder{

    public TextView mTvTitle;
    public TextView mTvDescrip;
    public ImageView mImView;

    public ViewHolder(View view){
      mTvTitle = (TextView) view.findViewById(R.id.title);
      mTvDescrip = (TextView) view.findViewById(R.id.descrip);
      mImView = (ImageView) view.findViewById(R.id.image);
    }

  }

}
