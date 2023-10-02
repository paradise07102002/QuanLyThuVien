package edu.huflit.doanqlthuvien.fragment_dau_sach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDMDauSach;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.LoaiSach;
import edu.huflit.doanqlthuvien.R;

public class ManHinhDauSach extends Fragment {
    View view;
    ImageView next_add_dau_sach, img_back;
    TextView tv_thong_bao_null;
    public static ManHinhChinh manHinhChinh;
    MyDatabase database;
    public static Context context;
    public static ListView listView;
    public static ArrayList<LoaiSach> loaiSaches;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.man_hinh_dau_sach, container, false);
        context = getContext();
        database = new MyDatabase(context);
        anhXa();
        manHinhChinh = (ManHinhChinh) getActivity();
        //ẤN VÀO BUTTON ĐỂ TỚI MÀN HÌNH THÊM ĐẦU SÁCH
        next_add_dau_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhAddDauSach();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhChinhAdmin();
            }
        });
        loaiSaches = new ArrayList<LoaiSach>();
        capNhatDuLieuDSach();
        listView.invalidateViews();
        return view;
    }
    public void anhXa()
    {
        next_add_dau_sach = (ImageView) view.findViewById(R.id.next_add_dau_sach);
        img_back = (ImageView) view.findViewById(R.id.man_hinh_dau_sach_back);
        listView = (ListView) view.findViewById(R.id.lv_dau_sach);
        tv_thong_bao_null = (TextView) view.findViewById(R.id.tv_dau_sach_null);
    }
    public void capNhatDuLieuDSach()
    {
        database = new MyDatabase(context);
        Cursor cursor = database.layDuLieuDauSach();
        if (cursor != null)
        {
            int ten_dau_sach_index = cursor.getColumnIndex(DBHelper.TEN_LOAI_SACH_LS);
            int ma_dau_sach_index = cursor.getColumnIndex(DBHelper.MA_LOAI_SACH_LS);
            while (cursor.moveToNext())
            {
                LoaiSach loaiSach = new LoaiSach();
                if (ten_dau_sach_index != -1)
                {
                    loaiSach.setLoai_sach_ls(cursor.getString(ten_dau_sach_index));
                }
                if (ma_dau_sach_index != -1)
                {
                    loaiSach.setMa_loai_sach_ls(cursor.getInt(ma_dau_sach_index));
                }
                loaiSaches.add(loaiSach);
            }
            cursor.close();
        }
        if (loaiSaches != null)
        {
            listView.setAdapter(new MyAdapterDMDauSach(context));
        }
        else
        {
            tv_thong_bao_null.setText("Đầu sách rỗng");
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //SharedPreferences lay_id_dau_sach =getSharedPreferences("id_dau_sach", MODE_PRIVATE);
                //SharedPreferences.Editor editor = lay_id_dau_sach.edit();
                //int id_dau_sach = loaiSaches.get(i).getMa_loai_sach_ls();
                //editor.putInt("ma_dau_sach", id_dau_sach);
                //editor.apply();
                //Intent next_chi_tiet_dau_sach = new Intent(getApplicationContext(), DauSachChiTiet.class);
                //startActivity(next_chi_tiet_dau_sach);

            }
        });
    }
    public static void xoaDauSach(int ma_dau_sach)
    {
        MyDatabase database1 = new MyDatabase(context);
        database1.xoaDauSach(ma_dau_sach);
    }
}
