package com.example.sjw;

import java.util.Calendar;

/**
 * 创建时间：2019/2/22
 * 作者：sjw
 * 描述：获取时间的工具类，进行一次封装
 */
public final class DateUtil {

  private static int year;
  private static int month;
  private static int day;
  private static int hour;
  private static int minute;
  private static int second;

  private static Calendar calendar = Calendar.getInstance();

  private static void update(){
    year = calendar.get(Calendar.YEAR);
    month = calendar.get(Calendar.MONTH);
    day = calendar.get(Calendar.DAY_OF_MONTH);
    hour = calendar.get(Calendar.HOUR_OF_DAY);
    minute = calendar.get(Calendar.MINUTE);
    second = calendar.get(Calendar.SECOND);
  }

  public static String getDate(){
    update();
    return year + "年" + month + "月" + day + "日";
  }

  public static String getTime(){
    update();
    return hour + ":" + minute + ":" + second;
  }

  public static String getInfo() {
    update();
    return "Calendar获取当前日期"+year+"年"+month+"月"+day+"日"+hour+":"+minute+":"+second;
  }
}
