package edu.huflit.doanqlthuvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.huflit.doanqlthuvien.OOP.NhanVien;

public class MyDatabase {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase(Context context)
    {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }
    //Thêm nhân viên
    public long addNhanVien(NhanVien nhanVien)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TEN_NHAN_VIEN_NV, nhanVien.getTen_nhan_vien_nv());
        contentValues.put(DBHelper.DIA_CHI_NV, nhanVien.getDia_chi_nv());
        contentValues.put(DBHelper.EMAIL_NV, nhanVien.getEmail_nv());
        contentValues.put(DBHelper.SO_DIEN_THOAI_NV, nhanVien.getSo_dien_thoai_nv());
        contentValues.put(DBHelper.USERNAME, nhanVien.getUsername());
        contentValues.put(DBHelper.PASSWORD, nhanVien.getPassword());
        return database.insert(DBHelper.TABLE_NHAN_VIEN, null, contentValues);
    }
    //Kiểm tra login
    public boolean checkLogin(String username, String password)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_NHAN_VIEN + " WHERE " + DBHelper.USERNAME + " = " + "'" + username + "'" + " AND " + DBHelper.PASSWORD + " = " + "'" + password + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst() == false)
        {
            return false;//Ko đúng user or pass
        }
        else
        {
            return true;
        }
    }
}
