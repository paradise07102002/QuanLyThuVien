package edu.huflit.doanqlthuvien.fragment_sach;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.LoaiSach;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.R;

public class DetailSach extends Fragment {
    View view;
    View background;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    ImageView back, img_sach;
    TextView dau_sach, ten_sach, tac_gia, nha_xb, nam_xb, trang_thai, mo_ta_sach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_sach, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        setColorTextView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhSach();
            }
        });
        showDuLieuSach();
        return view;
    }
    public void anhXa()
    {
        background = (View) view.findViewById(R.id.background_detail_sach);

        back = (ImageView) view.findViewById(R.id.detail_sach_back);
        img_sach = (ImageView) view.findViewById(R.id.detail_sach_img);

        dau_sach = (TextView) view.findViewById(R.id.detail_sach_spinner);
        ten_sach = (TextView) view.findViewById(R.id.detail_sach_name);
        tac_gia = (TextView) view.findViewById(R.id.detail_sach_tg);
        nha_xb = (TextView) view.findViewById(R.id.detail_sach_nhaxb);
        nam_xb = (TextView) view.findViewById(R.id.detail_sach_namxb);
        trang_thai = (TextView) view.findViewById(R.id.detail_sach_tt);
        mo_ta_sach = (TextView) view.findViewById(R.id.detail_sach_mo_ta);
    }
    public Sach layDuLieu()
    {
        SharedPreferences lay_ma_sach = getActivity().getSharedPreferences("lay_ma_sach", Context.MODE_PRIVATE);
        int ma_sach = lay_ma_sach.getInt("ma_sach", 0);
        Cursor cursor = database.layDuLieuSachByID(ma_sach);
        Sach sach = new Sach();
        if (cursor != null)
        {
            int ma_dau_sach_index = cursor.getColumnIndex(DBHelper.MA_LOAI_SACH_S);

            int ten_sach_index = cursor.getColumnIndex(DBHelper.TEN_SACH_S);
            int ten_tg_index = cursor.getColumnIndex(DBHelper.TAC_GIA_S);
            int nha_xb_index = cursor.getColumnIndex(DBHelper.NHA_XUAT_BAN_S);
            int nam_xb_index = cursor.getColumnIndex(DBHelper.NAM_XUAT_BAN_S);
            int trang_thai_index = cursor.getColumnIndex(DBHelper.TRANG_THAI_S);
            int mo_ta_index = cursor.getColumnIndex(DBHelper.MO_TA_SACH);
            int img_sach_index = cursor.getColumnIndex(DBHelper.IMAGE_SACH);

            cursor.moveToFirst();
            sach.setMa_loai_sach_s(cursor.getInt(ma_dau_sach_index));
            sach.setTen_sach_s(cursor.getString(ten_sach_index));
            sach.setTac_gia_s(cursor.getString(ten_tg_index));
            sach.setNha_xuat_ban_s(cursor.getString(nha_xb_index));
            sach.setNam_xuat_ban_s(cursor.getInt(nam_xb_index));
            sach.setTrang_thai_s(cursor.getInt(trang_thai_index));
            sach.setMo_ta_sach(cursor.getString(mo_ta_index));
            sach.setImage_sach(cursor.getBlob(img_sach_index));

            cursor.close();
        }
        return sach;
    }
    public void showDuLieuSach()
    {
        Sach sach = layDuLieu();
        LoaiSach loaiSach = new LoaiSach();

        //Ta có mã đầu sách, truy vấn lấy tên đầu sách
        String ten_dau_sach = "";
        Cursor cursor = database.layDuLieuDauSachByID(sach.getMa_loai_sach_s());
        if (cursor != null)
        {
            int ten_dau_sach_index = cursor.getColumnIndex(DBHelper.TEN_LOAI_SACH_LS);
            cursor.moveToFirst();
            loaiSach.setLoai_sach_ls(cursor.getString(ten_dau_sach_index));
            cursor.close();
        }
//        Đổ dữ liệu lên TextView
        dau_sach.setText(loaiSach.getLoai_sach_ls());
        ten_sach.setText(sach.getTen_sach_s());
        tac_gia.setText(sach.getTac_gia_s());
        nha_xb.setText(sach.getNha_xuat_ban_s());
        nam_xb.setText(Integer.toString(sach.getNam_xuat_ban_s()));
        mo_ta_sach.setText(sach.getMo_ta_sach());
        int tt = sach.getTrang_thai_s();
        if (tt == 0)
        {
            trang_thai.setText("Chưa cho mượn");
        }
        else
        {
            trang_thai.setText("Đã cho mượn");
        }
//        byte[] bytes = ManHinhSach.saches.get(i).getImage_sach();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        imageView = (ImageView) view.findViewById(R.id.dms_image);
//        imageView.setImageBitmap(bitmap);
        byte[] bytes = sach.getImage_sach();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img_sach.setImageBitmap(bitmap);
    }
    public void setColorTextView()
    {
        int[] colors = {
                Color.parseColor("#99FFFF"),
                Color.parseColor("#CC00FF"),
                Color.parseColor("#CC99FF"),
                Color.parseColor("#CCFFFF")
        };
        // Tạo một `ValueAnimator` để chuyển đổi màu sắc
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colors[0], colors[1], colors[2], colors[3]);
        valueAnimator.setDuration(6000);
        //lặp lại vô hạn
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // Bắt đầu `ValueAnimator`
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                // Lấy giá trị màu hiện tại
                int color = (int) valueAnimator.getAnimatedValue();
                // Đặt màu cho textview
                background.setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });
    }
}
