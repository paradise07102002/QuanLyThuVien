package edu.huflit.doanqlthuvien.fragment_muon_tra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import edu.huflit.doanqlthuvien.DBHelper;
import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.MuonTraSach;
import edu.huflit.doanqlthuvien.R;
import edu.huflit.doanqlthuvien.fragments.DatePickerFragment;

public class ManHinhCreatePhieuMuon extends Fragment{
    View view;
    ImageView back;
    TextView username_muon, ma_sach_muon;
    private TextView ngay_muon, ngay_tra;
    Button btn_create;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.man_hinh_create_phieu_muon, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        //Hiển thị ngày mượn ngày trả
        showNgay();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoMuonTra();
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = kiemTraNhapThongTin();//Kiểm tra đã nhập thông tin chưa
                if (check == false)
                {
                    return;//Kết thúc
                }
                if (checkUername()==false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Tên tài khoản không tồn tại");
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                    return;
                }
                if (checkMaSach() == false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Mã sách không tồn tại");
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                    return;
                }
                else
                {

                    //Lấy id user thông qua username
                    Cursor cursor = database.getUserByUsername(username_muon.getText().toString().trim());
                    int id_user_index = cursor.getColumnIndex(DBHelper.ID_USER);
                    cursor.moveToFirst();
                    int id_user = cursor.getInt(id_user_index);

                    MuonTraSach muonTraSach = new MuonTraSach();
                    muonTraSach.setMa_sach_mts(Integer.parseInt(ma_sach_muon.getText().toString().trim()));
                    muonTraSach.setMa_user_mts(id_user);
                    muonTraSach.setNgay_muon_mts(ngay_muon.getText().toString().trim());
                    muonTraSach.setNgay_tra_mts(ngay_tra.getText().toString().trim());
                    database.addMuonTraSach(muonTraSach);
                    //tHÔNG BÁO MƯỢN THÀNH CÔNG
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Tạo phiếu mượn thành công");
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                }
            }
        });
        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.create_phieu_muon_back);
        username_muon = (EditText) view.findViewById(R.id.phieu_muon_username);
        ma_sach_muon = (EditText) view.findViewById(R.id.phieu_muon_ma_sach);
        ngay_muon = (TextView) view.findViewById(R.id.phieu_muon_ngay_muon);
        ngay_tra = (TextView) view.findViewById(R.id.phieu_muon_ngay_tra);
        btn_create = (Button) view.findViewById(R.id.phieu_muon_btn);
    }
    public void showNgay()
    {
        //Ngày tháng năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        ngay_muon.setText(day + "-" + month + "-" + year);
        //Tính ngày trả
        Date date_hien_tai = new Date();
        Calendar day_tra = Calendar.getInstance();//lấy ngày hiện tại
        day_tra.setTime(date_hien_tai);
        //Thêm số ngày cần
        day_tra.add(Calendar.DATE, 5);//Mượn 5 ngày sẽ trả sách
        //Lấy ngày Calendar
        int day1 = day_tra.get(Calendar.DAY_OF_MONTH);
        int month1 = day_tra.get(Calendar.MONTH);
        int year1 = day_tra.get(Calendar.YEAR);
        ngay_tra.setText(day1 + "-" + month1 + "-" + year1);

    }
    public boolean kiemTraNhapThongTin()
    {
        if (username_muon.length() == 0 )
        {
            String p = "<font color='#FF0000'>Vui lòng nhập tên tài khoản</font>";
            username_muon.setHint(Html.fromHtml(p));
            return false;
        } else if (ma_sach_muon.length() == 0)
        {
            String p = "<font color='#FF0000'>Vui lòng nhập mã sách mượn</font>";
            ma_sach_muon.setHint(Html.fromHtml(p));
            return false;
        }
        return true;
    }
    public boolean checkUername()
    {
        Cursor cursor = database.getUserByUsername(username_muon.getText().toString().trim());
        if (cursor == null || cursor.getCount() == 0)
        {
            return false;
        }
        return true;
    }
    public boolean checkMaSach()
    {
        Cursor cursor = database.getSachByMaSach(Integer.parseInt(ma_sach_muon.getText().toString().trim()));
        if (cursor == null || cursor.getCount() == 0)
        {
            return false;
        }
        return true;
    }
}
