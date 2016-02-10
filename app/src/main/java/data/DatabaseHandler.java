package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Item;

/**
 * Created by DK on 1/6/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    private final ArrayList<Item> itemList=new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table sql
        String CREATE_TABLE="CREATE TABLE "+Constants.TABLE_NAME+"("
                +Constants.KEY_ID+" INTEGER PRIMARY KEY, "+Constants.ITEM_NAME
                +" TEXT, "+Constants.ITEM_COST_NAME+" INT, "+Constants.DATE_NAME+" LONG);";

        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);

        //create new
        onCreate(db);
    }


    //get total number of items saved
    public int getTotalItems()
    {
        int totalItems=0;
        String query="SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase dba=this.getReadableDatabase();

        //stores database rows
        Cursor cursor=dba.rawQuery(query,null);

        //gives total number of rows/items
        totalItems=cursor.getCount();
        cursor.close();

        return totalItems;
    }

    public int totalDollars()
    {
        int dolls=0;
        SQLiteDatabase dba=this.getReadableDatabase();

        //gives total cost
        String query="SELECT SUM( "+Constants.ITEM_COST_NAME+" ) "+
                "FROM "+Constants.TABLE_NAME;

        //query,args
        Cursor cursor=dba.rawQuery(query,null);

        //if there is something with the cursor point to it and read it
        if(cursor.moveToFirst())
        {   //arg=column
            dolls=cursor.getInt(0);
        }
        cursor.close();
        dba.close();

        return dolls;
    }
    public  void deleteItem(int id)
    {
        SQLiteDatabase dba=this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        dba.close();
    }
    public void addItem(Item item)
    {
        SQLiteDatabase dba=this.getWritableDatabase();

        ContentValues val=new ContentValues();
        val.put(Constants.ITEM_NAME,item.getItemName());
        val.put(Constants.ITEM_COST_NAME,item.getDollars());
        val.put(Constants.DATE_NAME, System.currentTimeMillis());

        dba.insert(Constants.TABLE_NAME, null, val);

        Log.v("Added Item ", "Yo...");
        dba.close();

    }

    //arraylist of items
    public ArrayList<Item> getItems()
    {
        itemList.clear();
        SQLiteDatabase dba=this.getReadableDatabase();
        Cursor cursor=dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,Constants.ITEM_NAME,Constants.ITEM_COST_NAME,
                        Constants.DATE_NAME},null,null,null,null,Constants.DATE_NAME+" DESC ");

        if (cursor.moveToFirst())
        {
            do {
                Item item=new Item();
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME)));
                item.setDollars(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_COST_NAME)));
                item.setItemID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));


                DateFormat df=DateFormat.getDateInstance();
                String date=df.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                item.setRecordDate(date);

                itemList.add(item);

            }while(cursor.moveToNext());


        }

        cursor.close();
        dba.close();
        return itemList;
    }
}
