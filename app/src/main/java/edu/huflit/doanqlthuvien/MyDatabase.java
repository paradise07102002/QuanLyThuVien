package edu.huflit.doanqlthuvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.huflit.doanqlthuvien.OOP.LoaiSach;
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
    public boolean checkTenDauSach(String ten_dau_sach)
    {
        //Truy vấn lấy cột mã loại sách nếu mã loại sách nhập vào có trong Database
        String select = "SELECT " + DBHelper.MA_LOAI_SACH_LS  + " FROM " + DBHelper.TABLE_LOAI_SACH + " WHERE " + DBHelper.TEN_LOAI_SACH_LS + " = " + "'" + ten_dau_sach + "'";
        //Đưa kết quả truy vấn vào trong cursor/ Do mã sách là duy nhất nên chỉ có thể tìm ra tối đa 1 đối tượng mã sách
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            return true;//Mã loại sách tồn tại
        }
        return false;//Mã loại sách không tồn tại
    }
    public long addLoaiSach(LoaiSach loaiSach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TEN_LOAI_SACH_LS, loaiSach.getLoai_sach_ls());
        return database.insert(DBHelper.TABLE_LOAI_SACH, null, values);
    }
    public Cursor layDuLieuDauSach()
    {
        String[] cot = {DBHelper.MA_LOAI_SACH_LS, DBHelper.TEN_LOAI_SACH_LS};
        Cursor cursor = database.query(DBHelper.TABLE_LOAI_SACH, cot, null, null, null, null, null);
        return cursor;
    }
    public long xoaDauSach(int ma_dau_sach)
    {
        return database.delete(DBHelper.TABLE_LOAI_SACH, DBHelper.MA_LOAI_SACH_LS + " = " + "'" + ma_dau_sach + "'", null);
    }
}
