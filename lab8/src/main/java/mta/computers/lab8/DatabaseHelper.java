package mta.computers.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gifts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_GIFTS = "gifts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_OBJECT_TYPE = "objectType";
    public static final String COLUMN_WRAPPED = "wrapped";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_GIFTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MESSAGE + " TEXT, " +
                    COLUMN_WEIGHT + " INTEGER, " +
                    COLUMN_OBJECT_TYPE + " TEXT, " +
                    COLUMN_WRAPPED + " INTEGER" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIFTS);
        onCreate(db);
    }

    public long insertGift(Gift gift) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, gift.getMessage());
        values.put(COLUMN_WEIGHT, gift.getWeight());
        values.put(COLUMN_OBJECT_TYPE, gift.getObjectType().name());
        values.put(COLUMN_WRAPPED, gift.isWrapped() ? 1 : 0);

        long id = db.insert(TABLE_GIFTS, null, values);
        db.close();
        return id;
    }

    public List<Gift> getAllGifts() {
        List<Gift> gifts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GIFTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                gifts.add(cursorToGift(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gifts;
    }

    public Gift getGiftByMessage(String message) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GIFTS, null, COLUMN_MESSAGE + "=?",
                new String[]{message}, null, null, null);

        Gift gift = null;
        if (cursor.moveToFirst()) {
            gift = cursorToGift(cursor);
        }
        cursor.close();
        db.close();
        return gift;
    }

    public List<Gift> getGiftsInWeightRange(int minWeight, int maxWeight) {
        List<Gift> gifts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GIFTS, null, COLUMN_WEIGHT + " BETWEEN ? AND ?",
                new String[]{String.valueOf(minWeight), String.valueOf(maxWeight)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                gifts.add(cursorToGift(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gifts;
    }

    public int deleteGiftsWithWeightLessThan(int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_GIFTS, COLUMN_WEIGHT + " < ?", new String[]{String.valueOf(weight)});
        db.close();
        return count;
    }

    public void incrementWeightForMessagesStartingWith(String letter) {
        SQLiteDatabase db = this.getWritableDatabase();
        // SQL: UPDATE gifts SET weight = weight + 1 WHERE message LIKE 'letter%'
        db.execSQL("UPDATE " + TABLE_GIFTS + " SET " + COLUMN_WEIGHT + " = " + COLUMN_WEIGHT + " + 1 " +
                " WHERE " + COLUMN_MESSAGE + " LIKE ?", new String[]{letter + "%"});
        db.close();
    }

    private Gift cursorToGift(Cursor cursor) {
        String message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE));
        int weight = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT));
        Objects type = Objects.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBJECT_TYPE)));
        boolean wrapped = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WRAPPED)) == 1;

        return new Gift(message, weight, type, wrapped);
    }
}