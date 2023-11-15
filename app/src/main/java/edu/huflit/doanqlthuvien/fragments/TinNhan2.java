package edu.huflit.doanqlthuvien.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDMDauSach;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterTinNhan;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.Chat;
import edu.huflit.doanqlthuvien.OOP.LoaiSach;
import edu.huflit.doanqlthuvien.R;

public class TinNhan2 extends Fragment {
    View view;
    EditText edt_tin_nhan;
    ImageView back, send;
    MyDatabase database;
    public static ListView listView;
    public static ArrayList<Chat> chats;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tin_nhan2, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhUser();
            }
        });
        capNhatDuLieuChat();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_user;
                //Lấy id user
                SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                Cursor get_id_user = database.getUserByUsername(get_user.getString("username", null));
                if (get_id_user != null)
                {
                    int id_index = get_id_user.getColumnIndex(DBHelper.ID_USER);
                    get_id_user.moveToFirst();
                    id_user = get_id_user.getInt(id_index);

                    Chat chat = new Chat();
                    chat.setId_chat_user(id_user);
                    //Lấy id admin
                    chat.setId_chat_admin(1);
                    chat.setId_gui(id_user);
                    chat.setNoi_dung(edt_tin_nhan.getText().toString().trim());
                    //Lấy tời gian hiện tại
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);

                    String a = String.format("%02d", 10);
                    String b = String.format("%02d", 10);
                    String c = String.format("%04d", 2023);

                    String thoi_gian = day + "-" + month + "-" + year;
                    chat.setThoi_gian_gui(thoi_gian);
                    chat.setCount_new_messsage(1);
                    database.addChat(chat);
                    capNhatDuLieuChat();
                    edt_tin_nhan.setText("");
                }

            }
        });

        return view;
    }
    public void anhXa()
    {
        listView = (ListView) view.findViewById(R.id.lv_tin_nhan2);
        edt_tin_nhan = (EditText) view.findViewById(R.id.edt_tin_nhan2);
        back = (ImageView) view.findViewById(R.id.tin_nhan2_back);
        send = (ImageView) view.findViewById(R.id.img_tin_nhan2);
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

        Cursor cursor = database.layDuLieuChat(id_user);
        if (cursor != null)
        {
            int id_chat_index = cursor.getColumnIndex(DBHelper.ID_TIN_NHAN);
            int noi_dung_index = cursor.getColumnIndex(DBHelper.NOI_DUNG);
            int thoi_gian_index = cursor.getColumnIndex(DBHelper.THOI_GIAN_GUI);
            int id_nguoi_gui_index = cursor.getColumnIndex(DBHelper.ID_CHAT_USER);
            int id_nguoi_nhan_index = cursor.getColumnIndex(DBHelper.ID_CHAT_ADMIN);
            int id_gui_index = cursor.getColumnIndex(DBHelper.ID_GUI);
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
                    chat.setId_chat_user(cursor.getInt(id_nguoi_gui_index));
                }
                if (id_nguoi_nhan_index != -1)
                {
                    chat.setId_chat_admin(cursor.getInt(id_nguoi_nhan_index));
                }
                if (id_gui_index != -1)
                {
                    chat.setId_gui(cursor.getInt(id_gui_index));
                }
                chats.add(chat);
            }
        }
        if (chats != null)
        {
            listView.setAdapter(new MyAdapterTinNhan(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
