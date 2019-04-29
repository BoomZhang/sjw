package com.example.sjw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "info.db";//数据库名称
  public static final int DATABASE_VERSION = 1;//数据库的版本号

  public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  public MySqliteHelper(Context context){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
  }

  //当数据库创建时，执行下面的函数
  @Override
  public void onCreate(SQLiteDatabase db) {
    //可以在这里写SQL语句，需要创建的表可以全部在这里先创建好
    String sqlCreateTableVi = "create table vi (id Integer primary key autoincrement,name varchar(50),backgroudID Integer,dex0 varchar(50))";
    String sqlCreateTablePi = "create table pi (id Integer primary key autoincrement,dex1 varchar(50), path varchar(100))";
    db.execSQL(sqlCreateTablePi);
    db.execSQL(sqlCreateTableVi);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

}
