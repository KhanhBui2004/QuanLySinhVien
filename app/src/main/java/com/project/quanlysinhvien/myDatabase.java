package com.project.quanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class myDatabase extends SQLiteOpenHelper {
    // Database Information
    private static final String DATABASE_NAME = "UniversityDSfnsldi_hídfh.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_STUDENT = "Student";
    private static final String TABLE_DEPARTMENT = "Department";

    // Common Columns
    private static final String COLUMN_ID = "id";

    // Student Table Columns
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_MIDDLE_NAME = "middleName";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_BIRTHDAY = "birthday";
    private static final String COLUMN_DEPARTMENT_ID = "department_id";


    // Department Table Columns
    private static final String COLUMN_DEPARTMENT_NAME = "name";
    private static final String COLUMN_DEPARTMENT_NOTE = "note";

    public myDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Department Table
        String CREATE_TABLE_DEPARTMENT = "CREATE TABLE " + TABLE_DEPARTMENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DEPARTMENT_NOTE + " TEXT NOT NULL, " +
                COLUMN_DEPARTMENT_NAME + " TEXT NOT NULL);";

        // Create Student Table
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                COLUMN_MIDDLE_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT, " +
//                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_BIRTHDAY + " TEXT, " + // Store as TEXT in YYYY-MM-DD format
                COLUMN_DEPARTMENT_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_DEPARTMENT_ID + ") REFERENCES " +
                TABLE_DEPARTMENT + "(" + COLUMN_ID + "));";


        // Execute the SQL statements
        db.execSQL(CREATE_TABLE_DEPARTMENT);
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTMENT);

        // Recreate tables
        onCreate(db);
    }

    // Add Student
    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_LAST_NAME, student.getLastName());
        values.put(COLUMN_MIDDLE_NAME, student.getMiddleName());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_PHONE_NUMBER, student.getPhoneNumber());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_BIRTHDAY, student.getBirthday());
        values.put(COLUMN_DEPARTMENT_ID, student.getDepartmentId());

        long result = db.insert(TABLE_STUDENT, null, values);
        db.close();
        return result; // Returns the row ID of the newly inserted row, or -1 if an error occurred
    }
    // Update Student
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_LAST_NAME, student.getLastName());
        values.put(COLUMN_MIDDLE_NAME, student.getMiddleName());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_PHONE_NUMBER, student.getPhoneNumber());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_BIRTHDAY, student.getBirthday());
        values.put(COLUMN_DEPARTMENT_ID, student.getDepartmentId());

        int rowsAffected = db.update(TABLE_STUDENT, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
        return rowsAffected; // Returns the number of rows affected
    }

    // Delete Student
    public int deleteStudent(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_STUDENT, COLUMN_ID + " = ?",
                new String[]{studentId});
        db.close();
        return rowsDeleted; // Returns the number of rows deleted
    }

    // Search Student by Name
    public List<Student> searchStudent(String key) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to search for matching names (case-insensitive search)
        String query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " +
                COLUMN_FIRST_NAME + " LIKE ? OR " +
                COLUMN_LAST_NAME + " LIKE ? OR " +
                COLUMN_MIDDLE_NAME + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{
                "%" + key + "%", // Match firstName
                "%" + key + "%", // Match lastName
                "%" + key + "%"  // Match middleName
        });

        // Iterate through results
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                student.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
                student.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)));
                student.setMiddleName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MIDDLE_NAME)));
                student.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                student.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
                student.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDAY)));
                student.setDepartmentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT_ID)));
                students.add(student);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        db.close();

        return students;
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENT;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                student.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
                student.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)));
                student.setMiddleName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MIDDLE_NAME)));
                student.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                student.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
                student.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDAY)));
                student.setDepartmentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT_ID)));

                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    // Retrieve students by department ID
    public List<Student> getStudentsInDept(int depId) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_DEPARTMENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(depId)});

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                student.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
                student.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)));
                student.setMiddleName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MIDDLE_NAME)));
                student.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                student.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
                student.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTHDAY)));
                student.setDepartmentId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT_ID)));

                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return students;
    }
}
