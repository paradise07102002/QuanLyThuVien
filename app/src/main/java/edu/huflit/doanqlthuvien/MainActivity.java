package edu.huflit.doanqlthuvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import edu.huflit.doanqlthuvien.OOP.User;
import edu.huflit.doanqlthuvien.fragments.DangKy2;

public class MainActivity extends AppCompatActivity {
    ImageView img_main;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new MyDatabase(this);
        anhXa();
//        SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
//        SharedPreferences.Editor editor = get_user.edit();
//        editor.putBoolean("is_login", false);
//        editor.apply();
        //Tạo tài khoản admin
        boolean checkAdmin  = database.checkAdmin();
        if (checkAdmin == false)
        {
            User user = new User();
            //Dùng set để gán dữ liệu cho biến sach
            user.setUsername_user("admin");
            user.setPassword_user("admin");
            user.setFullname_user("Phạm Đình Khôi");
            user.setEmail_user("admin@gmail.com");
            user.setPhone_user("0123456789");
            user.setRole_user("admin");
            user.setLoai_kh_user("vip3");
            //Trả về loaisach chứa các thông tin của loại sách
            database.addUser(user);
        }

        //
        img_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ManHinhChinh.class);
                startActivity(intent);

            }
        });
    }
    public void anhXa()
    {
        img_main = (ImageView) findViewById(R.id.img_main);
    }

}