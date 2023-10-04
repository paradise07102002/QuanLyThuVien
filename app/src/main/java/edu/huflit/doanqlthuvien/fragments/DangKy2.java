package edu.huflit.doanqlthuvien.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import edu.huflit.doanqlthuvien.ManHinhChinh;
import edu.huflit.doanqlthuvien.MyDatabase;
import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.R;

public class DangKy2 extends Fragment {
    View view;
    EditText username, password, re_password, fullname, email, phone;
    Button dang_ky;
    ImageView back;
    TextView next_login;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dang_ky2, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();

        anhXa();

        dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemTraNhapThongTin();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoLogin();
            }
        });
        next_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoLogin();
            }
        });
        return view;
    }
    public void anhXa()
    {
        dang_ky = (Button) view.findViewById(R.id.dk_btn);
        username = (EditText) view.findViewById(R.id.dk_username);
        password = (EditText) view.findViewById(R.id.dk_password);
        re_password = (EditText) view.findViewById(R.id.dk_re_password);
        fullname = (EditText) view.findViewById(R.id.dk_fullname);
        email = (EditText) view.findViewById(R.id.dk_email);
        phone = (EditText) view.findViewById(R.id.dk_password);

        back = (ImageView) view.findViewById(R.id.dang_ky_back);
        next_login = (TextView) view.findViewById(R.id.dk_next_login);

    }
    public void kiemTraNhapThongTin()
    {
        if(username.length() == 0)
        {
            String p = "<font color='#FF0000'>Username không được để trống!</font>";
            username.setHint(Html.fromHtml(p));
        }
        else if (password.length() == 0)
        {
            String pass = "<font color='#FF0000'>Password không được để trống!</font>";
            password.setHint(Html.fromHtml(pass));
        } else if (re_password.length() == 0)
        {
            String pass = "<font color='#FF0000'>Re-password không được để trống!</font>";
            re_password.setHint(Html.fromHtml(pass));
        } else if (fullname.length() == 0)
        {
            String pass = "<font color='#FF0000'>Fullname không được để trống!</font>";
            fullname.setHint(Html.fromHtml(pass));
        } else if (email.length() == 0)
        {
            String pass = "<font color='#FF0000'>Email không được để trống!</font>";
            email.setHint(Html.fromHtml(pass));
        } else if (phone.length() == 0)
        {
            String pass = "<font color='#FF0000'>Phone không được để trống!</font>";
            phone.setHint(Html.fromHtml(pass));
        } else if (checkPass()==false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Mật khẩu không trùng khớp");
            builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }
        else if (checkUsername())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Username đã tồn tại");
            builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }
        else
        {
            addUser();
        }
    }
    public boolean checkPass()
    {
        String pass, re_pass;
        pass = password.getText().toString().trim();
        re_pass = re_password.getText().toString().trim();
        if (pass.equals(re_pass))
        {
            return true;
        }
        return false;
    }
    public boolean checkUsername()
    {
        boolean check = database.checkUsername(username.getText().toString().trim());
        if (check)
        {
            return true;
        }
        return false;
    }
    public User layDuLieu()
    {
        //Ngược lại là mã sách chưa tồn tại--> Thêm thông tin sách vào Sach

        //Khai báo/khởi tạo biến sach kiểu Sach
        User user = new User();
        //Dùng set để gán dữ liệu cho biến sach
        user.setUsername_user(username.getText().toString().trim());
        user.setPassword_user(password.getText().toString().trim());
        user.setFullname_user(fullname.getText().toString().trim());
        user.setEmail_user(email.getText().toString().trim());
        user.setPhone_user(phone.getText().toString().trim());
        user.setRole_user("user");
        user.setLoai_kh_user("vip3");
        //Trả về loaisach chứa các thông tin của loại sách
        return user;
    }
    public void addUser()
    {
        User user = layDuLieu();
        database.addUser(user);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Đăng ký thành công");
        builder.setNegativeButton("Đến đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
