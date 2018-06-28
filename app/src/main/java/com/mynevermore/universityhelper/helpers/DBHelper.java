package com.mynevermore.universityhelper.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

import com.mynevermore.universityhelper.model.Assignment;
import com.mynevermore.universityhelper.model.Grade;
import com.mynevermore.universityhelper.model.Note;
import com.mynevermore.universityhelper.model.Task;
import com.mynevermore.universityhelper.model.Tutes;

// import au.edu.murdoch.ict376.universityquest.model.Achievement;

public class DBHelper extends SQLiteOpenHelper {
    // Array divider
    private static final String ARRAY_DIVIDER = "vyo6vj50fdbv";

    // The database name
    private static final String DATABASE_NAME = "UniQuest.db";

    // The table "notes"
    private static final String NOTES_TABLE_NAME = "notes";
    private static final String NOTES_COLUMN_ID = "id";
    private static final String NOTES_COLUMN_TITLE = "title";
    private static final String NOTES_COLUMN_DATE = "date";
    private static final String NOTES_COLUMN_TEXT = "text";

    // The table "tasks"
    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String TASKS_COLUMN_ID = "id";
    private static final String TASKS_COLUMN_TITLE = "title";
    private static final String TASKS_COLUMN_DATE = "date";
    private static final String TASKS_COLUMN_TEXT = "text";
    private static final String TASKS_COLUMN_COMPLETED = "completed";

    // The table "assignments"
    private static final String ASSIGNMENTS_TABLE_NAME = "assignments";
    private static final String ASSIGNMENTS_COLUMN_ID = "id";
    private static final String ASSIGNMENTS_COLUMN_TITLE = "title";
    private static final String ASSIGNMENTS_COLUMN_DUEDATE = "duedate";
    private static final String ASSIGNMENTS_COLUMN_DUETIME = "duetime";
    private static final String ASSIGNMENTS_COLUMN_TASKS = "tasks";
    private static final String ASSIGNMENTS_COLUMN_COMPLETED = "completed";
    private static final String ASSIGNMENTS_COLUMN_TEXT = "text";

    // The table "achievements"
    private static final String ACHIEVEMENTS_TABLE_NAME = "achievements";
    private static final String ACHIEVEMENTS_COLUMN_ID = "id";
    private static final String ACHIEVEMENTS_COLUMN_TITLE = "title";
    private static final String ACHIEVEMENTS_COLUMN_CRITERIA = "criteria";
    private static final String ACHIEVEMENTS_COLUMN_COMPLETED = "completed";

    // The table "classes"
    private static final String TUTES_TABLE_NAME = "classes";
    private static final String TUTES_COLUMN_ID = "id";
    private static final String TUTES_COLUMN_TITLE = "title";
    private static final String TUTES_COLUMN_DAY = "day";
    private static final String TUTES_COLUMN_TIME = "time";
    private static final String TUTES_COLUMN_TYPE = "type";
    private static final String TUTES_COLUMN_LOCATION = "location";

    // The table "grades"
    private static final String GRADES_TABLE_NAME = "grades";
    private static final String GRADES_COLUMN_ID = "id";
    private static final String GRADES_COLUMN_UNIT = "unit";
    private static final String GRADES_COLUMN_GRADE = "grade";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating the tables
        db.execSQL("create table " + NOTES_TABLE_NAME + "(" +
                        NOTES_COLUMN_ID + " integer primary key autoincrement, " +
                        NOTES_COLUMN_TITLE + " text, " +
                        NOTES_COLUMN_DATE + " text, " +
                        NOTES_COLUMN_TEXT + " text);");

        db.execSQL("create table " + TASKS_TABLE_NAME + "(" +
                        TASKS_COLUMN_ID + " integer primary key autoincrement, " +
                        TASKS_COLUMN_TITLE + " text, " +
                        TASKS_COLUMN_DATE + " text, " +
                        TASKS_COLUMN_TEXT + " text, " +
                        TASKS_COLUMN_COMPLETED + " text);");

