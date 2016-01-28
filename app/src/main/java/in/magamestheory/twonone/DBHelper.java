package in.magamestheory.twonone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MilindTownOne.db";
    public static final String TABLE_NAME = "player";
    public static final String COLUMN_ID = "id";

    public static final String MSCORE = "mscore";
    public static final String GAMEBOARD = "gameboard";
    public static final String FB_ID = "fb_id";
    public static final String ME = "playerName";
    public static final String FRIENDS_LIST = "friendList";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "create table player " +
                        "(id integer primary key, playerName text,fb_id text,email text,friendlist text,mscore integer,gameboard text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS player");
        onCreate(db);
    }


    public boolean insertContact(String player_name, String fb_id,String emailId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playerName", player_name);
        contentValues.put("fb_id",fb_id);
        contentValues.put("friendlist","0");
        contentValues.put("email", emailId);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getMyData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from player where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateFriendsList(Integer id,String friendlist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("friendlist",friendlist);


        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateGameBoard(Integer id,String gameBoard){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("gameboard",gameBoard);


        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean isSignin(String fb_id){
        String[] tableColumns = new String[] {
                "playerName"
        };
        String whereClause = "fb_id = ?";
        String[] whereArgs = new String[] {
                fb_id
        };
        String orderBy = "column1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, tableColumns, whereClause, whereArgs,
                null, null, null);
        if(!c.isAfterLast()) return true;
        return false;
    }


    public boolean updateScore (Integer id, int score,String fb_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mscore",score);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }




   /* public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(FIRST_PLAYER)));
            res.moveToNext();
        }
        return array_list;
    }*/
}

