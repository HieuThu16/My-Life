package com.example.imhieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "skills.db";
    private static final int DATABASE_VERSION = 2; // Incremented version


    // Categories Table
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    // Skills Table
    private static final String TABLE_SKILLS = "skills";
    private static final String COLUMN_SKILL_ID = "id";
    private static final String COLUMN_SKILL_NAME = "name";
    private static final String COLUMN_SKILL_SCORE = "score";
    private static final String COLUMN_SKILL_MAX_POINTS = "max_points";
    private static final String COLUMN_SKILL_DESCRIPTION = "description";
    private static final String COLUMN_SKILL_CATEGORY_ID = "category_id"; // foreign key reference to category

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        // Create Categories Table
        String createCategoriesTable = "CREATE TABLE " + TABLE_CATEGORIES + " ("
                + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CATEGORY_NAME + " TEXT)";
        db.execSQL(createCategoriesTable);

        // Create Skills Table
        String createSkillsTable = "CREATE TABLE " + TABLE_SKILLS + " ("
                + COLUMN_SKILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SKILL_NAME + " TEXT, "
                + COLUMN_SKILL_SCORE + " INTEGER, "
                + COLUMN_SKILL_MAX_POINTS + " INTEGER, "
                + COLUMN_SKILL_DESCRIPTION + " TEXT, "
                + COLUMN_SKILL_CATEGORY_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_SKILL_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + " (" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(createSkillsTable);
    }


    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLS); // Drop skills table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES); // Drop categories table
        onCreate(db); // Recreate the tables
    }


    // Add a new category to the database
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, category.getName());
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    // Get all categories from the database
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME));
                categories.add(new Category(id, name, new ArrayList<>())); // Assuming Category constructor needs this
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    // Add a skill to a specific category
// Add a skill to a specific category
    public void addSkillToCategory(int categoryId, Skill skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SKILL_NAME, skill.getName());
        values.put(COLUMN_SKILL_SCORE, skill.getPoints());
        values.put(COLUMN_SKILL_MAX_POINTS, skill.getMaxPoints());
        values.put(COLUMN_SKILL_DESCRIPTION, skill.getDescription());
        values.put(COLUMN_SKILL_CATEGORY_ID, categoryId); // Link the skill to the category
        db.insert(TABLE_SKILLS, null, values);
        db.close();
    }
    // Get all skills for a specific category
    public List<Skill> getSkillsForCategory(int categoryId) {
        List<Skill> skills = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SKILLS, null, COLUMN_SKILL_CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SKILL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL_NAME));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SKILL_SCORE));
                int maxPoints = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SKILL_MAX_POINTS));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL_DESCRIPTION));

                // Fetch the Category object for this skill
                Category category = getCategoryById(categoryId); // Create this method to get a Category object by ID

                skills.add(new Skill(id, name, score, maxPoints, description, category));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return skills;
    }


    // Generate a unique ID for a new category
    public int generateCategoryId() {
        int newId = 1; // Default ID if no categories exist
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to find the maximum ID in the categories table
        String query = "SELECT MAX(" + COLUMN_CATEGORY_ID + ") FROM " + TABLE_CATEGORIES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int maxId = cursor.getInt(0); // Get the maximum ID
            newId = maxId + 1;           // Increment for the new ID
        }

        cursor.close(); // Close the cursor to prevent memory leaks
        db.close();     // Close the database connection

        return newId;
    }

    // Generate a unique ID for a new skill
    public int generateSkillId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_SKILL_ID + ") FROM " + TABLE_SKILLS, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0); // Get the max ID
            cursor.close();
            return id + 1; // Return the next ID (auto-increment)
        }

        cursor.close();
        return 1; // Return 1 if the table is empty (first skill)
    }



    public Category getCategoryById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, null, COLUMN_CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)}, null, null, null);

        Category category = null;
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME));
            category = new Category(categoryId, name, new ArrayList<>());
        }
        cursor.close();
        db.close();
        return category;
    }
    public void updateSkillPoints(int skillId, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", points); // Giả sử cột lưu điểm là "points"

        db.update("skills", values, "id = ?", new String[]{String.valueOf(skillId)});
        db.close();
    }

}
