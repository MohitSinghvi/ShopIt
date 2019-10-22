package com.example.shopit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Electra.db";
    public static final String TABLE_NAME1="Users";
    public static final String TABLE_NAME2="products";
    public static final String TABLE_NAME3="orders";

    public static final String COLUMN1="user_id";
    public static final String COLUMN2="name";
    public static final String COLUMN3="username";
    public static final String COLUMN4="password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, 3);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME1 +"(user_id Integer PRIMARY KEY AUTOINCREMENT, name Text,username Text, password Text)");
        db.execSQL("create table "+ TABLE_NAME2 + "(prod_id Integer PRIMARY KEY AUTOINCREMENT, prod_name Text, prod_brand Text,prod_category Text, prod_image blob, prod_description text,prod_price Integer)");
        db.execSQL("create table "+ TABLE_NAME3+"(order_id Integer PRIMARY KEY AUTOINCREMENT, username Text, prod_id Integer,location Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        onCreate(db);
    }

    public boolean insertUser(String name,String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();



        contentValues.put(COLUMN2,name);
        contentValues.put(COLUMN3,username);
        contentValues.put(COLUMN4,password);

        long result = db.insert(TABLE_NAME1,null,contentValues);

        if(result==-1){

            return false;
        }
        else {
            return true;
        }
    }

    public boolean isUserRegistered(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select username,password from "+ TABLE_NAME +" where username= "+username+" and password = "+ password,null);
        Cursor res = db.rawQuery("select username,password from "+ TABLE_NAME1 +" where username = ? and password = ?",new String[] {username,password});
//        SignupPage sp = new SignupPage();
//        sp.showMessage("hi","HI");

        return(res.getCount()!=0);

    }
    public boolean insertProduct(String prod_name, String prod_brand, String prod_category, String prod_description, byte[] prod_image, int product_price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prod_name",prod_name);
        contentValues.put("prod_brand",prod_brand);
        contentValues.put("prod_category",prod_category);
        contentValues.put("prod_description",prod_description);
        contentValues.put("prod_image",prod_image);
        contentValues.put("prod_price",product_price);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        if(result==-1){

            return false;
        }
        else {
            return true;
        }


    }

    public Cursor ShowProducts(String query,String search){

        SQLiteDatabase db = this.getWritableDatabase();
        if (search!=""){
            Cursor res=db.rawQuery(query,null);
            return res;
        }

        Cursor res=db.rawQuery(query,null);
        return res;
    }

    public boolean insertOrder(String username, int prod_id,String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("prod_id",prod_id);
        contentValues.put("location",location);
        long result = db.insert(TABLE_NAME3,null,contentValues);
        if(result==-1){

            return false;
        }
        else {
            return true;
        }


    }







}
