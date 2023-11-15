package edu.huflit.doanqlthuvien.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDanhSachTinNhan;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterTinNhan;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.Chat;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;

public class TinNhan extends Fragment {
    View view;
    ImageView back;
    MyDatabase database;
    public static ListView listView;
    public static ArrayList<User> users;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tin_nhan, container, false);
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());
        anhXa();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhChinhAdmin();
            }
        });
        users = new ArrayList<User>();
        capNhatDuLieuChat();
        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.tin_nhan_back);
        listView = (ListView) view.findViewById(R.id.lv_tinnhan);
    }
    public void capNhatDuLieuChat()
    {
        if (users == null)
        {
            users = new ArrayList<User>();
        }
        else
        {
            users.removeAll(users);
        }
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuUserChat();
        if (cursor != null)
        {
            int id_user_index = cursor.getColumnIndex(DBHelper.ID_USER);
            int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);

            while (cursor.moveToNext())
            {
                User user = new User();
                if (id_user_index != -1)
                {
                    user.setId_user(cursor.getInt(id_user_index));
                }
                if (username_index != -1)
                {
                    user.setUsername_user(cursor.getString(username_index));
                }
                users.add(user);
            }
        }
        cursor.close();
        if (users != null)
        {
            listView.setAdapter(new MyAdapterDanhSachTinNhan(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("tin_nhan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id_nguoi_gui", users.get(i).getId_user());
                editor.apply();
                manHinhChinh.gotoChatAdmin();
                database.daXemTinNhan(users.get(i).getId_user());
                manHinhChinh.updateMessage();
            }
        });
    }
}
