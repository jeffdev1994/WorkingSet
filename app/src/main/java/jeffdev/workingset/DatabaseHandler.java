package jeffdev.workingset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 2015-02-14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, "Workingset DB", null, 1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISE_TABLE = "CREATE TABLE exercise(name TEXT primary key,description TEXT)";
        String CREATE_MAKE_UP_TABLE = "CREATE TABLE makeup(Ename TEXT,Wname TEXT)";
        String CREATE_SET_TABLE = "CREATE TABLE doesset(Ename TEXT,Wname TEXT,date TEXT,reps INTEGER, weight REAL)";
        String CREATE_WORKOUT_TABLE = "CREATE TABLE workout(name TEXT,date TEXT, length TEXT, PRIMARY KEY(name,date))";

        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_MAKE_UP_TABLE);
        db.execSQL(CREATE_SET_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
    }


    // Upgrading database - need to look into this, might need to make seperate classes for each table..
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
//
//        // Create tables again
//        onCreate(db);
    }

    public void addExercise(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        db.insert("exercise",null,values);
        db.close();
    }

    public List<String[]> getExercise(String search){
        List<String[]> exerciselist = new ArrayList<String[]>();

        //if it is null, return the whole list
        if (search.equals("all")){
            String query = "select * from exercise";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            String[] exercise = new String[2];

            if(cursor.moveToFirst()){
                do{
                    exercise[0] = cursor.getString(0);
                    exercise[1] = cursor.getString(1);

                    exerciselist.add(exercise);
                }while(cursor.moveToNext());
            }
        }
        //need to fill this in, it will be for searching.
        else{

        }
        return exerciselist;
    }
}
