package edu.huflit.doanqlthuvien.fragment_muon_tra;

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
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterDMSach;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterQLMuonTra;
import edu.huflit.doanqlthuvien.MyAdapter.MyAdapterShowSach;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.MuonTraSach;
import edu.huflit.doanqlthuvien.OOP.Sach;
import edu.huflit.doanqlthuvien.R;

public class QuanLySachMuon extends Fragment {
    View view;
    ImageView back;
    public static ListView listView;
    public static ArrayList<MuonTraSach> muonTraSaches;
    ManHinhChinh manHinhChinh;
    MyDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quan_ly_sach_muon, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        muonTraSaches = new ArrayList<>();
        capNhatDuLieuDSach();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoManHinhChinhAdmin();
            }
        });
        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.ql_sach_back);
        listView = (ListView) view.findViewById(R.id.lv_ql_sach);
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
        Cursor cursor = database.layDuLieuMuonTraSach();
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
            listView.setAdapter(new MyAdapterQLMuonTra(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences lay_ma_muon_tra = getActivity().getSharedPreferences("lay_ma_sach_muon_tra", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = lay_ma_muon_tra.edit();
                int ma_muon_tra = muonTraSaches.get(i).getMa_muon_tra_mts();
                editor.putInt("ma_muon_tra", ma_muon_tra);
                editor.apply();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.icon_question);
                builder.setMessage("Trả sách");
                builder.setNegativeButton("Trả sách", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences lay_ma_muon_tra = getActivity().getSharedPreferences("lay_ma_sach_muon_tra", Context.MODE_PRIVATE);
                        database.suaTrangThaiSach(lay_ma_muon_tra.getInt("ma_muon_tra", 0), 0);
                        database.xoaMuonTraSach(lay_ma_muon_tra.getInt("ma_muon_tra", 0));
                        capNhatDuLieuDSach();
                        Toast.makeText(getActivity(), "Trả sách thành công", Toast.LENGTH_LONG).show();
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

}
