package edu.huflit.doanqlthuvien.ManHinhUser;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterShowSach;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.OOP.YeuThich;
import edu.huflit.doanqlthuvien.R;

public class ManHinhUser extends Fragment {
    View view;
    MyDatabase database;
    public static ListView listView;
    public static ArrayList<Sach> saches;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.man_hinh_user, container, false);
        anhXa();
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());
        saches = new ArrayList<>();
        capNhatDuLieuDSach();

        //Kiểm tra có sách mượn đến hẹn trả hay không
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean check_login = get_user.getBoolean("is_login", false);
        if (check_login)
        {
            int id_user;
            String get_user_name = get_user.getString("username", null);
            Cursor lay_id_user = database.getUserByUsername(get_user_name);
            if (lay_id_user != null)
            {
                int id_user_index = lay_id_user.getColumnIndex(DBHelper.ID_USER);
                lay_id_user.moveToFirst();
                id_user = lay_id_user.getInt(id_user_index);

                Cursor cursor = database.getNgayTraSach(id_user);
            }
        }
        return view;
    }
    public void anhXa()
    {
        listView = (ListView) view.findViewById(R.id.lv_show_sach_for_user);
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
            listView.setAdapter(new MyAdapterShowSach(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences lay_ma_sach = getActivity().getSharedPreferences("lay_ma_sach", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = lay_ma_sach.edit();
                int ma_sach = saches.get(i).getMa_sach_s();
                editor.putInt("ma_sach", ma_sach);
                editor.apply();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sách");
                builder.setNegativeButton("Xem chi tết", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        manHinhChinh.gotoDetailSach();
                    }
                });
                builder.setPositiveButton("Thêm vào yêu thích", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences check_login = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                        if (check_login.getBoolean("is_login", false))
                        {
                            //Lấy mã user
                            int id_user, id_sach;
                            SharedPreferences get_user = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                            String get_user_name = get_user.getString("username", null);
                            Cursor lay_id_user = database.getUserByUsername(get_user_name);

                            int id_user_index = lay_id_user.getColumnIndex(DBHelper.ID_USER);
                            lay_id_user.moveToFirst();
                            id_user = lay_id_user.getInt(id_user_index);
                            //Lấy mã sách
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("lay_ma_sach", Context.MODE_PRIVATE);
                            id_sach = sharedPreferences.getInt("ma_sach", -1);

                            //KIỂM TRA SÁCH ĐÃ ĐƯỢC THÊM VÀO YÊU THÍCH CHƯA
                            boolean check_yt = database.checkYeuThich(id_sach, id_user);
                            if (check_yt == true)
                            {
                                Toast.makeText(getActivity(), "Đã có trong yêu thích", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                YeuThich yeuThich = new YeuThich();
                                yeuThich.setMa_sach_yt(id_sach);
                                yeuThich.setMa_user_yt(id_user);
                                database.addYeuThich(yeuThich);
                                Toast.makeText(getActivity(), "Đã thêm vào yêu thích", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }
}