        db.execSQL("create table " + ASSIGNMENTS_TABLE_NAME + "(" +
                        ASSIGNMENTS_COLUMN_ID + " integer primary key autoincrement, " +
                        ASSIGNMENTS_COLUMN_TITLE + " text, " +
                        ASSIGNMENTS_COLUMN_DUEDATE + " text, " +
                        ASSIGNMENTS_COLUMN_DUETIME + " text, " +
                        ASSIGNMENTS_COLUMN_TASKS + " text, " +
                        ASSIGNMENTS_COLUMN_COMPLETED + " text, " +
                        ASSIGNMENTS_COLUMN_TEXT + " text);");

        db.execSQL("create table " + ACHIEVEMENTS_TABLE_NAME + "(" +
                        ACHIEVEMENTS_COLUMN_ID + " integer primary key autoincrement, " +
                        ACHIEVEMENTS_COLUMN_TITLE + " text, " +
                        ACHIEVEMENTS_COLUMN_CRITERIA + " text, " +
                        ACHIEVEMENTS_COLUMN_COMPLETED + " text);");

        db.execSQL("create table " + TUTES_TABLE_NAME + "(" +
                        TUTES_COLUMN_ID + " integer primary key autoincrement, " +
                        TUTES_COLUMN_TITLE + " text, " +
                        TUTES_COLUMN_DAY + " text, " +
                        TUTES_COLUMN_TIME + " text, " +
                        TUTES_COLUMN_TYPE + " text, " +
                        TUTES_COLUMN_LOCATION + " text);");

        db.execSQL("create table " + GRADES_TABLE_NAME + "(" +
                        GRADES_COLUMN_ID + " integer primary key autoincrement, " +
                        GRADES_COLUMN_UNIT + " text, " +
                        GRADES_COLUMN_GRADE + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ACHIEVEMENTS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TUTES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GRADES_TABLE_NAME + ";");

