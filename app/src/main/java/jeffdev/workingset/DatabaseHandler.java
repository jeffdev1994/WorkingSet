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


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS exercise");
        db.execSQL("DROP TABLE IF EXISTS makeup");
        db.execSQL("DROP TABLE IF EXISTS doesset");
        db.execSQL("DROP TABLE IF EXISTS workout");

        // Create tables again
        onCreate(db);
    }
//for testing purposes, deletes the tables
    public void resettables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS exercise");
        db.execSQL("DROP TABLE IF EXISTS makeup");
        db.execSQL("DROP TABLE IF EXISTS doesset");
        db.execSQL("DROP TABLE IF EXISTS workout");

        onCreate(db);
    }

    public int addExercise(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        try {
            db.insertOrThrow("exercise", null, values);
        }catch(android.database.sqlite.SQLiteConstraintException e){
            return 1;
        }
        db.close();
        return 0;
    }

    public List<exerciseStorage> getExercise(String search){
        List<exerciseStorage> exerciselist = new ArrayList<exerciseStorage>();
        String query;
        //if it is null, return the whole list
//        if (search.equals("all")){
//            query = "select * from exercise";
//        }
        //need to fill this in, it will be for searching.
        query =  "Select * from exercise where name like '%" + search + "%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                exerciseStorage exercise = new exerciseStorage();
                exercise.setName(cursor.getString(0));
                exercise.setDescription(cursor.getString(1));

                exerciselist.add(exercise);
            }while(cursor.moveToNext());
        }
        return exerciselist;
    }
}
