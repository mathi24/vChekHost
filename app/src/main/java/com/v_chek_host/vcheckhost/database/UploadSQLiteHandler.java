package com.v_chek_host.vcheckhost.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.v_chek_host.vcheckhost.V2.models.UploadVideoData;
import com.v_chek_host.vcheckhostsdk.model.entity.ModelStatus;

import java.util.ArrayList;
import java.util.List;


public class UploadSQLiteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vChekHost";
    private static final String TABLE_MODEL = "model_training_video";
    private static final String KEY_ID = "id";
    private static final String KEY_MODEL_ID = "model_id";
    private static final String KEY_VIDEO_UPLOAD_TYPE = "video_upload_type";
    private static final String KEY_VIDEO_LOCATION = "video_location";
    private static final String KEY_UPLOAD_STATUS = "upload_status";



    public UploadSQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MODEL + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_MODEL_ID + " INTEGER,"+ KEY_VIDEO_UPLOAD_TYPE + " INTEGER," + KEY_VIDEO_LOCATION + " TEXT,"
            + KEY_UPLOAD_STATUS + " INTEGER"+")";
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODEL);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addUploadVideo(UploadVideoData uploadVideoData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MODEL_ID, uploadVideoData.getModelId());
        values.put(KEY_VIDEO_UPLOAD_TYPE, uploadVideoData.getUploadType());
        values.put(KEY_VIDEO_LOCATION, uploadVideoData.getVideoLocation());
        values.put(KEY_UPLOAD_STATUS, uploadVideoData.getUploadStatus());

        // Inserting Row
        db.insert(TABLE_MODEL, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    ModelStatus getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MODEL, new String[] { KEY_ID}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ModelStatus contact = new ModelStatus(Integer.parseInt(cursor.getString(0)));
        // return contact
        return contact;
    }

    // code to get single upload data
    public List<UploadVideoData> getVideoUploadData() {
        List<UploadVideoData> uploadVideoDataList = new ArrayList<UploadVideoData>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MODEL + " where "+KEY_UPLOAD_STATUS +" = "+ 0 +" Limit "+1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UploadVideoData uploadVideoData = new UploadVideoData(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)));

                // Adding contact to list
                uploadVideoDataList.add(uploadVideoData);
            } while (cursor.moveToNext());
        }

        // return contact list
        return uploadVideoDataList;
    }

    public boolean getVideoUploadStatus()
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        boolean hasData = false;
        Cursor cursor = null;
        try {
            String selectQuery;
            selectQuery = "SELECT * FROM " + TABLE_MODEL + " where "+KEY_UPLOAD_STATUS +" = "+ 0 +" Limit "+1;
            cursor = sqLiteDatabase.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                hasData = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return hasData;
    }

    public int updateUploadStatus(int keyId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UPLOAD_STATUS, 1);
               // updating row
        return db.update(TABLE_MODEL, values, KEY_ID + " = ?",
                new String[] { String.valueOf(keyId) });
    }



    /*// code to update the single contact
    public int updateContact(ModelStatus modelStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MODEL_NAME, modelStatus.get_model_name());
        values.put(KEY_MODEL_FRAME_TYPE, modelStatus.get_model_frame_type());

        // updating row
        return db.update(TABLE_MODEL, values, KEY_ID + " = ?",
                new String[] { String.valueOf(modelStatus.get_model_id()) });
    }*/

    // Deleting single contact
    public void deleteVideoData(UploadVideoData uploadVideoData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MODEL, KEY_ID + " = ?",
                new String[] { String.valueOf(uploadVideoData.getKeyId()) });
        db.close();
    }

    // Getting contacts Count
    public int getModelCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MODEL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}