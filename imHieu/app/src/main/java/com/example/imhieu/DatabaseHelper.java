package com.example.imhieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SkillsDB";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_SKILLS = "skills";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_MAX_POINTS = "max_points";
    private static final String COLUMN_DESCRIPTION = "description";

    // Create table SQL query
    private static final String CREATE_TABLE_SKILLS =
            "CREATE TABLE " + TABLE_SKILLS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_POINTS + " INTEGER,"
                    + COLUMN_MAX_POINTS + " INTEGER DEFAULT 100,"
                    + COLUMN_DESCRIPTION + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SKILLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLS);
        onCreate(db);
    }

    // Add new skill
    public long addSkill(Skill skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, skill.getName());
        values.put(COLUMN_POINTS, skill.getPoints());
        values.put(COLUMN_MAX_POINTS, skill.getMaxPoints());
        values.put(COLUMN_DESCRIPTION, skill.getDescription()); // Add description

        long id = db.insert(TABLE_SKILLS, null, values);
        db.close();
        return id;
    }

    // Get single skill
    public Skill getSkill(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_SKILLS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_POINTS, COLUMN_MAX_POINTS, COLUMN_DESCRIPTION},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null)
            cursor.moveToFirst();

        Skill skill = new Skill(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_POINTS)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_MAX_POINTS)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)) // Get description
        );

        cursor.close();
        return skill;
    }

    // Get all skills
    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SKILLS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Skill skill = new Skill(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_POINTS)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_MAX_POINTS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)) // Get description
                );
                skills.add(skill);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return skills;
    }

    // Update skill
    public int updateSkill(Skill skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, skill.getName());
        values.put(COLUMN_POINTS, skill.getPoints());
        values.put(COLUMN_MAX_POINTS, skill.getMaxPoints());
        values.put(COLUMN_DESCRIPTION, skill.getDescription()); // Update description

        return db.update(
                TABLE_SKILLS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(skill.getId())}
        );
    }

    // Delete skill
    public void deleteSkill(Skill skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_SKILLS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(skill.getId())}
        );
        db.close();
    }
}
