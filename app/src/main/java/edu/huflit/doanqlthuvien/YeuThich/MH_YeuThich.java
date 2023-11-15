package edu.huflit.doanqlthuvien.YeuThich;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import edu.huflit.doanqlthuvien.MyAdapter.ShowYeuThich;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.YeuThich;
import edu.huflit.doanqlthuvien.R;

public class MH_YeuThich extends Fragment {
    View view;
    ManHinhChinh manHinhChinh;
    MyDatabase database;
    ImageView back;
    public static ListView listView;
    public static ArrayList<YeuThich> yeuThiches;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.yeu_thich, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        capNhat();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhUser();
            }
        });

        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.yeu_thich_back);
        listView = (ListView) view.findViewById(R.id.lv_yeu_thich);
    }
    public void capNhat()
    {
        if (yeuThiches == null)
        {
            yeuThiches = new ArrayList<YeuThich>();
        }
        else
        {
            yeuThiches.removeAll(yeuThiches);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        Cursor get_user = database.getUserByUsername(sharedPreferences.getString("username", null));
        int id_user_index = get_user.getColumnIndex(DBHelper.ID_USER);
        get_user.moveToFirst();
        int id_user = get_user.getInt(id_user_index);
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuYeuThichByIDUser(id_user);
        if (cursor != null)
        {
            int ma_yeu_thich_index = cursor.getColumnIndex(DBHelper.MA_YEU_THICH);
            int ma_sach_yt_index = cursor.getColumnIndex(DBHelper.MA_SACH_YT);
            int ma_user_yt_index = cursor.getColumnIndex(DBHelper.MA_USER_YT);

            while (cursor.moveToNext())
            {
                YeuThich yeuThich = new YeuThich();
                if (ma_yeu_thich_index != -1)
                {
                    yeuThich.setMa_yeu_thich(cursor.getInt(ma_yeu_thich_index));
                }
                if (ma_sach_yt_index != -1)
                {
                    yeuThich.setMa_sach_yt(cursor.getInt(ma_sach_yt_index));
                }
                if (ma_user_yt_index != -1)
                {
                    yeuThich.setMa_user_yt(cursor.getInt(ma_user_yt_index));
                }
                yeuThiches.add(yeuThich);
            }
        }
        if (yeuThiches != null)
        {
            listView.setAdapter(new ShowYeuThich(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences get_ma_yt = getActivity().getSharedPreferences("lay_ma_yt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = get_ma_yt.edit();
                editor.putInt("ma_yt", yeuThiches.get(i).getMa_yeu_thich());
                editor.apply();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Yêu thích");
                builder.setNegativeButton("Xóa khỏi yêu thích", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences get_ma_yt = getActivity().getSharedPreferences("lay_ma_yt", Context.MODE_PRIVATE);
                        database.xoaYeuThich(get_ma_yt.getInt("ma_yt", -1));
                        Toast.makeText(getActivity(), "Đã xóa khỏi yêu thích", Toast.LENGTH_LONG).show();
                        capNhat2();
                    }
                });
                builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
    //Cập nhật lại listview khi xóa yêu thích
    public void capNhat2()
    {
        if (yeuThiches == null)
        {
            yeuThiches = new ArrayList<YeuThich>();
        }
        else
        {
            yeuThiches.removeAll(yeuThiches);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        Cursor get_user = database.getUserByUsername(sharedPreferences.getString("username", null));
        int id_user_index = get_user.getColumnIndex(DBHelper.ID_USER);
        get_user.moveToFirst();
        int id_user = get_user.getInt(id_user_index);
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuYeuThichByIDUser(id_user);
        if (cursor != null)
        {
            int ma_yeu_thich_index = cursor.getColumnIndex(DBHelper.MA_YEU_THICH);
            int ma_sach_yt_index = cursor.getColumnIndex(DBHelper.MA_SACH_YT);
            int ma_user_yt_index = cursor.getColumnIndex(DBHelper.MA_USER_YT);

            while (cursor.moveToNext())
            {
                YeuThich yeuThich = new YeuThich();
                if (ma_yeu_thich_index != -1)
                {
                    yeuThich.setMa_yeu_thich(cursor.getInt(ma_yeu_thich_index));
                }
                if (ma_sach_yt_index != -1)
                {
                    yeuThich.setMa_sach_yt(cursor.getInt(ma_sach_yt_index));
                }
                if (ma_user_yt_index != -1)
                {
                    yeuThich.setMa_user_yt(cursor.getInt(ma_user_yt_index));
                }
                yeuThiches.add(yeuThich);
            }
        }
        if (yeuThiches != null)
        {
            listView.setAdapter(new ShowYeuThich(getActivity()));
        }
    }
}
