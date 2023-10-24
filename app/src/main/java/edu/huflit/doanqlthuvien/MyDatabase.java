package edu.huflit.doanqlthuvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.huflit.doanqlthuvien.OOP.BinhLuanSach;
import edu.huflit.doanqlthuvien.OOP.Chat;
import edu.huflit.doanqlthuvien.OOP.LoaiSach;
import edu.huflit.doanqlthuvien.OOP.MuonTraSach;
import edu.huflit.doanqlthuvien.OOP.NhanVien;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.OOP.User;

public class MyDatabase {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase(Context context)
    {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }
    //Thêm nhân viên

    //Kiểm tra login
    public boolean checkLogin(String username, String password)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'" + " AND " + DBHelper.PASSWORD_USER + " = " + "'" + password + "'";
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
    public long addChat(Chat chat)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ID_NGUOI_GUI, chat.getId_nguoi_gui());
        values.put(DBHelper.ID_NGUOI_NHAN, chat.getId_nguoi_nhan());
        values.put(DBHelper.NOI_DUNG, chat.getNoi_dung());
        values.put(DBHelper.THOI_GIAN_GUI, chat.getThoi_gian_gui());
        return database.insert(DBHelper.TABLE_TIN_NHAN, null, values);
    }
    public Cursor layDuLieuDauSach()
    {
        String[] cot = {DBHelper.MA_LOAI_SACH_LS, DBHelper.TEN_LOAI_SACH_LS};
        Cursor cursor = database.query(DBHelper.TABLE_LOAI_SACH, cot, null, null, null, null, null);
        return cursor;
    }
    public Cursor layDuLieuChat(int id_nguoi_gui)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_TIN_NHAN + " WHERE " + DBHelper.ID_NGUOI_GUI + " = " + id_nguoi_gui;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public Cursor layDuLieuFullChat()
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_TIN_NHAN;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public Cursor layDuLieuSach()
    {
        String[] cot = {DBHelper.MA_SACH_S, DBHelper.TEN_SACH_S, DBHelper.IMAGE_SACH};
        Cursor cursor = database.query(DBHelper.TABLE_SACH, cot, null, null, null, null, null);
        return cursor;
    }
    public Cursor layDuLieuBinhLuanByMaSach(int ma_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_BINH_LUAN + " WHERE " + DBHelper.MA_SACH_BL + " = " + ma_sach;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public long xoaDauSach(int ma_dau_sach)
    {
        return database.delete(DBHelper.TABLE_LOAI_SACH, DBHelper.MA_LOAI_SACH_LS + " = " + "'" + ma_dau_sach + "'", null);
    }
    public long xoaMuonTraSach(int ma_muon_tra)
    {
        return database.delete(DBHelper.TABLE_MUON_TRA, DBHelper.MA_MUON_TRA_MTS + " = " + "'" + ma_muon_tra + "'", null);
    }
    public long xoaSach(int ma_sach)
    {
        return database.delete(DBHelper.TABLE_SACH, DBHelper.MA_SACH_S + " = " + "'" + ma_sach + "'", null);
    }
    public boolean kiemTraTenDS(String ten_dau_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_LOAI_SACH + " WHERE " + DBHelper.TEN_LOAI_SACH_LS + " = " + "'" + ten_dau_sach + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst() == true)
        {
            return true;//có tồn tại
        }
        else
        {
            return false;
        }
    }
    public long suaDauSach(LoaiSach loaiSach)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.MA_LOAI_SACH_LS, loaiSach.getMa_loai_sach_ls());
        contentValues.put(DBHelper.TEN_LOAI_SACH_LS, loaiSach.getLoai_sach_ls());
        return database.update(DBHelper.TABLE_LOAI_SACH, contentValues, DBHelper.MA_LOAI_SACH_LS + " = " + loaiSach.getMa_loai_sach_ls(), null);
    }
    public long suaSach(Sach sach)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.MA_SACH_S, sach.getMa_sach_s());
        contentValues.put(DBHelper.TEN_SACH_S, sach.getTen_sach_s());
        contentValues.put(DBHelper.TAC_GIA_S, sach.getTac_gia_s());
        contentValues.put(DBHelper.NHA_XUAT_BAN_S, sach.getNha_xuat_ban_s());
        contentValues.put(DBHelper.NAM_XUAT_BAN_S, sach.getNam_xuat_ban_s());
        contentValues.put(DBHelper.MO_TA_SACH, sach.getMo_ta_sach());
        contentValues.put(DBHelper.IMAGE_SACH, sach.getImage_sach());
        return database.update(DBHelper.TABLE_SACH, contentValues, DBHelper.MA_SACH_S + " = " + sach.getMa_sach_s(), null);
    }
    public long suaTrangThaiSach(int ma_sach, int trang_thai)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TRANG_THAI_S, trang_thai);
        return database.update(DBHelper.TABLE_SACH, values, DBHelper.MA_SACH_S + " = " + ma_sach,null);
    }
    public Cursor layDuLieuDauSachByID(int ma_dau_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_LOAI_SACH + " WHERE " + DBHelper.MA_LOAI_SACH_LS + " = " + ma_dau_sach;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public Cursor layDuLieuSachByID(int ma_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_SACH + " WHERE " + DBHelper.MA_SACH_S + " = " + ma_sach;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public Cursor layDuLieuSachByName(String name_sach)
    {
        String select = " SELECT * FROM " + DBHelper.TABLE_SACH + " WHERE " + DBHelper.TEN_SACH_S + " LIKE " + "'%" + name_sach + "%'";
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    //Hàm lấy đầu sách đưa lên spinner
    public Cursor layFullDuLieuDauSach()
    {
        String select = "SELECT " + DBHelper.TEN_LOAI_SACH_LS + " FROM " + DBHelper.TABLE_LOAI_SACH;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    //Lấy id đầu sách khi có mã sách
    public LoaiSach getMaDauSachByTenDS(String ten_dau_sach)
    {
        LoaiSach loaiSach = new LoaiSach();
        String select = "SELECT * FROM "
                + DBHelper.TABLE_LOAI_SACH + " WHERE " + DBHelper.TEN_LOAI_SACH_LS + " = "
                + "'" + ten_dau_sach + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor != null)
        {
            int ma_dau_sach_index = cursor.getColumnIndex(DBHelper.MA_LOAI_SACH_LS);
            while (cursor.moveToNext())
            {
                //có 10 bản ghi sách trong cursor
                //moveToFirst chỉ tới cuốn sách đầu
                loaiSach.setMa_loai_sach_ls(cursor.getInt(ma_dau_sach_index));
            }
        }
        return loaiSach;
    }
    //Hàm thêm sách
    public long addBook(Sach sach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.MA_LOAI_SACH_S, sach.getMa_loai_sach_s());
        values.put(DBHelper.TEN_SACH_S, sach.getTen_sach_s());
        values.put(DBHelper.TAC_GIA_S, sach.getTac_gia_s());
        values.put(DBHelper.NHA_XUAT_BAN_S, sach.getNha_xuat_ban_s());
        values.put(DBHelper.NAM_XUAT_BAN_S, sach.getNam_xuat_ban_s());
        values.put(DBHelper.IMAGE_SACH, sach.getImage_sach());
        values.put(DBHelper.TRANG_THAI_S, 0);
        values.put(DBHelper.MO_TA_SACH, sach.getMo_ta_sach());
        return database.insert(DBHelper.TABLE_SACH, null, values);
    }
    public boolean checkUsername(String username)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            return true;
        }
        return false;
    }
    public long addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USERNAME_USER, user.getUsername_user());
        values.put(DBHelper.PASSWORD_USER, user.getPassword_user());
        values.put(DBHelper.FULLNAME_USER, user.getFullname_user());
        values.put(DBHelper.EMAIL_USER, user.getEmail_user());
        values.put(DBHelper.PHONE_USER, user.getPhone_user());
        values.put(DBHelper.ROLE_USER, user.getRole_user());
        values.put(DBHelper.LOAI_KH_USER, user.getLoai_kh_user());
        return database.insert(DBHelper.TABLE_USER, null, values);
    }
    public long addBinhLuan(BinhLuanSach binhLuanSach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.MA_USER_BL, binhLuanSach.getMa_user_binh_luan());
        values.put(DBHelper.MA_SACH_BL, binhLuanSach.getMa_sach_binh_luan());
        values.put(DBHelper.NOI_DUNG_BL, binhLuanSach.getNoi_dung_binh_luan());
        return database.insert(DBHelper.TABLE_BINH_LUAN, null, values);
    }
    public long addMuonTraSach(MuonTraSach muonTraSach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.MA_SACH_MTS, muonTraSach.getMa_sach_mts());
        values.put(DBHelper.MA_USER_MTS, muonTraSach.getMa_user_mts());
        values.put(DBHelper.NGAY_MUON_MTS, muonTraSach.getNgay_muon_mts());
        values.put(DBHelper.NGAY_TRA_MTS, muonTraSach.getNgay_tra_mts());

        return database.insert(DBHelper.TABLE_MUON_TRA, null, values);
    }
    public Cursor layDuLieuMuonTraSach()
    {
        String[] cot = {DBHelper.MA_MUON_TRA_MTS, DBHelper.MA_SACH_MTS, DBHelper.MA_USER_MTS};
        Cursor cursor = database.query(DBHelper.TABLE_MUON_TRA, cot, null, null, null, null, null);
        return cursor;
    }
    public Cursor layDuLieuMuonTraSachByMaUser(int ma_user)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_MUON_TRA + " WHERE " + DBHelper.MA_USER_MTS + " = " + "'" + ma_user + "'";
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public Cursor getUserByUsername(String username)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'";
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public User getUserByIDUser(int ma_user)
    {
        User user = new User();
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.ID_USER + " = " + ma_user;
        Cursor cursor = database.rawQuery(select, null);
        if (cursor != null)
        {
            int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);
            cursor.moveToFirst();
            user.setUsername_user(cursor.getString(username_index));
        }
        return user;
    }
    public Cursor getSachByMaSach(int ma_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_SACH + " WHERE " + DBHelper.MA_SACH_S + " = " + "'" + ma_sach + "'";
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }

    public User checkRole(String username)
    {
        User user = new User();
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor != null)
        {
            int role_index = cursor.getColumnIndex(DBHelper.ROLE_USER);
            cursor.moveToFirst();
            user.setRole_user(cursor.getString(role_index));
        }
        cursor.close();
        return user;
    }
    //kiểm tra mật khẩu cũ có trùng không
    public boolean checkMatKhau(String username, String password)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'" + " AND " + DBHelper.PASSWORD_USER + " = " + "'" + password + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor != null || cursor.getCount() > 0)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }
    public boolean checkMK(String username, String password)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'" + " AND " + DBHelper.PASSWORD_USER + " = " + "'" + password + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst() == false)
        {
            cursor.close();
            return false;//mk cũ sai
        }
        else
        {
            cursor.close();
            return true;
        }
    }
    public long doiMK(String username, String mat_khau_moi)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.PASSWORD_USER, mat_khau_moi);
        return database.update(DBHelper.TABLE_USER, contentValues, DBHelper.USERNAME_USER +
                " = " + "'" + username + "'", null);
    }
    //Kiểm tra người dùng có từng mượn sách hay chưa
    public boolean checkMuonSach(int ma_user)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_MUON_TRA + " WHERE " + DBHelper.MA_USER_MTS + " = " + ma_user;
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            return true;
        }
        else {
            return false;
        }
    }
    //Kiểm tra admin tồn tại chưa
    public boolean checkAdmin()
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.ROLE_USER + " = " + "'admin'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //Lấy ngày trả sách
    public Cursor getNgayTraSach(int id_user)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_MUON_TRA + " WHERE " + DBHelper.MA_USER_MTS + " = " + id_user;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    //Lấy ngày mượn
    public Cursor getNgayTra(int ma_user)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_MUON_TRA + " WHERE " + DBHelper.MA_USER_MTS + " = " + ma_user;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public long editFullName(String username, String fullname)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FULLNAME_USER, fullname);
        return database.update(DBHelper.TABLE_USER, contentValues, DBHelper.USERNAME_USER +
                " = " + "'" + username + "'", null);
    }
    public long editEmail(String username, String email)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.EMAIL_USER, email);
        return database.update(DBHelper.TABLE_USER, contentValues, DBHelper.USERNAME_USER +
                " = " + "'" + username + "'", null);
    }
    public long editPhone(String username, String phone)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.PHONE_USER, phone);
        return database.update(DBHelper.TABLE_USER, contentValues, DBHelper.USERNAME_USER +
                " = " + "'" + username + "'", null);
    }
}
