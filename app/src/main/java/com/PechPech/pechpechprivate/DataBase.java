package com.PechPech.pechpechprivate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.PechPech.pechpechprivate.RecyclerViewDataModel;

import java.util.ArrayList;
import java.util.List;

/* renamed from: ir.rayanafrooz.app.DataBase */
public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Message";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_IsRead = "IsRead";
    private static final String KEY_Message = "Message";
    private static final String KEY_Time = "Time";
    private static final String KEY_Title = "Title";
    private static final String TABLE_Message = "Tbl_Messages";

    DataBase(Context context) {
        super(context, "Message", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Tbl_Messages(id INTEGER PRIMARY KEY,Message TEXT,Time TEXT ,IsRead INTEGER ,Title TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /* access modifiers changed from: 0000 */
    public void setRead(RecyclerViewDataModel dataModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_IsRead, Integer.valueOf(dataModel.isRead()));
        StringBuilder sb = new StringBuilder();
        sb.append(" id = ");
        sb.append(dataModel.getId());
        db.update(TABLE_Message, cv, sb.toString(), null);
        db.close();
    }

    /* access modifiers changed from: 0000 */
    public void addMessage(RecyclerViewDataModel dataModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Message", dataModel.getDescription());
        values.put(KEY_Time, dataModel.getDate());
        values.put(KEY_IsRead, dataModel.isRead());
        values.put(KEY_Title, dataModel.getTitle());
        db.insert(TABLE_Message, null, values);
        db.close();
    }

    /* access modifiers changed from: 0000 */
    public List<RecyclerViewDataModel> getAllMessages(int limit) {
        String selectQuery;
        List<RecyclerViewDataModel> contactList = new ArrayList<>();
        if (limit != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT  * FROM Tbl_Messages order by id desc limit ");
            sb.append(limit);
            selectQuery = sb.toString();
        } else {
            selectQuery = "SELECT  * FROM Tbl_Messages order by id desc";
        }
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RecyclerViewDataModel contact = new RecyclerViewDataModel();
                contact.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex("Message")));
                contact.setDate(cursor.getString(cursor.getColumnIndex(KEY_Time)));
                contact.setRead(cursor.getInt(cursor.getColumnIndex(KEY_IsRead)));
                contact.setTitle(cursor.getString(cursor.getColumnIndex(KEY_Title)));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    /* access modifiers changed from: 0000 */
    public void deleteAllMessages() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from Tbl_Messages");
        db.close();
    }

    /* access modifiers changed from: 0000 */
    public void deleteMassageOnByOn(int messageId) {
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("delete from Tbl_Messages where id = ");
        sb.append(messageId);
        db.execSQL(sb.toString());
        db.close();
    }
}