        onCreate(db);
    }

    public static String serialise(String content[]) {
        return TextUtils.join(ARRAY_DIVIDER, content);
    }

    public static String[] deserialise(String content) {
        return content.split(ARRAY_DIVIDER);
    }

    // ------------------- NOTES TABLE METHODS --------------------- //

    public void addNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_COLUMN_TITLE, note.getTitle());
        values.put(NOTES_COLUMN_DATE, note.getDate().toString());
        values.put(NOTES_COLUMN_TEXT, note.getText());

        database.insert(NOTES_TABLE_NAME, null, values);

        database.close();
    }

    public Note getNote(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor =
                database.query(NOTES_TABLE_NAME,
                        null,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note();
        note.setId(Integer.parseInt(cursor.getString(0)));
        note.setTitle(cursor.getString(1));
        note.setDate(cursor.getString(2));
        note.setText(cursor.getString(3));

        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new LinkedList<Note>();

        String query = "SELECT  * FROM " + NOTES_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Note note = null;
        if (cursor.moveToFirst()) {
            do {
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDate(cursor.getString(2));
                note.setText(cursor.getString(3));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        return notes;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_COLUMN_TITLE, note.getTitle());
        values.put(NOTES_COLUMN_DATE, note.getDate());
        values.put(NOTES_COLUMN_TEXT, note.getText());

        int i = db.update(NOTES_TABLE_NAME, //table
                values, // column/value
                NOTES_COLUMN_ID + " = ?", // selections
                new String[]{String.valueOf(note.getId())}); //selection args

        db.close();

        return i;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(NOTES_TABLE_NAME,
                NOTES_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    // ------------------- TASKS TABLE METHODS --------------------- //

    public void addTask(Task task) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASKS_COLUMN_TITLE, task.getTitle());
        values.put(TASKS_COLUMN_DATE, task.getDate().toString());
        values.put(TASKS_COLUMN_TEXT, serialise(task.getText()));
        values.put(TASKS_COLUMN_COMPLETED, task.getCompleted().toString());

        database.insert(TASKS_TABLE_NAME, null, values);

        database.close();
    }

    public Task getTask(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor =
                database.query(TASKS_TABLE_NAME,
                        null,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task();
        task.setId(Integer.parseInt(cursor.getString(0)));
        task.setTitle(cursor.getString(1));
        task.setDate(cursor.getString(2 ));
        task.setText(deserialise(cursor.getString(3)));

        return task;
    }

    public Task getTaskTitle(String title) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor =
                database.query(TASKS_TABLE_NAME,
                        null,
                        " title = ?",
                        new String[]{String.valueOf(title)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task();
        task.setId(Integer.parseInt(cursor.getString(0)));
        task.setTitle(cursor.getString(1));
        task.setDate(cursor.getString(2 ));
        task.setText(deserialise(cursor.getString(3)));

        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new LinkedList<Task>();

        String query = "SELECT  * FROM " + TASKS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Task task = null;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTitle(cursor.getString(1));
                task.setDate(cursor.getString(2));
                task.setText(deserialise(cursor.getString(3)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }

        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASKS_COLUMN_TITLE, task.getTitle());
        values.put(TASKS_COLUMN_DATE, task.getDate().toString());
        values.put(TASKS_COLUMN_TEXT, serialise(task.getText()));
        values.put(TASKS_COLUMN_COMPLETED, task.getCompleted().toString());

        int i = db.update(TASKS_TABLE_NAME, //table
                values, // column/value
                TASKS_COLUMN_ID + " = ?", // selections
                new String[]{String.valueOf(task.getId())}); //selection args

        db.close();

        return i;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TASKS_TABLE_NAME,
                TASKS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }


    // ------------------- ASSIGNMENTS TABLE METHODS --------------------- //

    public void addAssignment(Assignment assignment) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ASSIGNMENTS_COLUMN_TITLE, assignment.getTitle());
        values.put(ASSIGNMENTS_COLUMN_DUEDATE, assignment.getDueDate().toString());
        values.put(ASSIGNMENTS_COLUMN_DUETIME, assignment.getDueTime().toString());
        values.put(ASSIGNMENTS_COLUMN_TASKS, assignment.getTaskId());
        values.put(ASSIGNMENTS_COLUMN_COMPLETED, assignment.isCompleted());
        values.put(ASSIGNMENTS_COLUMN_TEXT, assignment.getText());

        database.insert(ASSIGNMENTS_TABLE_NAME, null, values);

        database.close();
    }

    public Assignment getAssignment(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor =
                database.query(ASSIGNMENTS_TABLE_NAME,
                        null,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Assignment assignment = new Assignment();
        assignment.setId(Integer.parseInt(cursor.getString(0)));
        assignment.setTitle(cursor.getString(1));
        assignment.setDueDate(cursor.getString(2));
        assignment.setDueTime(cursor.getString(3));
        assignment.setTaskId(Integer.parseInt(cursor.getString(4)));
        assignment.setCompleted(Boolean.parseBoolean(cursor.getString(5)));
        assignment.setText(cursor.getString(6));

        return assignment;
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> assignments = new LinkedList<Assignment>();

        String query = "SELECT  * FROM " + ASSIGNMENTS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Assignment assignment = null;
        if (cursor.moveToFirst()) {
            do {
                assignment = new Assignment();
                assignment.setId(Integer.parseInt(cursor.getString(0)));
                assignment.setTitle(cursor.getString(1));
                assignment.setDueDate(cursor.getString(2));
                assignment.setDueTime(cursor.getString(3));
                assignment.setTaskId(Integer.parseInt(cursor.getString(4)));
                assignment.setCompleted(Boolean.parseBoolean(cursor.getString(5)));
                assignment.setText(cursor.getString(6));

                assignments.add(assignment);
            } while (cursor.moveToNext());
        }

        return assignments;
    }

    public int updateAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ASSIGNMENTS_COLUMN_TITLE, assignment.getTitle());
        values.put(ASSIGNMENTS_COLUMN_DUEDATE, assignment.getDueDate().toString());
        values.put(ASSIGNMENTS_COLUMN_DUETIME, assignment.getDueTime().toString());
        values.put(ASSIGNMENTS_COLUMN_TASKS, assignment.getTaskId());
        values.put(ASSIGNMENTS_COLUMN_COMPLETED, assignment.isCompleted());
        values.put(ASSIGNMENTS_COLUMN_TEXT, assignment.getText());

        int i = db.update(ASSIGNMENTS_TABLE_NAME, //table
                values, // column/value
                ASSIGNMENTS_COLUMN_ID + " = ?", // selections
                new String[]{String.valueOf(assignment.getId())}); //selection args

        db.close();

        return i;
    }

    public void deleteAssignment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ASSIGNMENTS_TABLE_NAME,
                ASSIGNMENTS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }


