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
    public static final String TABLE_NAME = "score";
    public static final String COLUMN_ID = "id";
    public static final String FIRST_PLAYER = "first_player";
    public static final String SECOND_PLAYER = "second_player";
    public static final String FIRST_PLAYER_SCORE = "first_player_score";
    public static final String SECOND_PLAYER_SCORE = "second_player_score";
    public static final String TIES = "ties";
    public static final String TURN = "turn";
    public static final String PAIR = "pairno";
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
                "create table score " +
                        "(id integer primary key, first_player text,first_player_score integer ,second_player text,second_player_score integer,ties integer,pairno integer)"
        );
        db.execSQL(
                "create table player " +
                        "(id integer primary key, playerName text,fb_id text,email text,friendlist text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS score");
        db.execSQL("DROP TABLE IF EXISTS player");
        onCreate(db);
    }

    public boolean insertPlayers(String first_player, String second_player,int pair_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_player", first_player);
        contentValues.put("second_player", second_player);
        contentValues.put("first_player_score",0);
        contentValues.put("second_player_score",0);
        contentValues.put("ties", 0);

        contentValues.put("pairno", pair_no);

        db.insert("score", null, contentValues);
        return true;
    }
    public boolean insertContact(String player_name, String fb_id,String emailId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playerName", player_name);
        contentValues.put("fb_id",fb_id);
        contentValues.put("friendlist","0");
        contentValues.put("email",emailId);
        db.insert("player", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from score where id="+id+"", null );
        return res;
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
    public boolean updatePlayer(Integer id,String first_player,String second_player,int turn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_player",first_player);
        contentValues.put("second_player",second_player);
        contentValues.put("first_player_score",0);
        contentValues.put("second_player_score",0);
        contentValues.put("ties",0);

        db.update("score", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateFriendsList(Integer id,String friendlist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("friendlist",friendlist);


        db.update("player", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
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
        Cursor c = db.query("table1", tableColumns, whereClause, whereArgs,
                null, null, null);
        if(!c.isAfterLast()) return true;
        return false;
    }
    public boolean updatepair (Integer id, int pair_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("pairno",pair_no);
        db.update("score", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateScore (Integer id, int first_player_score,int second_player_score,int ties)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_player_score",first_player_score);
        contentValues.put("second_player_score",second_player_score);
        contentValues.put("ties",ties);
        db.update("score", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
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
    }
}

