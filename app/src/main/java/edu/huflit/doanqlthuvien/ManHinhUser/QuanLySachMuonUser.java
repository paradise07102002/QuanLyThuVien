package edu.huflit.doanqlthuvien.ManHinhUser;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDMSach;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterQLMuonTra;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterQLSachMuon;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterQLSachMuonUser;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.MuonTraSach;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.R;

public class QuanLySachMuonUser extends Fragment {
    View view;
    public static ListView listView;
    public static ArrayList<MuonTraSach> muonTraSaches;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    ImageView back;
    int id_user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quan_ly_sach_muon_user, container, false);
        database = new MyDatabase(getActivity());
        muonTraSaches = new ArrayList<>();
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = get_user.getString("username", null);
        Cursor cursor = database.getUserByUsername(username);
        int id_user_index = cursor.getColumnIndex(DBHelper.ID_USER);
        cursor.moveToFirst();
        id_user = cursor.getInt(id_user_index);
        capNhatDuLieuDSach();
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
        back = (ImageView) view.findViewById(R.id.sach_muon_user_back);
        listView = (ListView) view.findViewById(R.id.lv_sach_muon_user);
    }
    public void capNhatDuLieuDSach()
    {
        if (muonTraSaches == null)
        {
            muonTraSaches = new ArrayList<MuonTraSach>();
        }
        else
        {
            muonTraSaches.removeAll(muonTraSaches);
        }
        Cursor cursor = database.layDuLieuMuonTraSachByMaUser(id_user);
        if (cursor != null)
        {
            int ma_muon_tra_index = cursor.getColumnIndex(DBHelper.MA_MUON_TRA_MTS);
            int ma_sach_index = cursor.getColumnIndex(DBHelper.MA_SACH_MTS);
            int ma_doc_gia_index = cursor.getColumnIndex(DBHelper.MA_USER_MTS);
            while (cursor.moveToNext())
            {
                    MuonTraSach muonTraSach = new MuonTraSach();
                    if (ma_muon_tra_index != -1)
                    {
                        muonTraSach.setMa_muon_tra_mts(cursor.getInt(ma_muon_tra_index));
                    }
                    if (ma_sach_index != -1)
                    {
                        muonTraSach.setMa_sach_mts(cursor.getInt(ma_sach_index));
                    }
                    if (ma_doc_gia_index != -1)
                    {
                        muonTraSach.setMa_user_mts(cursor.getInt(ma_doc_gia_index));
                    }
                    muonTraSaches.add(muonTraSach);
            }
        }
            if (muonTraSaches != null)
            {
                listView.setAdapter(new MyAdapterQLSachMuonUser(getActivity()));
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });
        }
    }
