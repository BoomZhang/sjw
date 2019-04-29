package com.example.sjw;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要对数据库操作的工具类
 */

public class DbManger {

    private static MySqliteHelper helper;
    //获得help类对象
    public static MySqliteHelper getIntance(Context context){
        if(helper == null){
            helper = new MySqliteHelper(context);
        }
        return helper;
    }

    /**
     * 根据SQL语句在数据库中执行语句
     * @param db 数据库对象
     * @param sql SQL语句
     */
    public static void execSQL(SQLiteDatabase db, String sql){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }

    /**
     * 对数据库的查询操作吗，获得一个Cursor对象
     * @param db
     * @param sql
     * @param selectionArges 查询条件的占位符
     * @return
     */
    public static Cursor selectSQL(SQLiteDatabase db, String sql, String[] selectionArges){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                return db.rawQuery(sql,selectionArges);
            }
        }
        return null;
    }

    public static List<ViBean> toList(Cursor cursor){
      List<ViBean> list = new ArrayList<>();
      while(cursor.moveToNext()){
        int columnIndex = cursor.getColumnIndex("id");
        list.add(new ViBean(cursor.getString(1),
            cursor.getInt(2),cursor.getString(3)));
      }
      return list;
    }
}
