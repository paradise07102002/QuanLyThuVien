package edu.huflit.doanqlthuvien;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TEN_DATABASE = "DATABASE";

    //BẢNG SÁCH
    public static final String TABLE_SACH = "Sach";
    public static final String IMAGE_SACH = "image_sach";

    public static final String MA_SACH_S = "ma_sach_s";

    public static final String MA_LOAI_SACH_S = "ma_loai_sach_s";
    public static final String TEN_SACH_S = "ten_sach_s";
    public static final String TAC_GIA_S = "tac_gia_s";
    public static final String NHA_XUAT_BAN_S = "nha_xuat_ban_s";
    public static final String NAM_XUAT_BAN_S = "nam_xuat_ban_s";
    public static final String TRANG_THAI_S = "trang_thai_s";
    public static final String MO_TA_SACH = "mo_ta_sach";

    //BẢNG LOẠI SÁCH
    public static final String TABLE_LOAI_SACH = "LoaiSach";
    public static final String MA_LOAI_SACH_LS = "ma_loai_sach_ls";
    public static final String TEN_LOAI_SACH_LS = "ten_loai_sach_ls";

    //BẢNG THÀNH VIÊN
//    public static final String TABLE_THANH_VIEN= "ThanhVien";
//    public static final String MA_THANH_VIEN_TV = "ma_thanh_vien_tv";
//    public static final String HO_TEN_TV = "ho_ten_tv";
//    public static final String DIA_CHI_TV = "dia_chi_tv";
//    public static final String SO_DIEN_THOAI_TV = "so_dien_thoai_tv";
//    public static final String EMAIL_TV = "email_tv";
    //BẢNG USER
    public static final String TABLE_USER = "User";
    public static final String ID_USER = "id_user";
    public static final String USERNAME_USER = "username_user";
    public static final String PASSWORD_USER = "password_user";
    public static final String ROLE_USER = "role_user";
    public static final String FULLNAME_USER = "fullname_user";
    public static final String LOAI_KH_USER = "loai_kh_user";
    public static final String EMAIL_USER = "email_user";
    public static final String PHONE_USER = "phone_user";

    //BẢNG MƯỢN TRẢ SÁCH
    public static final String TABLE_MUON_TRA= "MuonTraSach";
    public static final String MA_MUON_TRA_MTS = "ma_muon_tra_mts";
    public static final String MA_SACH_MTS = "ma_sach_mts";
    public static final String MA_USER_MTS = "ma_user_mts";
    public static final String NGAY_MUON_MTS = "ngay_muon_mts";
    public static final String NGAY_TRA_MTS = "ngay_tra_mts";

    //BẢNG NHÂN VIÊN
//    public static final String TABLE_NHAN_VIEN = "NhanVien";
//
//    public static final String USERNAME = "username";
//
//    public static final String PASSWORD = "password";
//    public static final String MA_NHAN_VIEN_NV = "ma_nhan_vien_nv";
//    public static final String TEN_NHAN_VIEN_NV = "ten_nhan_vien_nv";
//    public static final String DIA_CHI_NV = "dia_chi_nv";
//    public static final String SO_DIEN_THOAI_NV = "so_dien_thoai_nv";
//
//    public static final String EMAIL_NV = "email_nv";

    //BẢNG LIÊN KẾT SÁCH VỚI THÀNH VIÊN
//    public static final String TABLE_LIEN_KET = "LienKetSachThanhVien";
//    public static final String MA_SACH_LK = "ma_sach_lk";
//    public static final String MA_THANH_VIEN_LK = "ma_thanh_vien_lk";

    //TẠO DATABASE
    //TẠO BẢNG USER
    private static final String TAO_TABLE_USER = ""
            + "CREATE TABLE " + TABLE_USER + "( "
            + ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME_USER + " TEXT NOT NULL, "
            + PASSWORD_USER + " TEXT NOT NULL, "
            + ROLE_USER + " TEXT NOT NULL, "
            + FULLNAME_USER + " TEXT NOT NULL, "
            + LOAI_KH_USER + " TEXT, "
            + PHONE_USER + " TEXT NOT NULL,"
            + EMAIL_USER + " TEXT NOT NULL)";
    //TẠO BẢNG SÁCH
    private static final String TAO_TABLE_SACH = ""
            + "CREATE TABLE " + TABLE_SACH + "( "
            + IMAGE_SACH + " BLOB,"
            + MO_TA_SACH + " TEXT,"
            + MA_SACH_S + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MA_LOAI_SACH_S + " INTEGER NOT NULL, "
            + TEN_SACH_S + " TEXT NOT NULL, "
            + TAC_GIA_S + " TEXT NOT NULL, "
            + NHA_XUAT_BAN_S + " TEXT NOT NULL, "
            + NAM_XUAT_BAN_S + " INTEGER NOT NULL, "
            + TRANG_THAI_S + " INTEGER DEFAULT 0 CHECK(" + TRANG_THAI_S + " IN(0,1)), "
            + "FOREIGN KEY (" + MA_LOAI_SACH_S + ")" + " REFERENCES " + TABLE_LOAI_SACH + "(" + MA_LOAI_SACH_LS + "))";
    //TẠO TABLE LOẠI SÁCH
    private static final String TAO_TABLE_LOAI_SACH = ""
            + "CREATE TABLE " + TABLE_LOAI_SACH + "( "
            + MA_LOAI_SACH_LS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TEN_LOAI_SACH_LS + " TEXT NOT NULL )";
    //TẠO TABLE THÀNH VIÊN
