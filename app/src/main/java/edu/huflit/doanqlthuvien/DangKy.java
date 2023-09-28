package edu.huflit.doanqlthuvien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.huflit.doanqlthuvien.OOP.NhanVien;

public class DangKy extends AppCompatActivity {
    EditText ten_nv, dc, email, sdt, username, password;
    Button dang_ky;
    MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        database = new MyDatabase(this);
        anhXa();
        dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemTraNhapThongTin();
            }
        });
    }
    public void anhXa()
    {
        ten_nv = (EditText) findViewById(R.id.ten_nv);
        dc = (EditText) findViewById(R.id.dia_chi);
        email = (EditText) findViewById(R.id.email);
        sdt = (EditText) findViewById(R.id.sdt);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);

        dang_ky = (Button) findViewById(R.id.dang_ky);
    }
    public void kiemTraNhapThongTin()
    {
        addNhanVien();
    }

    //Lấy thông tin từ EditText và lưu vào các biến/ Sau đó thêm vào Sach
    public NhanVien layDuLieu()
    {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTen_nhan_vien_nv(ten_nv.getText().toString().trim());
        nhanVien.setDia_chi_nv(dc.getText().toString().trim());
        nhanVien.setEmail_nv(email.getText().toString().trim());
        nhanVien.setSo_dien_thoai_nv(sdt.getText().toString().trim());
        nhanVien.setUsername(username.getText().toString().trim());
        nhanVien.setPassword(password.getText().toString().trim());
        return nhanVien;
    }
    //Thêm sách

    public void addNhanVien()
    {
        //Khai báo/khởi tạo sách và gọi hàm layDuLieu để thêm thông tin chp sách
        NhanVien nhanVien = layDuLieu();//Hàm lấy dữ liệu retrun về một biến tên sach-sau đó gán vào biến tên sach trong hàm này-lúc này sach sẽ có các thông tin từ người dùng nhập

        if(nhanVien != null)
        {
            database.addNhanVien(nhanVien);//Gọi hàm addBook từ Mydatabase để thêm sách
            AlertDialog.Builder builder = new AlertDialog.Builder(DangKy.this);
            builder.setTitle("Đăng ký okee");
            builder.setMessage("OKEEEE");
            builder.setNegativeButton("Tiếp tục thêm sách", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent back = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(back);
                }
            });
            builder.create().show();
        }
    }
}