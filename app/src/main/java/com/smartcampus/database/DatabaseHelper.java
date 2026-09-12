package com.smartcampus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * PRACTICAL 8: SQLite Database Helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "SmartCampus.db";
    private static final int    DB_VERSION = 1;

    public static final String TABLE_STUDENTS = "students";
    public static final String COL_ID         = "id";
    public static final String COL_ROLL       = "roll_number";
    public static final String COL_NAME       = "name";
    public static final String COL_MARKS      = "marks";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENTS + " ("
                + COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ROLL  + " TEXT NOT NULL UNIQUE, "
                + COL_NAME  + " TEXT NOT NULL, "
                + COL_MARKS + " REAL NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    /** Insert a student record. Returns row id or -1 on failure. */
    public long insertStudent(String roll, String name, double marks) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ROLL,  roll);
        cv.put(COL_NAME,  name);
        cv.put(COL_MARKS, marks);
        long result = db.insert(TABLE_STUDENTS, null, cv);
        db.close();
        return result;
    }

    /** Returns all student rows as a list of String arrays [roll, name, marks]. */
    public List<String[]> getAllStudents() {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_ROLL + "," + COL_NAME + "," + COL_MARKS
                + " FROM " + TABLE_STUDENTS + " ORDER BY " + COL_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new String[]{
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
                });
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
