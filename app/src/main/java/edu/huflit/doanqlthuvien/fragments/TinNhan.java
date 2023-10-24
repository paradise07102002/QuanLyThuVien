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
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDanhSachTinNhan;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterTinNhan;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.Chat;
import edu.huflit.doanqlthuvien.R;

public class TinNhan extends Fragment {
    View view;
    ImageView back;
    MyDatabase database;
    public static ListView listView;
    public static ArrayList<Chat> chats;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tin_nhan, container, false);
        database = new MyDatabase(getActivity());
        chats = new ArrayList<Chat>();

        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.tin_nhan_back);
        listView = (ListView) view.findViewById(R.id.lv_tin_nhan);
    }
    public void capNhatDuLieuChat()
    {
        if (chats == null)
        {
            chats = new ArrayList<Chat>();
        }
        else
        {
            chats.removeAll(chats);
        }
        database = new MyDatabase(getActivity());
        //Lấy id user
        int id_user;
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        Cursor get_id_user = database.getUserByUsername(get_user.getString("username", null));
        int id_index = get_id_user.getColumnIndex(DBHelper.ID_USER);
        get_id_user.moveToFirst();
        id_user = get_id_user.getInt(id_index);

        Cursor cursor = database.layDuLieuFullChat();
        if (cursor != null)
        {
            int id_chat_index = cursor.getColumnIndex(DBHelper.ID_TIN_NHAN);
            int noi_dung_index = cursor.getColumnIndex(DBHelper.NOI_DUNG);
            int thoi_gian_index = cursor.getColumnIndex(DBHelper.THOI_GIAN_GUI);
            int id_nguoi_gui_index = cursor.getColumnIndex(DBHelper.ID_NGUOI_GUI);
            int id_nguoi_nhan_index = cursor.getColumnIndex(DBHelper.ID_NGUOI_NHAN);
            while (cursor.moveToNext())
            {
                Chat chat = new Chat();
                if (id_chat_index != -1)
                {
                    chat.setId_tin_nhan(cursor.getInt(id_chat_index));
                }
                if (noi_dung_index != -1)
                {
                    chat.setNoi_dung(cursor.getString(noi_dung_index));
                }
                if (thoi_gian_index != -1)
                {
                    chat.setThoi_gian_gui(cursor.getString(thoi_gian_index));
                }
                if (id_nguoi_gui_index != -1)
                {
                    chat.setId_nguoi_gui(cursor.getInt(id_nguoi_gui_index));
                }
                if (id_nguoi_nhan_index != -1)
                {
                    chat.setId_nguoi_nhan(cursor.getInt(id_nguoi_nhan_index));
                }
                chats.add(chat);
            }
        }
        if (chats != null)
        {
            listView.setAdapter(new MyAdapterDanhSachTinNhan(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