//    private static final String TAO_TABLE_THANH_VIEN = ""
//            + "CREATE TABLE " + TABLE_THANH_VIEN + "( "
//            + MA_THANH_VIEN_TV + " INTEGER PRIMARY KEY, "
//            + HO_TEN_TV + " TEXT NOT NULL, "
//            + DIA_CHI_TV + " TEXT NOT NULL, "
//            + SO_DIEN_THOAI_TV + " TEXT NOT NULL, "
//            + EMAIL_TV + " TEXT NOT NULL )";
    //TẠO BẢNG MƯỢN TRẢ SÁCH
    private static final String TAO_TABLE_MUON_TRA = ""
            + "CREATE TABLE " + TABLE_MUON_TRA + "( "
            + MA_MUON_TRA_MTS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MA_SACH_MTS + " INTEGER NOT NULL, "
            + MA_USER_MTS + " INTEGER NOT NULL, "
            + NGAY_MUON_MTS + " TEXT, "
            + NGAY_TRA_MTS + " TEXT, "
            + "FOREIGN KEY (" + MA_SACH_MTS +")" + " REFERENCES " + TABLE_SACH + "(" + MA_SACH_S + "), "
            + "FOREIGN KEY (" + MA_USER_MTS + ")" + " REFERENCES " + TABLE_USER + "(" + ID_USER + "))";
    //TẠO BẢNG NHÂN VIÊN
//    private static final String TAO_TABLE_NHAN_VIEN = ""
//            + "CREATE TABLE " + TABLE_NHAN_VIEN + "( "
//            + MA_NHAN_VIEN_NV + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + USERNAME + " TEXT UNIQUE, "
//            + PASSWORD + " TEXT NOT NULL, "
//            + TEN_NHAN_VIEN_NV + " TEXT NOT NULL, "
//            + DIA_CHI_NV + " TEXT NOT NULL, "
//            + EMAIL_NV + " TEXT NOT NULL, "
//            + SO_DIEN_THOAI_NV + " TEXT NOT NULL )";
    //TẠO BẢNG LIÊN KẾT
//    private static final String TAO_TABLE_LIEN_KET = ""
//            + "CREATE TABLE " + TABLE_LIEN_KET + "( "
//            + MA_SACH_LK + " INTEGER NOT NULL, "
//            + MA_THANH_VIEN_LK + " INTEGER NOT NULL, "
//            + "PRIMARY KEY(" + MA_SACH_LK + "," + MA_THANH_VIEN_LK + "), "
//            + "FOREIGN KEY(" + MA_SACH_LK + ")" + " REFERENCES " + TABLE_SACH + "(" + MA_SACH_S + "), "
//            + "FOREIGN KEY(" + MA_THANH_VIEN_LK + ")" + " REFERENCES " + TABLE_THANH_VIEN + "(" + MA_THANH_VIEN_TV + "))";

    public DBHelper(Context context)
    {
        super(context, TEN_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TAO_TABLE_SACH);
        sqLiteDatabase.execSQL(TAO_TABLE_LOAI_SACH);
//        sqLiteDatabase.execSQL(TAO_TABLE_THANH_VIEN);
//        sqLiteDatabase.execSQL(TAO_TABLE_NHAN_VIEN);
        sqLiteDatabase.execSQL(TAO_TABLE_MUON_TRA);
//        sqLiteDatabase.execSQL(TAO_TABLE_LIEN_KET);
        sqLiteDatabase.execSQL(TAO_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
