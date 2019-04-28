package com.example.sjw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
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
    viewHolder.mTvDescrip.setText(bean.index);

    return convertView;
  }

  public final class ViewHolder{

    public TextView mTvTitle;
    public TextView mTvDescrip;

    public ViewHolder(View view){
      mTvTitle = view.findViewById(R.id.title);
      mTvDescrip = view.findViewById(R.id.descrip);
    }

  }

}