//    // ------------------- ACHIEVEMENTS TABLE METHODS --------------------- //
//
//    public void addAchievement(Achievement achievement) {
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(ACHIEVEMENTS_COLUMN_TITLE, achievement.getTitle());
//        values.put(ACHIEVEMENTS_COLUMN_CRITERIA, achievement.getCriteria());
//        values.put(ACHIEVEMENTS_COLUMN_COMPLETED, achievement.isCompleted());
//
//        database.insert(ACHIEVEMENTS_TABLE_NAME, null, values);
//
//        database.close();
//    }
//
//    public Achievement getAchievement(int id) {
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor =
//                database.query(ACHIEVEMENTS_TABLE_NAME,
//                        null,
//                        " id = ?",
//                        new String[]{String.valueOf(id)},
//                        null,
//                        null,
//                        null,
//                        null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Achievement achievement = new Achievement();
//        achievement.setId(Integer.parseInt(cursor.getString(0)));
//        achievement.setTitle(cursor.getString(1));
//        achievement.setCriteria(cursor.getString(2));
//        achievement.setCompleted(Boolean.parseBoolean(cursor.getString(3)));
//
//        return achievement;
//    }
//
//    public List<Achievement> getAllAchievements() {
//        List<Achievement> achievements = new LinkedList<Achievement>();
//
//        String query = "SELECT  * FROM " + ACHIEVEMENTS_TABLE_NAME;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        Achievement achievement = null;
//        if (cursor.moveToFirst()) {
//            do {
//                achievement = new Achievement();
//                achievement.setId(Integer.parseInt(cursor.getString(0)));
//                achievement.setTitle(cursor.getString(1));
//                achievement.setCriteria(cursor.getString(2));
//                achievement.setCompleted(Boolean.parseBoolean(cursor.getString(3)));
//
//                achievements.add(achievement);
//            } while (cursor.moveToNext());
//        }
//
//        return achievements;
//    }
//
//    public int updateAchievement(Achievement achievement) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(ACHIEVEMENTS_COLUMN_TITLE, achievement.getTitle());
//        values.put(ACHIEVEMENTS_COLUMN_CRITERIA, achievement.getCriteria());
//        values.put(ACHIEVEMENTS_COLUMN_COMPLETED, achievement.isCompleted());
//
//        int i = db.update(ACHIEVEMENTS_TABLE_NAME, //table
//                values, // column/value
//                ACHIEVEMENTS_COLUMN_ID + " = ?", // selections
//                new String[]{String.valueOf(achievement.getId())}); //selection args
//
//        db.close();
//
//        return i;
//    }
//
//    public void deleteAchievement(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.delete(ACHIEVEMENTS_TABLE_NAME,
//                ACHIEVEMENTS_COLUMN_ID + " = ?",
//                new String[]{String.valueOf(id)});
//
//        db.close();
//    }


    // ------------------- TUTES TABLE METHODS --------------------- //

    public void addTutes(Tutes tutes) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TUTES_COLUMN_TITLE, tutes.getTitle());
        values.put(TUTES_COLUMN_TYPE, tutes.getTuteType());
        values.put(TUTES_COLUMN_DAY, tutes.getDay());
        values.put(TUTES_COLUMN_TIME, tutes.getTime());
        values.put(TUTES_COLUMN_LOCATION, tutes.getLocation());

        database.insert(TUTES_TABLE_NAME, null, values);

        database.close();
    }

    public Tutes getTutes(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor =
                database.query(TUTES_TABLE_NAME,
                        null,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Tutes tute = new Tutes();
        tute.setId(Integer.parseInt(cursor.getString(0)));
        tute.setTitle(cursor.getString(1));
        tute.setTuteType(cursor.getString(4));
        tute.setDay(cursor.getString(2));
        tute.setTime(cursor.getString(3));
        tute.setLocation(cursor.getString(5));

        return tute;
    }

    public List<Tutes> getAllTutes() {
        List<Tutes> tutes = new LinkedList<Tutes>();

        String query = "SELECT  * FROM " + TUTES_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Tutes tute = null;
        if (cursor.moveToFirst()) {
            do {
                tute = new Tutes();
                tute.setId(Integer.parseInt(cursor.getString(0)));
                tute.setTitle(cursor.getString(1));
                tute.setTuteType(cursor.getString(4));
                tute.setDay(cursor.getString(2));
                tute.setTime(cursor.getString(3));
                tute.setLocation(cursor.getString(5));

                // Add book to books
                tutes.add(tute);
            } while (cursor.moveToNext());
        }

        return tutes;
    }

    public int updateTutes(Tutes tutes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TUTES_COLUMN_TITLE, tutes.getTitle());
        values.put(TUTES_COLUMN_TYPE, tutes.getTuteType());
        values.put(TUTES_COLUMN_DAY, tutes.getDay());
        values.put(TUTES_COLUMN_TIME, tutes.getTime());
        values.put(TUTES_COLUMN_LOCATION, tutes.getLocation());

        int i = db.update(TUTES_TABLE_NAME, //table
                values, // column/value
                TUTES_COLUMN_ID + " = ?", // selections
                new String[]{String.valueOf(tutes.getId())}); //selection args

        db.close();

        return i;
    }

    public void deleteTutes(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TUTES_TABLE_NAME,
                TUTES_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }


    // ------------------- GRADES TABLE METHODS --------------------- //

    public void addGrade(Grade grade) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GRADES_COLUMN_UNIT, grade.getUnit());
        values.put(GRADES_COLUMN_GRADE, grade.getGrade());

        database.insert(GRADES_TABLE_NAME, null, values);

        database.close();
    }

    public Grade getGrade(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor =
                database.query(GRADES_TABLE_NAME,
                        null,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Grade grade = new Grade();
        grade.setId(Integer.parseInt(cursor.getString(0)));
        grade.setUnit(cursor.getString(1));
        grade.setGrade(cursor.getString(2));

        return grade;
    }

    public List<Grade> getAllGrades() {
        List<Grade> grades = new LinkedList<Grade>();

        String query = "SELECT  * FROM " + GRADES_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Grade grade = null;
        if (cursor.moveToFirst()) {
            do {
                grade = new Grade();
                grade.setId(Integer.parseInt(cursor.getString(0)));
                grade.setUnit(cursor.getString(1));
                grade.setGrade(cursor.getString(2));

                grades.add(grade);
            } while (cursor.moveToNext());
        }

        return grades;
    }

    public int updateGrade(Grade grade) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GRADES_COLUMN_UNIT, grade.getUnit());
        values.put(GRADES_COLUMN_GRADE, grade.getGrade());

        int i = db.update(GRADES_TABLE_NAME, //table
                values, // column/value
                GRADES_COLUMN_ID + " = ?", // selections
                new String[]{String.valueOf(grade.getId())}); //selection args

        db.close();

        return i;
    }

    public void deleteGrade(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(GRADES_TABLE_NAME,
                GRADES_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }
}