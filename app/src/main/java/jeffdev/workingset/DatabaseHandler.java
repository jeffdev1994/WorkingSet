package jeffdev.workingset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        String CREATE_WORKOUT_TABLE = "CREATE TABLE workout(Name TEXT UNIQUE)";
        String CREATE_SET_TABLE = "CREATE TABLE doesset(Ename TEXT,Wname TEXT,date TEXT,reps INTEGER, weight REAL)";
        String CREATE_COMPLETED_WORKOUT_TABLE = "CREATE TABLE completedworkout1(name TEXT,date TEXT, length TEXT, PRIMARY KEY(name,date))";

        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_MAKE_UP_TABLE);
        db.execSQL(CREATE_SET_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_COMPLETED_WORKOUT_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS exercise");
        db.execSQL("DROP TABLE IF EXISTS makeup");
        db.execSQL("DROP TABLE IF EXISTS workout");
        db.execSQL("DROP TABLE IF EXISTS doesset");
        db.execSQL("DROP TABLE IF EXISTS completedworkout1");

        // Create tables again
        onCreate(db);
    }

    public void test(){
        exerciseStorage exercise = new exerciseStorage();
        String query = "Select * from workout";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.d("size",String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        for(int i=0; i < cursor.getCount(); i++) {
            Log.d("workout", cursor.getString(0));
            cursor.moveToNext();
        }
    }

    //return 0 if successful, 1 if its already there
    public int addworkout(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        try{
            db.insertOrThrow("workout",null,values);
        }catch(android.database.sqlite.SQLiteConstraintException e){
            db.close();
            return 1;
        }
        db.close();
        return 0;
    }

    //if performance is an issue, turn Ename into an array and do it all with one writable db
    //return 0 if successful, 1 if its already there
    public int addmakeup(String Ename, String Wname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ename",Ename);
        values.put("Wname",Wname);
        try{
            db.insertOrThrow("makeup",null,values);
        }catch(android.database.sqlite.SQLiteConstraintException e){
            db.close();
            return 1;
        }
        db.close();
        return 0;
    }

    //return 0 if successful, 1 if its already there
    public int addExercise(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        try {
            db.insertOrThrow("exercise", null, values);
        }catch(Exception e){
            db.close();
            return 1;
        }
        db.close();
        return 0;
    }

    public int deleteWorkout(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from workout where name = '" + name + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        query = "Delete from makeup where Wname = '" + name + "'";
        cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){}
        }
        db.close();
        return 0;
    }

    public void deleteExercise(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from exercise where name = '" + name + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        db.close();

    }
    //return 0 if successful, 1 if its already there
    public int updateExercise(String oldname, String newname, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "Update exercise set name = " + newname + ", description = " + description + " where name = '" + oldname+"'";
        //Cursor cursor = db.rawQuery(query,null);
        //cursor.moveToFirst();
        ContentValues values = new ContentValues();
        values.put("name",newname);
        values.put("description", description);
        try {
            db.updateWithOnConflict("exercise", values, "name = ?", new String[]{oldname}, SQLiteDatabase.CONFLICT_FAIL);
        }catch(Exception e){
            return 1;
        }
        db.close();
        return 0;
    }

    public exerciseStorage getSingleExercise(String name){
        exerciseStorage exercise = new exerciseStorage();
        String query = "Select * from exercise where name = '" + name +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        exercise.setName(cursor.getString(0));
        exercise.setDescription(cursor.getString(1));
        db.close();
        return exercise;
    }

    public List<makeupStorage> getMakeup(String name){
        List<makeupStorage> makeuplist = new ArrayList<makeupStorage>();
        String query = "Select * from makeup where Wname = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                makeupStorage makeup = new makeupStorage();
                makeup.Ename = cursor.getString(0);
                makeup.Wname = cursor.getString(1);

                makeuplist.add(makeup);
            }while(cursor.moveToPrevious());
        }
        db.close();
        return makeuplist;
    }

    //was easier to do this, then adjust abunch of code when i needed the list to be serializable
    public ArrayList<makeupStorage> getMakeup_list(String name){
        ArrayList<makeupStorage> makeuplist = new ArrayList<makeupStorage>();
        String query = "Select * from makeup where Wname = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                makeupStorage makeup = new makeupStorage();
                makeup.Ename = cursor.getString(0);
                makeup.Wname = cursor.getString(1);

                makeuplist.add(makeup);
            }while(cursor.moveToPrevious());
        }
        db.close();
        return makeuplist;
    }

    public List<String> getAllWorkout(){
        List<String> workoutlist = new ArrayList<String>();
        String query = "select * from workout";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        String workout;
        if(cursor.moveToLast()){
            do{
                 workout = cursor.getString(0);
                workoutlist.add(workout);
            }while(cursor.moveToPrevious());
        }
        db.close();
        return workoutlist;
    }

    public List<exerciseStorage> getExercise(String search){
        List<exerciseStorage> exerciselist = new ArrayList<exerciseStorage>();
        String query;
        query =  "Select * from exercise where name like '%" + search + "%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToLast()){
            do{
                exerciseStorage exercise = new exerciseStorage();
                exercise.setName(cursor.getString(0));
                exercise.setDescription(cursor.getString(1));

                exerciselist.add(exercise);
            }while(cursor.moveToPrevious());
        }
        db.close();
        return exerciselist;
    }

    public void startTransaction(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
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
}
