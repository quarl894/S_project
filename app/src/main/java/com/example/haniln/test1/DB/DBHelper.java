package com.example.haniln.test1.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.haniln.test1.item;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hospital";
    private static final String TABLE_erp = "tb_erp";

    private static final String CREATE_TABLE_erp = "create table " + TABLE_erp + "(_index INTEGER PRIMARY KEY AUTOINCREMENT, chk Integer, code String, gear String, name String);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 필드 : chk Integer, code String, gear String, name String
        db.execSQL(CREATE_TABLE_erp);
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_erp);
    }

    public void insert(int chk, String code, String gear, String name) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO " + TABLE_erp +
                " VALUES(NULL, + '" + chk + "','"+ code + "', '" + gear + "', '" + name + "');");
        db.close();
    }

    //바꿔줘야 할 곳.
    public void update(String code, String chk) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE " + TABLE_erp +
                " SET chk=" + chk + " WHERE code='" + code + "';");
        db.close();
    }

    public void delete(String code) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM " + TABLE_erp +
                " WHERE code='" + code + "';");
        db.close();
    }
    public void db_clear(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_erp, null, null);
    }

    //출력 테스트
    public ArrayList<item> get_item(){
        SQLiteDatabase db = getReadableDatabase();
        String CREATE_tb = "create table if not exists "
                + TABLE_erp + "(_index INTEGER PRIMARY KEY AUTOINCREMENT, chk Integer, code String, gear String, name String);";
        db.execSQL(CREATE_tb);

        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_erp, null);
        if(cursor.getCount() ==0){
            Log.e("get_item: ", "0");
            return null;
        }else{
            ArrayList<item> arr = new ArrayList<>();
            while(cursor.moveToNext()){
                arr.add(new item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            cursor.close();
            db.close();
            return arr;
        }
    }

    // 나중에 바꿀 곳. 출력하는 형식.
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_erp +
                "", null);
        while (cursor.moveToNext()) {
            result += cursor.getInt(0)
                    + "  "
                    + cursor.getString(1)
                    + "  "
                    + cursor.getString(2)
                    + " "
                    + cursor.getString(3)
                    + "\n";
        }
        return result;
    }
}

