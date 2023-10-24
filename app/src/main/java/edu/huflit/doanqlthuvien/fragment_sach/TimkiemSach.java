package edu.huflit.doanqlthuvien.fragment_sach;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDMSach;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterTimKiemSach;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;

public class TimkiemSach extends Fragment {
    View view;
    public static ListView listView;
    public static ArrayList<Sach> saches;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    ImageView back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tim_kiem_sach, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        saches = new ArrayList<>();
        anhXa();
        capNhatDuLieuDSach();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences getUser = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                String get_uername = getUser.getString("username",null);
                User user = database.checkRole(get_uername);
                if (user.getRole_user().equals("admin"))
                {
                    manHinhChinh.gotoManHinhChinhAdmin();
                }
                else
                {
                    manHinhChinh.gotoManHinhUser();
                }
            }
        });

        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.search_back);
        listView = (ListView) view.findViewById(R.id.lv_search);
    }
    public void capNhatDuLieuDSach()
    {
        if (saches == null)
        {
            saches = new ArrayList<Sach>();
        }
        else
        {
            saches.removeAll(saches);
        }
        SharedPreferences name_sach = getActivity().getSharedPreferences("search_sach", Context.MODE_PRIVATE);
        String ten_sach = name_sach.getString("name_sach", null);
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuSachByName(ten_sach);
        if (cursor != null)
        {
            int ten_sach_index = cursor.getColumnIndex(DBHelper.TEN_SACH_S);
            int ma_sach_index = cursor.getColumnIndex(DBHelper.MA_SACH_S);
            int img_sach_index = cursor.getColumnIndex(DBHelper.IMAGE_SACH);
            while (cursor.moveToNext())
            {
                Sach sach = new Sach();
                if (ten_sach_index != -1)
                {
                    sach.setTen_sach_s(cursor.getString(ten_sach_index));
                }
                if (ma_sach_index != -1)
                {
                    sach.setMa_sach_s(cursor.getInt(ma_sach_index));
                }
                if (img_sach_index != -1)
                {
                    sach.setImage_sach(cursor.getBlob(img_sach_index));
                }
                saches.add(sach);
            }
        }
        if (saches != null)
        {
            listView.setAdapter(new MyAdapterTimKiemSach(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public void capNhatListView()
    {
        if (saches == null)
        {
            saches = new ArrayList<Sach>();
        }
        else
        {
            saches.removeAll(saches);
        }
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuSach();
        if (cursor != null)
        {
            int ten_sach_index = cursor.getColumnIndex(DBHelper.TEN_SACH_S);
            int ma_sach_index = cursor.getColumnIndex(DBHelper.MA_SACH_S);
            int img_sach_index = cursor.getColumnIndex(DBHelper.IMAGE_SACH);
            while (cursor.moveToNext())
            {
                Sach sach = new Sach();
                if (ten_sach_index != -1)
                {
                    sach.setTen_sach_s(cursor.getString(ten_sach_index));
                }
                if (ma_sach_index != -1)
                {
                    sach.setMa_sach_s(cursor.getInt(ma_sach_index));
                }
                if (img_sach_index != -1)
                {
                    sach.setImage_sach(cursor.getBlob(img_sach_index));
                }
                saches.add(sach);
            }
        }
        if (saches != null)
        {
            listView.setAdapter(new MyAdapterDMSach(getActivity()));
        }
    }
}
