package edu.huflit.doanqlthuvien.fragment_muon_tra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.R;

public class ChiTietMuonTra extends Fragment {
    View view;
    ManHinhChinh manHinhChinh;
    MyDatabase database;
    TextView tv_ten_sach, tv_ten_doc_gia, tv_username;
    ImageView back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chi_tiet_sach, container, false);
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());



        return view;
    }
    public void anhXa()
    {

    }
}
